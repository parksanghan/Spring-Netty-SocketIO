package org.socketio.demo.domain.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class SocketServerRestartInitializer
        implements ApplicationListener<ContextRefreshedEvent> {


    public Integer port = 8085;
    private void stopServerOnPort(int port) {
        try {
            System.out.println("SocketServerRestartInitializer");
            // 8085 포트를 사용하는 서버 프로세스를 강제로 종료합니다.
            Runtime.getRuntime().exec("cmd /c for /f \"tokens=5\" %a in ('netstat -aon ^| findstr \":" + port + "\" ^| find \"LISTENING\"') do taskkill /F /PID %a");
        } catch (IOException e) {
            // 예외 처리
            System.err.println("ERROR: Could not stop server on port " + port);
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("onApplicationEvent");

        try {
            SocketServerStopper.waitForPortRelease(port,10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
