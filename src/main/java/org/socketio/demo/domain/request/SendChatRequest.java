package org.socketio.demo.domain.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendChatRequest {
    private String roomId;
    private String roomName;
    private String message;
}
