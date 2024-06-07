package org.socketio.demo.domain.controller;


import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.sanghan.repository.annotation.SocketSupporter.SocketController;
import org.sanghan.repository.annotation.SocketSupporter.SocketOn;
import org.socketio.demo.api.LangProperty;
import org.socketio.demo.api.recognizer.SpeechRecognition;
import org.socketio.demo.api.ttsmodel.TTSRecognizer;
import org.socketio.demo.domain.service.SocketSessionManager;
 import org.socketio.demo.domain.socket.SocketIOHandler;
import org.socketio.demo.domain.socket.config.SocketProperty;
import org.socketio.demo.fileIo.FileIOManager;
import org.socketio.demo.fileIo.FileIOProperty;
import ws.schild.jave.EncoderException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@SocketController
@RequiredArgsConstructor
public class SocketRoomController {
    private final  SocketSessionManager sessionManager;
    private  final  SocketSessionManager socketSessionManager;
    private  final FileIOManager fileIOManager;
    private final SpeechRecognition speechRecognition;
    private final TTSRecognizer ttsRecognizer;
    //                   "join-room"
    @SocketOn(endPoint = SocketProperty.JOIN_ROOM , requestDto = Map.class)
    public void joinRoom(SocketIOClient client, SocketIOServer server, Map<String,Object> request) {
        try {

            ObjectMapper connectData = new ObjectMapper();ObjectMapper userListData = new ObjectMapper();ObjectMapper userListSendData = new ObjectMapper();
            String room_id = (String) request.get(SocketProperty.ROOM_ID);
            System.out.println(room_id);
            String display_name = sessionManager.names_sid.get(client.getSessionId());
            client.joinRoom(room_id); // 방 입장
//            rooms_sid[sid] = room_id
//            names_sid[sid] = display_name  // 이부분은 save 하는 것들인데 name 은 connect 에 만함
            sessionManager.saveRooms_Sid(room_id,client.getSessionId());
            System.out.printf("[%s] client's username: [%s] is joined room : `[%s] %n",
                    client.getSessionId(), display_name, room_id); // UUID 형식에 맞춰 출력
             String userConnectData=connectData.writeValueAsString
                    (Map.of(SocketProperty.SID,client.getSessionId(),SocketProperty.NAME,display_name));
             SocketIOHandler.sendEventToOtherClientsExcluding
                    //sio.emit("user-connect",{"sid":sid, "name":display_name},room=room_id,skip_sid=sid)
                    (room_id,SocketProperty.USER_CONNECT,server,client,userConnectData,true);
            // users_in_room [room_id] =  [] 소켓 리스트에서 방 키 값이 없는거라면
            // user-list 라는 이벤트로  "my-id"를 키로 socketId를 전송
            if (!socketSessionManager.usersInRoom.containsKey(room_id)) {
                sessionManager.saveUsersInRoom(room_id,client.getSessionId());
                String userListJsonData = userListData.writeValueAsString(Map.of(SocketProperty.MY_ID,client.getSessionId()));
                client.sendEvent(SocketProperty.USER_LIST,userListJsonData);
            }
            else{
                Map<UUID,String> userJSONList = new HashMap<>();
                // sessionManager.usersInRoom.get(room_id) 하면 반환값은 List<UUID>임 socketId임
                sessionManager.usersInRoom.get(room_id).
                         forEach(eaUUID-> {
                             System.out.println(eaUUID);
                             userJSONList.put(eaUUID,sessionManager.names_sid.get(eaUUID));
                         });
                String userListSendJSONDATA= userListSendData.writeValueAsString(
                        Map.of(SocketProperty.LIST,userJSONList,SocketProperty.MY_ID,client.getSessionId()));
                //sio.emit("user-list", {"list": userlist, "my_id": sid},to=sid) 이거와 같이 특정 클라한테 보냄
                client.sendEvent(SocketProperty.USER_LIST,userListSendJSONDATA);
                sessionManager.saveUsersInRoom(room_id,client.getSessionId());
                System.out.println("users: "+sessionManager.usersInRoom);
             }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @SocketOn(endPoint = SocketProperty.DATA,requestDto = Object.class)
    public void on_data(SocketIOClient client, SocketIOServer server, Object request) {
        if (request instanceof Map) {
            Map<String, Object> data = (Map<String, Object>) request;
            Object senderIdObj = data.get(SocketProperty.SENDER_ID);
            Object targetIdObj = data.get(SocketProperty.TARGET_ID);
            UUID sender_sid = UUID.fromString((String) senderIdObj);
            UUID target_sid = UUID.fromString((String) targetIdObj);
            System.out.println();
            if (!sender_sid.equals(client.getSessionId())) {
                System.out.println("[Not supposed to happen!] request sid and sender_id don't match!!!");
            }
            if (data.get(SocketProperty.TYPE) != SocketProperty.NEW_ICE_CANDIDATE) {
                System.out.println(sender_sid + "Message from");
                SocketIOClient targetClient = server.getClient(target_sid);
                targetClient.sendEvent(SocketProperty.DATA, request);
            }
        }
    }



    @SocketOn(endPoint =SocketProperty.SET_LANG,requestDto = String.class)
    public void on_setLang(SocketIOClient client, SocketIOServer server, String lang) {
        System.out.println("언어설정");
        sessionManager.lang_Sid.put(client.getSessionId(),lang);
    }

        // stt 요청
    @SocketOn(endPoint =SocketProperty.AUDIO,requestDto = String.class)
    public void on_audio(SocketIOClient client, SocketIOServer server, String audioData) throws IOException, EncoderException, ExecutionException, InterruptedException {
        System.out.println("vocie 발생");
        byte[] decodedAudi= Base64.getDecoder().decode(audioData);
        String filePath  = FileIOProperty.AUDIO_DIR +client.getSessionId().toString()+FileIOProperty.WEBM;
        try (OutputStream outputStream = new FileOutputStream(filePath, true)) { // append 모드로 파일 열기
            outputStream.write(decodedAudi); // 바이트 배열 추가
            System.out.println(filePath);
        } catch (IOException e) {
            System.err.println("Error writing audio file: " + e.getMessage());
        }
        String path = fileIOManager.convertWebmToWav(client.getSessionId().toString(),client.getSessionId().toString());
        String reuslt=speechRecognition.activateRecognizers
                (sessionManager.lang_Sid.get(client.getSessionId()),client.getSessionId().toString());
        System.out.println(reuslt);
        client.sendEvent(SocketProperty.STT_ANSWER,reuslt); // speech to answer 텍스트 전송
    }

    @SocketOn(endPoint =SocketProperty.TTS_REQUEST,requestDto = String.class)
    public void on_tts_request(SocketIOClient client, SocketIOServer server, String text)  {
        String absolutePath=ttsRecognizer.TTSActivator(text,"tts_"+client.getSessionId().toString());
        File file = new File(absolutePath);
        if (!file.exists()){
            System.out.println("존재하지않는 파일"+absolutePath);
            client.sendEvent(SocketProperty.TTS_ANSWER,"ERROR");
            return;
        }
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] audioData = inputStream.readAllBytes();
            client.sendEvent(SocketProperty.TTS_ANSWER,audioData);
        }
        catch (IOException e) {
            client.sendEvent(SocketProperty.TTS_ANSWER,"ERROR");
        }

    }

    }
