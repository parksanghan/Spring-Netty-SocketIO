//package org.socketio.demo.domain.service;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class TestManager {
//    /** users_in_room  =  {} users_in_room[room_id] = [] 배열  sid 들이 들어감
//     users_in_room : room id 를 키값으로 문자열 리스트를 가짐  map = 리스트 자료형*/
//    public   Map<String, List<UUID>> usersInRoom;
//    /**
//     @apiNote  rooms_sid = []   rooms_sid[sid]
//     rooms_sid : roomId : sid 를 키값으로   room 값을 가짐 1대1임 map 자료형*/
//
//    public   Map<UUID, String> rooms_sid ;
//    /** @apiNote  names_sid = {} # names_sid[sid] = client_name
//    names_sid : sid 를 키값으로 name 값을 가짐 1대1임 map 자료형*/
//    public   Map<UUID ,String> names_sid;
//
//}
