package org.socketio.demo.domain.socket.config;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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


@Component
@RequiredArgsConstructor
public class SocketIOConnectListener {
    private final SessionRegistry sessionRegistry;
    private final SocketSessionManager socketSessionManager;

    @Setter
    private SocketIOServer server;

    public void OnConnect(SocketIOClient socketIOClient) {
        System.out.println(socketIOClient.getSessionId() + " connected"); // socketio
        List<String> cookies = socketIOClient.getHandshakeData().getHttpHeaders().getAll(HttpHeaders.COOKIE);
        for (String eaCookie : cookies) {
            if (eaCookie.contains("JSESSIONID")) eaCookie = eaCookie.replace("JSESSIONID=", "");
            System.out.println("접속자 연결 : JSESSIONID" + eaCookie + "SOCKETID" + socketIOClient.getSessionId());
            boolean isValid = AuthenticationFilter.isValidSession(sessionRegistry, eaCookie);
            System.out.println("IS VALID : " + isValid);
            if (isValid) {
                UserDetails authentication=AuthenticationFilter.findAuthenticationByJSessionId(sessionRegistry,eaCookie);
                if (authentication != null)socketSessionManager.saveNames_SidAtConnect(eaCookie,authentication.getUsername());
            }
        }
        socketIOClient.sendEvent("accept", socketIOClient.getSessionId());

    }


}

