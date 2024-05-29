package org.socketio.demo.domain.controller;


import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.sanghan.repository.annotation.SocketSupporter.SocketController;
import org.sanghan.repository.annotation.SocketSupporter.SocketOn;
import org.socketio.demo.config.AuthenticationFilter;
import org.socketio.demo.domain.socket.config.SocketProperty;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.net.Socket;


@RequiredArgsConstructor
@SocketController
public class SocketRoomController {



    @SocketOn(endPoint = SocketProperty.JOIN_KEY , requestDto = String.class)
    public void joinRoom(SocketIOClient client, SocketIOServer server, String request){
        System.out.println(request);
    }

    @SocketOn(endPoint = SocketProperty.OFFER_KEY , requestDto = String.class)
    public void offerRoom(SocketIOClient client, SocketIOServer server, String request){;
        System.out.println(request);
     }

    @SocketOn(endPoint = SocketProperty.ANSWER_KEY, requestDto = String.class)
    public void answerRoom(SocketIOClient client, SocketIOServer server, String request){
        System.out.println(request);
    }
        // endpoint : /candidate
    @SocketOn(endPoint = SocketProperty.ICECANDIDATE_KEY,requestDto = String.class)
    public void icecandidateRoom(SocketIOClient client, SocketIOServer server, String request){
        System.out.println(request);
    }
    // endpoint : /message
    @SocketOn(endPoint = SocketProperty.MESSAGE_KEY, requestDto = String.class)
    public void sendChat(SocketIOClient client, SocketIOServer server, String request) {
         System.out.println("메세지 받음"+client.getRemoteAddress()+ request);
        System.out.println(client.getHandshakeData().getAuthToken());
        client.getHandshakeData().getLocal();
        System.out.println(client.getSessionId().clockSequence());
     }

 }
