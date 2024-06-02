package org.socketio.demo.domain.socket.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocketProperty {
    public  static final String MESSAGE_KEY = "message";

    public static final  String JOIN_ROOM = "join-room";

    public static final String OFFER_KEY = "offer";

    public static final  String ANSWER_KEY = "answer";

    public static final String ICECANDIDATE_KEY = "candidate";

    public static final String USER_DISCONNECT =  "user-disconnect";

    public static final String NEW_ICE_CANDIDATE =  "new-ice-candidate";

    public  static final String DATA = "data";

    public static final String SENDER_ID = "sender_id";

    public static final String TARGET_ID = "target_id";

    public static final String TYPE = "type";

    public static final String USER_CONNECT = "user-connect";

    public static final String ROOM_ID = "room_id";

    public static final String SID = "sid";

    public static final String NAME = "name";

    public static final String USER_LIST = "user-list";

    public static final String MY_ID = "my_id";

    public static final String LIST = "list";

    public static final String JSESSIONID_PARSER = "JSESSIONID=";

    public static final String JSESSIONID = "JSESSIONID";

    public static final String EMPTY_STRING = "";

}
