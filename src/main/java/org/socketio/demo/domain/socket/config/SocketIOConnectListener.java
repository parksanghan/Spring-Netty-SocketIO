package org.socketio.demo.domain.socket.config;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.Collection;


@Component
@RequiredArgsConstructor
public class SocketIOConnectListener {
    private SocketIOServer server;
     public void setServer(SocketIOServer server){
         this.server = server;
     }

    public void OnConnect(SocketIOClient socketIOClient) {

        System.out.println(socketIOClient.getSessionId() + " connected");
        BroadcastOperations broadcast = server.getBroadcastOperations();
        Collection<SocketIOClient> clientss=broadcast.getClients();
//        server.getAllClients().forEach(eaclient -> eaclient.sendEvent("accept",eaclient.getSessionId()));
        // 해당 코드는 접속할때마다 모든 클라이언트에게
        socketIOClient.sendEvent("accept",socketIOClient.getSessionId());
        //
        socketIOClient.isChannelOpen();
        System.out.println(socketIOClient.isWritable());
        server.getClient(socketIOClient.getSessionId());
        System.out.println("이벤트 보냄 ");


     }
}
