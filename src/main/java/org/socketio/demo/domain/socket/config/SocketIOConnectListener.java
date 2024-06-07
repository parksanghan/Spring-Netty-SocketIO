package org.socketio.demo.domain.socket.config;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.socketio.demo.config.AuthenticationFilter;
import org.socketio.demo.domain.service.SocketSessionManager;
 import org.socketio.demo.domain.socket.SocketIOHandler;
import org.socketio.demo.fileIo.FileIOProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
 import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SocketIOConnectListener {
    private final SessionRegistry sessionRegistry;
    private final SocketSessionManager socketSessionManager;
//    private final TestManager testManager;

    @Setter
    private SocketIOServer server;

    public void OnConnect(SocketIOClient socketIOClient)   {
        System.out.println(socketIOClient.getSessionId() + " connected"); // socketio SocketId
        System.out.println(socketIOClient.getSessionId().toString() + " connected"); // socketio SocketId
        List<String> cookies = socketIOClient.getHandshakeData().getHttpHeaders().getAll(HttpHeaders.COOKIE);
        for (String eaCookie : cookies) {
            if (eaCookie.contains(SocketProperty.JSESSIONID)) eaCookie =
                    eaCookie.replace(SocketProperty.JSESSIONID_PARSER, SocketProperty.EMPTY_STRING);
            System.out.println("접속자 연결 : JSESSIONID" + eaCookie + "SOCKETID" + socketIOClient.getSessionId());
            boolean isValid = AuthenticationFilter.isValidSession(sessionRegistry, eaCookie);
            System.out.println("IS VALID : " + isValid);
            if (isValid) {
                UserDetails authentication=AuthenticationFilter.findAuthenticationByJSessionId(sessionRegistry,eaCookie);
                if (authentication != null) {
                    System.out.println("saved at connect"+ socketIOClient.getSessionId()+ "name: " + authentication.getUsername() );
                    socketSessionManager.saveNames_SidAtConnect
                            (socketIOClient.getSessionId(),authentication.getUsername());
                    System.out.println(socketSessionManager.names_sid.get(socketIOClient.getSessionId()));
                    System.out.println("보냄");
                }
                // names_sid[sid] = username 저장
            }
        }

    }


}

//System.out.println(socketSessionManager.names_sid);
//ObjectMapper mapper = new ObjectMapper();
//                    try{
//String JSONDATA = mapper.writeValueAsString(Map.of("test","test"));
//                        socketIOClient.sendEvent("test",JSONDATA);
//                        System.out.println("보냄");
//Map<String,List<UUID>> users_in_room = new HashMap<>();
//Map<UUID,String> names_sid = new HashMap<>();
//                        names_sid.put(socketIOClient.getSessionId(),"NAME");
//List<UUID> uuids = new ArrayList<>();
//                        uuids.add(socketIOClient.getSessionId());
//        uuids.add(socketIOClient.getSessionId());
//        uuids.add(socketIOClient.getSessionId());
//        uuids.add(socketIOClient.getSessionId());
//        uuids.add(socketIOClient.getSessionId());
//        users_in_room.put("room",uuids);
//                        users_in_room.get("room").forEach(eaUUID->{
//ObjectMapper jsondata = new ObjectMapper();
//                            try {
//String str=jsondata.writeValueAsString(Map.of(eaUUID,names_sid.get(eaUUID)));
//                                socketIOClient.sendEvent("test",str);
//                            } catch (JsonProcessingException e) {
//        throw new RuntimeException(e);
//                            }
//                                    });
//                                    }
//                                    catch (JsonProcessingException e){
//        System.out.println("ERROR JsonProcessingException");
//                    }



//   sessionManager.usersInRoom.get(room_id).

//forEach(eaUUID-> {
//        System.out.println(eaUUID);
//                             try {
//                                     userListData.writeValueAsString( // 이게 이제 userlist 의 내용임
//                                                                      Map.of(eaUUID,sessionManager.names_sid.get(eaUUID)));
//        //리스트 순회하면서 각각의 sid 를 names_sid[sid] =name 으로 부터 sid : name 매핑
//        } catch (JsonProcessingException e) {
//        throw new RuntimeException(e);
//                             }
//                                     });
//                                     userListSendData.writeValueAsString(
//        Map.of(SocketProperty.LIST,userListData,SocketProperty.MY_ID,client.getSessionId()));
