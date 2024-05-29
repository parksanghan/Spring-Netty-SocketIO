package org.socketio.demo.domain.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class SocketIOServerCommandLineRunner implements CommandLineRunner
{
    final private SocketIOServer socketIOServer;
    @Override
    public void run(String... args) throws Exception {

        socketIOServer.start();
    }
}
