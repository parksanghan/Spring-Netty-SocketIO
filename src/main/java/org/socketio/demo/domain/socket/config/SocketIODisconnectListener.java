package org.socketio.demo.domain.socket.config;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socketio.demo.config.AuthenticationFilter;
import org.socketio.demo.domain.service.SocketSessionManager;
import org.socketio.demo.domain.socket.SocketIOHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
 public class SocketIODisconnectListener {
    private static final Logger log = LoggerFactory.getLogger(SocketIODisconnectListener.class);
    private final SessionRegistry sessionRegistry;
    private final SocketSessionManager socketSessionManager;
    @Setter
    private SocketIOServer server;


    public void onDisconnect(SocketIOClient socketIOClient) {
        try {

            System.out.print("left member" + socketIOClient.getSessionId() + "from in " + socketIOClient.getAllRooms());
            Set<String> rooms = socketIOClient.getAllRooms();
            System.out.println(rooms);
            UUID SocketId = socketIOClient.getSessionId();
            ObjectMapper objectMapper = new ObjectMapper();
            String targetroom=socketSessionManager.rooms_sid.get(socketIOClient.getSessionId());
            String jsonMapperData =
                    objectMapper.writeValueAsString(Map.of(SocketProperty.SID, socketIOClient.getSessionId()));
            rooms.forEach(room -> SocketIOHandler.
                    sendEventToOtherClientsExcluding
                            (room, SocketProperty.USER_DISCONNECT, server,
                                    socketIOClient, jsonMapperData, true));
            rooms.forEach(room -> socketSessionManager.deleteUsersInRoom(room, SocketId));
            // 모든 users_in_rooms 삭제  users_in_rooms[room] = [] -> sid
            rooms.forEach(room -> socketSessionManager.deleteRooms_Sid(room, SocketId));
            // 모든 rooms_sid 삭제  rooms_sid[jSessionId]  = room 삭제
            socketSessionManager.deleteNames_SidAtDisconnect(socketIOClient.getSessionId(),
                    socketSessionManager.names_sid.get(socketIOClient.getSessionId()));
            // names_sid 에 있는  names_sid[sid] = name 에서 삭제
            rooms.forEach(socketIOClient::leaveRoom);
            socketSessionManager.deleteRooms_Sid(targetroom, SocketId);
            socketSessionManager.deleteUsersInRoom(targetroom,SocketId);
            // 모든 방 나가기
            System.out.println("user" + socketSessionManager.usersInRoom);

            // 유저가 있던 방에 있던 모든 방 유저들에게 USER_DISCONNECT 이벤트 보냄 해당 클라이언트 SKIP


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
