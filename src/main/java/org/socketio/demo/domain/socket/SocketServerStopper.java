package org.socketio.demo.domain.socket;

import org.springframework.data.redis.stream.Task;

import java.net.*;
import java.util.concurrent.TimeUnit;

public class SocketServerStopper  {
    public static void waitForPortRelease(int port, long timeoutMillis) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + timeoutMillis;

        while (System.currentTimeMillis() < endTime) {
            try {
                 // 시간 제한 내에 포트가 열려 있는지 확인
                new Socket("localhost", port).close();

                // 포트가 열려 있다면 잠시 대기
                TimeUnit.SECONDS.sleep(1);
            } catch (ConnectException e) {
                // 포트가 닫혔음을 확인
                return;
            } catch (Exception e) {
                // 예외 처리
                e.printStackTrace();
            }
        }

        // 지정된 시간 내에 포트가 아직 열려 있는 경우
        throw new InterruptedException("Port " + port + " is still in use after waiting for " + timeoutMillis + " milliseconds.");
    }
}