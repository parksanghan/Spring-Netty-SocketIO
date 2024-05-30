package org.socketio.demo.domain.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
@RequiredArgsConstructor
public class SocketIOHandler {

    public static void sendEventToOtherClientsExcluding
            (String room ,String endPoint ,SocketIOServer server ,SocketIOClient targetClient,
             Object data , boolean isSkip)
    {
        server.getRoomOperations(room).getClients().stream().
                filter(client->isSkip ? !client.getSessionId().equals(targetClient.getSessionId()):true)
                .forEach(client-> client.sendEvent(endPoint,data));

    }
}
//  rooms.forEach(room->server.getRoomOperations(room).getClients().stream().
//filter(client->!client.getSessionId().equals(socketIOClient.getSessionId()))
//        .forEach(client-> {
//        try {
//        client.sendEvent("user-disconnect",
//                         objectMapper.writeValueAsString(Map.of("sid",socketIOClient.getSessionId())));
//        } catch (JsonProcessingException e) {
//        throw new RuntimeException(e);
//                    }
//                            }));