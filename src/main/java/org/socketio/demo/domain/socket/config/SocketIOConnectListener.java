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

import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class SocketIOConnectListener {
    private final SessionRegistry sessionRegistry;
    private final SocketSessionManager socketSessionManager;

    @Setter
    private SocketIOServer server;

    public void OnConnect(SocketIOClient socketIOClient)   {
        System.out.println(socketIOClient.getSessionId() + " connected"); // socketio
        List<String> cookies = socketIOClient.getHandshakeData().getHttpHeaders().getAll(HttpHeaders.COOKIE);
        for (String eaCookie : cookies) {
            if (eaCookie.contains("JSESSIONID")) eaCookie = eaCookie.replace("JSESSIONID=", "");
            System.out.println("접속자 연결 : JSESSIONID" + eaCookie + "SOCKETID" + socketIOClient.getSessionId());
            boolean isValid = AuthenticationFilter.isValidSession(sessionRegistry, eaCookie);
            System.out.println("IS VALID : " + isValid);
            if (isValid) {
                UserDetails authentication=AuthenticationFilter.findAuthenticationByJSessionId(sessionRegistry,eaCookie);
                if (authentication != null)socketSessionManager.saveNames_SidAtConnect(socketIOClient.getSessionId(),authentication.getUsername());
            }
        }
        socketIOClient.sendEvent("accept", socketIOClient.getSessionId());
        System.out.println("sessionId"+socketIOClient.getSessionId());
        SocketIOClient target=server.getClient(socketIOClient.getSessionId());
        if (target!=null) {
            System.out.println("target" + target.getSessionId());

            System.out.println(target.getSessionId().equals(socketIOClient.getSessionId()));
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String data =  objectMapper.writeValueAsString(Map.of("sid", target.getSessionId()));
                target.sendEvent("hello", data);
            }
            catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        }

    }


}

