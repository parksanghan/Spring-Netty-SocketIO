package org.socketio.demo.domain.socket.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocketProperty {
    public  static final String MESSAGE_KEY = "message";

    public static final  String JOIN_KEY = "join";

    public static final String OFFER_KEY = "offer";

    public static final  String ANSWER_KEY = "answer";


    public static final  String ICECANDIDATE_KEY = "candidate";


}
