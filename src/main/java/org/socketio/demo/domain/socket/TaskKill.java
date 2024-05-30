package org.socketio.demo.domain.socket;

import java.io.IOException;

public class TaskKill {
    public static void killProcessByPort(int port) throws IOException {
        Runtime.getRuntime().exec
                ("taskkill /F /PID $(netstat -ano | findstr :" + port +
                        " | grep LISTEN | awk '{print $5}')");
    }
}
