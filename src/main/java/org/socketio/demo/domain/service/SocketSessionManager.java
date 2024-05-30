package org.socketio.demo.domain.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class SocketSessionManager{
    /** users_in_room  =  {} users_in_room[room_id] = [] 배열  sid 들이 들어감
    users_in_room : room id 를 키값으로 문자열 리스트를 가짐  map = 리스트 자료형*/
    public final Map<String, List<String>> usersInRoom  = new HashMap<>();

    /**
    @apiNote  rooms_sid = []   rooms_sid[sid]
    rooms_sid : roomId : sid 를 키값으로   room 값을 가짐 1대1임 map 자료형*/
    public final Map<String, String> rooms_sid = new HashMap<>();
    /** @apiNote  names_sid = {} # names_sid[sid] = client_name
    names_sid : sid 를 키값으로 name 값을 가짐 1대1임 map 자료형*/
    public final Map<String ,String> names_sid = new HashMap<>();

    public void saveNames_SidAtConnect(String JSessionId , String username){
        names_sid.put(JSessionId , username);
    }
    public void saveRooms_Sid(String roomId, String JSessionId){
        rooms_sid.put(JSessionId,roomId);
    }
    public void saveUsersInRoom(String roomId , String JSessionId){
         if (usersInRoom.containsKey(roomId)){
            usersInRoom.get(roomId).add(JSessionId);
         }
          else{
             usersInRoom.put(roomId, new ArrayList<>(Collections.singletonList(JSessionId)));
         }
    }

    public void deleteNames_SidAtDisconnect(String JSessionId, String username ){
        names_sid.remove(JSessionId);
    }
    public void deleteRooms_Sid(String roomId, String JSessionId){
        rooms_sid.remove(JSessionId);
    }
    public void deleteUsersInRoom(String roomId , String JSessionId){
        if (usersInRoom.containsKey(roomId)){
            usersInRoom.get(roomId).remove(JSessionId);
            if (usersInRoom.get(roomId).isEmpty())
            {
                usersInRoom.remove(roomId);
            }
        }

    }
}
