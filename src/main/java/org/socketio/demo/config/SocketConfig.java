package org.socketio.demo.config;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import io.netty.handler.codec.http.HttpHeaders;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
 import org.socketio.demo.domain.socket.config.SocketIOConnectListener;
import org.socketio.demo.domain.socket.config.SocketIODisconnectListener;
import org.socketio.demo.domain.socket.config.SocketIoAddMappingSupporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SocketConfig {

    final private SocketIOConnectListener socketIOConnectListener;
    final private SocketIODisconnectListener socketIODisconnectListener;
    final private SocketIoAddMappingSupporter socketIoAddMappingSupporter;
    final private AuthZListener authZListener;
    @Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setOrigin("*");

        config.setAuthorizationListener(authZListener);
        // SSL 증명서를 로드하고 적용
//        try {
//            SslContext sslContext = SslContextBuilder.forServer(new File("your-server-cert.pem"), new File("your-server-key.pem"))
//                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
//                    .build();
//        } catch (SSLException e) {
//            throw new RuntimeException(e);
//        }

        SocketIOServer socketIOServer = new SocketIOServer(config);
        socketIOConnectListener.setServer(socketIOServer);
        socketIODisconnectListener.setServer(socketIOServer);
        socketIoAddMappingSupporter.addListener(socketIOServer);
        socketIOServer.addConnectListener(socketIOConnectListener::OnConnect);
        socketIOServer.addDisconnectListener(socketIODisconnectListener::onDisconnect);

        return socketIOServer;
    }
}
