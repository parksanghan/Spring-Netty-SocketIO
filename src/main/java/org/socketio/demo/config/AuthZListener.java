package org.socketio.demo.config;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.util.List;

// Authentication 은 AuthN 으로 칭하며
// Authorization 은 AuthZ 로 칭합니다.
@Component
@RequiredArgsConstructor
public class AuthZListener implements AuthorizationListener {
    private final SessionRegistry sessionRegistry;
    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData handshakeData) {
        HttpHeaders httpHeaders=handshakeData.getHttpHeaders();
        boolean isValid=false;
        List<String> cookies=  httpHeaders.getAll(org.springframework.http.HttpHeaders.COOKIE);
        System.out.println(cookies);
        if (cookies!=null){
            for (String eaCookie : cookies){
                System.out.println("eaCookie:"+eaCookie);
                if (eaCookie.contains("JSESSIONID")){
                    eaCookie = eaCookie.replace("JSESSIONID=", "");
                    System.out.println("CONTAINS : JSESSIONID:"+eaCookie+"end");
                    isValid=AuthenticationFilter.isValidSession(sessionRegistry,eaCookie);
                    System.out.println(isValid);
                }
            }
        }
        return isValid ?
                AuthorizationResult.SUCCESSFUL_AUTHORIZATION : AuthorizationResult.FAILED_AUTHORIZATION;
    }
}
