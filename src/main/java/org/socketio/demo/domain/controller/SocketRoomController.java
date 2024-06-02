package org.socketio.demo.domain.controller;


import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.sanghan.repository.annotation.SocketSupporter.SocketController;
import org.sanghan.repository.annotation.SocketSupporter.SocketOn;
import org.socketio.demo.config.AuthenticationFilter;
import org.socketio.demo.domain.service.SocketSessionManager;
import org.socketio.demo.domain.socket.SocketIOHandler;
import org.socketio.demo.domain.socket.config.SocketProperty;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RequiredArgsConstructor
@SocketController
public class SocketRoomController {
    private final  SocketSessionManager sessionManager;
    private  final  SocketSessionManager socketSessionManager;

    //                   "join-room"
    @SocketOn(endPoint = SocketProperty.JOIN_ROOM , requestDto = Map.class)
    public void joinRoom(SocketIOClient client, SocketIOServer server, Map<String,Object> request) {
        try {
            ObjectMapper connectData = new ObjectMapper();ObjectMapper userListData = new ObjectMapper();ObjectMapper userListSendData = new ObjectMapper();
            String room_id = (String) request.get(SocketProperty.ROOM_ID);
            String display_name = sessionManager.names_sid.get(client.getSessionId());
            client.joinRoom(room_id); // 방 입장
//            rooms_sid[sid] = room_id
//            names_sid[sid] = display_name  // 이부분은 save 하는 것들인데 name 은 connect 에 만함
            sessionManager.saveRooms_Sid(room_id,client.getSessionId());
            System.out.printf("[%s] client's username: [%s] is joined room : `[%s] %n",
                    client.getSessionId(), display_name, room_id); // UUID 형식에 맞춰 출력
             connectData.writeValueAsString
                    (Map.of(SocketProperty.SID,client.getSessionId(),SocketProperty.NAME,display_name));
             SocketIOHandler.sendEventToOtherClientsExcluding
                    //sio.emit("user-connect",{"sid":sid, "name":display_name},room=room_id,skip_sid=sid)
                    (room_id,SocketProperty.USER_CONNECT,server,client,connectData,true);
            // users_in_room [room_id] =  [] 소켓 리스트에서 방 키 값이 없는거라면
            // user-list 라는 이벤트로  "my-id"를 키로 socketId를 전송
            if (!socketSessionManager.usersInRoom.containsKey(room_id)) {
                sessionManager.saveUsersInRoom(room_id,client.getSessionId());
                userListData.writeValueAsString(Map.of(SocketProperty.MY_ID,client.getSessionId()));
                client.sendEvent(SocketProperty.USER_LIST,userListData);
            }
            else{
                // sessionManager.usersInRoom.get(room_id) 하면 반환값은 List<UUID>임 socketId임
                sessionManager.usersInRoom.get(room_id).
                         forEach(eaUUID-> {
                             try {
                                 userListData.writeValueAsString( // 이게 이제 userlist 의 내용임
                                         Map.of(eaUUID,sessionManager.names_sid.get(eaUUID)));
                                 //리스트 순회하면서 각각의 sid 를 names_sid[sid] =name 으로 부터 sid : name 매핑
                             } catch (JsonProcessingException e) {
                                 throw new RuntimeException(e);
                             }
                         });
                userListSendData.writeValueAsString(
                        Map.of(SocketProperty.LIST,userListData,SocketProperty.MY_ID,client.getSessionId()));
                //sio.emit("user-list", {"list": userlist, "my_id": sid},to=sid) 이거와 같이 특정 클라한테 보냄
                client.sendEvent(SocketProperty.USER_LIST,userListSendData);
                sessionManager.saveUsersInRoom(room_id,client.getSessionId());
                System.out.println("users: "+sessionManager.usersInRoom);
             }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @SocketOn(endPoint = SocketProperty.DATA,requestDto = Map.class)
    public void on_data(SocketIOClient client, SocketIOServer server, Map<String,Object> request) {

        UUID sender_sid =  (UUID)request.get(SocketProperty.SENDER_ID);
        UUID target_sid =  (UUID)request.get(SocketProperty.TARGET_ID);
        if (!sender_sid.equals(client.getSessionId())){
            System.out.println("[Not supposed to happen!] request sid and sender_id don't match!!!");
        }
        if (request.get(SocketProperty.TYPE)!=SocketProperty.NEW_ICE_CANDIDATE){
            System.out.println(sender_sid+"Message from");
            SocketIOClient targetClient=server.getClient(target_sid);
            targetClient.sendEvent(SocketProperty.DATA,request);
        }
    }

 }
