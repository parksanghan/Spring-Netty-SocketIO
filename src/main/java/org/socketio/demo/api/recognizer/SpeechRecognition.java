package org.socketio.demo.api.recognizer;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import io.netty.handler.codec.EncoderException;
import lombok.RequiredArgsConstructor;
import org.socketio.demo.fileIo.FileIOProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component

public class SpeechRecognition {
    // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    private   String speechKey;
    private  String speechRegion;

    @Value("${speech.key}")
    public void setSpeechKey(String key) {
        speechKey = key;
    }

    @Value("${speech.region}")
    public void setSpeechRegion(String region) {
        speechRegion = region;
    }
    public String activateRecognizers(String langConfig,String filename) throws InterruptedException, ExecutionException, IOException {
        System.out.println("activateRecognizers");
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        speechConfig.setSpeechRecognitionLanguage(langConfig);
        return recognizeFromWav(speechConfig,filename);
    }

    public String recognizeFromWav(SpeechConfig speechConfig, String filename) {

            try {
                File source = new File(FileIOProperty.AUDIO_DIR + filename + FileIOProperty.WAV);
                if (source.exists()) {
                    String filepath = source.getAbsolutePath();
                    System.out.println(filepath);
                    AudioConfig audioConfig = AudioConfig.fromWavFileInput(filepath);
                    SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);
                    Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
                    SpeechRecognitionResult speechRecognitionResult = task.get();
                    if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
                        System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());
                        return speechRecognitionResult.getText();
                    } else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
                        System.out.println("NOMATCH: Speech could not be recognized.");
                    } else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
                        CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
                        System.out.println("CANCELED: Reason=" + cancellation.getReason());

                        if (cancellation.getReason() == CancellationReason.Error) {
                            System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                        }
                    }
                } else {
                    System.err.println("Audio file not found: " + filename);
                }
            } catch (Exception e) {
                System.out.println("false");
                System.out.println(e.getMessage());
            }

            return "ERROR"; // 컴파일 에러 방지를 위한 기본 반환값

    }


}