package org.socketio.demo.fileIo;


import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

public class CustomFFMPEGLocator extends DefaultFFMPEGLocator {
    @Override
    public String getExecutablePath() {
        return "F:\\jave2-3.3.1\\jave-nativebin-win64\\src\\main\\resources\\ws\\schild\\jave\\nativebin\\ffmpeg-amd64.exe";
    }
}