package org.socketio.demo.annotation;


import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.EngineIOVersion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.sanghan.repository.annotation.SocketSupporter.SocketController;
import org.sanghan.repository.annotation.SocketSupporter.SocketOn;

@CustomGetter
@RequiredArgsConstructor
@SocketController
public class TestTT {

    @SocketOn(endPoint = "message", requestDto = String.class)
    public void sendChat(SocketIOClient client, SocketIOServer server, String request) {
        System.out.println("메세지 받음"+client.getRemoteAddress()+ request);
        client.getAllRooms();
        System.out.println("클라이언트들"+server.getAllClients());
        SocketIOClient client1=server.getClient(client.getSessionId());
        EngineIOVersion version =  client1.getEngineIOVersion();
        System.out.println("버전"+ version);

        System.out.println(server.getClient(client.getSessionId()));
        System.out.println(client.getAllRooms().toString()+client.getEngineIOVersion().toString()+client.getTransport().toString());

    }
}
