package org.socketio.demo.api.ttsmodel;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.socketio.demo.fileIo.FileIOProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component

public class TTSRecognizer {

    // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    private String speechKey;
    private String speechRegion;

    @Value("${speech.key}")
    public void setSpeechKey(String key) {
        speechKey = key;
    }

    @Value("${speech.region}")
    public void setSpeechRegion(String region) {
        speechRegion = region;
    }

    public String TTSActivator(String ttsText, String outputfilename) {
        File dir = new File(FileIOProperty.AUDIO_DIR);
        try (SpeechConfig config = SpeechConfig.fromSubscription(speechKey, speechRegion)) {
            config.setSpeechSynthesisVoiceName("en-US-AvaMultilingualNeural");
            File tempFile = File.createTempFile(outputfilename, FileIOProperty.WAV, dir);
            tempFile.deleteOnExit();
            try (AudioConfig audioConfig = AudioConfig.fromWavFileOutput(tempFile.getAbsolutePath())) {
                SpeechSynthesizer synth = new SpeechSynthesizer(config, audioConfig);
                Future<SpeechSynthesisResult> task = synth.SpeakTextAsync(ttsText);
                SpeechSynthesisResult result = task.get();
                if (result.getReason() == ResultReason.SynthesizingAudioCompleted){
                    System.out.println("Synthesizing audio completed");
                    byte[] audioData = Files.readAllBytes(tempFile.toPath());
                    // 결과 파일 저장
                    Files.write(Paths.get(tempFile.getAbsolutePath()), audioData);
                    return tempFile.getAbsolutePath();
                }
                else {
                    System.out.println("Error synthesizing audio: " + result.getReason());
                    return null;
                }
            } catch (ExecutionException | InterruptedException e) {
                return null;
            }


        } catch (IOException e) {
           return null;
        }
    }
}
