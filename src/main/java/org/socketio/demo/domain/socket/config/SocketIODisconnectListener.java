package org.socketio.demo.domain.socket.config;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.socketio.demo.config.AuthenticationFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SocketIODisconnectListener {
    private final SessionRegistry sessionRegistry;
    @Setter
    private SocketIOServer server ;

    public void onDisconnect(SocketIOClient socketIOClient) {
        System.out.println("disconnected");
        Set<String> rooms=socketIOClient.getAllRooms();
        if (!rooms.isEmpty()){
            rooms.forEach(socketIOClient::leaveRoom);
        }
        List<String> cookies=socketIOClient.getHandshakeData().getHttpHeaders().getAll(HttpHeaders.COOKIE);
        for (String cookie:cookies){
            if(cookie.contains("JSESSIONID")){
                cookie=cookie.replace("JSESSIONID","") ;
                System.out.println("disconnected JSESSIONID:"+cookie);
                if ( AuthenticationFilter.isValidSession(sessionRegistry,cookie))
                    sessionRegistry.removeSessionInformation(cookie);
                System.out.println("Deleted JSESSIONID:"+cookie);

            }


        }

    }
}
