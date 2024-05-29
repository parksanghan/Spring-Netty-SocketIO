package org.socketio.demo.domain.request;

import lombok.Getter;

@Getter
public class JoinRoomRequest {
    private String roomId;
    private String userSessionId;

}
