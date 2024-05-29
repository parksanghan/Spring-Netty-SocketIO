package org.socketio.demo.domain.socket.config;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SocketIODisconnectListener {
    private SocketIOServer server ;
    public void setServer(SocketIOServer server) {
        this.server = server;
    }
    public void onDisconnect(SocketIOClient socketIOClient) {
        System.out.println("disconnected");
        Set<String> rooms=socketIOClient.getAllRooms();
        if (!rooms.isEmpty()){
            rooms.forEach(socketIOClient::leaveRoom);
        }

    }
}
