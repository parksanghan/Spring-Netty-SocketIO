
package org.socketio.demo.domain.service;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SocketSessionManager{
    /** users_in_room  =  {} users_in_room[room_id] = [] 배열  sid 들이 들어감
    users_in_room : room id 를 키값으로 문자열 리스트를 가짐  map = 리스트 자료형*/
    @Autowired
    @Qualifier("usersInRoomMap")
    public   Map<String, List<UUID>> usersInRoom;
    /**
    @apiNote  rooms_sid = []   rooms_sid[sid]
    rooms_sid : roomId : sid 를 키값으로   room 값을 가짐 1대1임 map 자료형*/
    @Autowired
    @Qualifier("roomsSidMap")
    public   Map<UUID, String> rooms_sid ;
    /** @apiNote  names_sid = {} # names_sid[sid] = client_name
    names_sid : sid 를 키값으로 name 값을 가짐 1대1임 map 자료형*/
    @Autowired
    @Qualifier("namesSidMap")
    public   Map<UUID ,String> names_sid;
    @Autowired
    @Qualifier("langSidMap")
    public  Map<UUID,String> lang_Sid;


    public void saveNames_SidAtConnect(UUID socketId , String username){
        names_sid.put(socketId , username);
//        names_sid [socketId] = username
    }
    public void saveRooms_Sid(String roomId, UUID socketId){
        rooms_sid.put(socketId,roomId);
//      rooms_sid [socketId] = roomId
    }
    public void saveUsersInRoom(String roomId , UUID socketId){
         if (usersInRoom.containsKey(roomId)){
            usersInRoom.get(roomId).add(socketId);
//            usersInRoom[roomId] =  [] -> socketId 리스트형태
         }
          else{
             usersInRoom.put(roomId, new ArrayList<>(Collections.singletonList(socketId)));
//            usersInRoom[roomId] =  [] -> socketId 리스트형태
         }
    }

    public void deleteNames_SidAtDisconnect(UUID socketId, String username ){
        names_sid.remove(socketId);
//        names_sid[socketId] = username 삭제
    }
    public void deleteRooms_Sid(String roomId, UUID SocketId){
        rooms_sid.remove(SocketId);
//        roomId[SocketId] =  roomId  삭제
    }
    public void deleteUsersInRoom(String roomId , UUID SocketId){
        if (usersInRoom.containsKey(roomId)){
            usersInRoom.get(roomId).remove(SocketId);
            if (usersInRoom.get(roomId).isEmpty())
            {
                usersInRoom.remove(roomId);
            }
//            usersInRoom[roomId] =  SocketId  리스트 삭제
        }

    }


}
