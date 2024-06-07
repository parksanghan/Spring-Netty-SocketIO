package org.socketio.demo.fileIo;

import io.netty.handler.codec.EncoderException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Component
public class FileIOManager {

     public  String convertWebmToWav(String filename, String outputfilename) throws EncoderException, ws.schild.jave.EncoderException {

            try {
                System.out.println("convertWebmToWav");
                File source =  new File(FileIOProperty.AUDIO_DIR+filename+FileIOProperty.WEBM);
                File target = new File( FileIOProperty.AUDIO_DIR+outputfilename+FileIOProperty.WAV);


                AudioAttributes audio = new AudioAttributes();
                audio.setCodec("pcm_s16le");
                EncodingAttributes attrs = new EncodingAttributes();
                attrs.setOutputFormat("wav");
                 attrs.setAudioAttributes(audio);
                Encoder encoder = new Encoder(new CustomFFMPEGLocator());
                encoder.encode(new MultimediaObject(source), target, attrs);

                System.out.println("true");
                return target.getAbsolutePath(); // 변환 성공 시 true 반환
            } catch (EncoderException | ws.schild.jave.EncoderException e) {
                System.out.println("false");
                System.out.println(e.getMessage());
                return null; // 변환 실패 시 false 반환
            }

    }
}
