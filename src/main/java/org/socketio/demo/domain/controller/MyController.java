package org.socketio.demo.domain.controller;



import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import org.socketio.demo.api.recognizer.SpeechRecognition;
import org.socketio.demo.api.ttsmodel.TTSRecognizer;
import org.socketio.demo.domain.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.socketio.demo.domain.service.SocketSessionManager;
import org.socketio.demo.fileIo.FileIOManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.socketio.demo.domain.service.AuthUserDetailsService;
import ws.schild.jave.EncoderException;

import javax.script.Compilable;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
public class MyController {
    final private AuthUserDetailsService authUserDetailsService;
    final private SocketSessionManager socketSessionManager;
    final private SpeechRecognition speechRecognition;
    final private FileIOManager fileIOManager;
    final private TTSRecognizer ttsRecognizer;
//    private final TestManager testManager;
    private final SessionRegistry sessionRegistry;
    @GetMapping("/*")
    // 시작시 요청 리액트이기에 이후부턴 템플릿을 제공하지 않음 이후에 RESTFUL API 로 동작
    public String index()
    {
        return "../static/index";
    }
//    @GetMapping("/js/chatroom_ui.js")
//    public String chatRoom_ui(){
//        return "../fronted/src/js/chatroom_ui.js";
//    }
//    @GetMapping("/js/chatroom_ui.js")
//    public String chatRoom_networking(){
//        return "../fronted/src/js/chatroom_networking.js";
//    }
//    @GetMapping("/js/video-manager.js")
//    public String video_manager(){
//        return "../fronted/src/js/video-manager.js";
//    }
//    @GetMapping("/js/*")
//    public String requestJs(){
//        return "../fronted/src/"
//    }
 

    @PostMapping("/join")
    public ResponseEntity<String> authJoin(@RequestBody AuthUser user) {
        try {
            authUserDetailsService.joinUser(user);
            return ResponseEntity.ok("가입 완료");
         }

        catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(ServletRequest request) throws IOException, ExecutionException, InterruptedException, EncoderException {

        System.out.println("users in room : users_in_room[room_id] = List[] socketID"
                +socketSessionManager.usersInRoom);
        System.out.println("rooms_sid : rooms_sid[sid]= room socketID"
                +socketSessionManager.rooms_sid);
         System.out.println("names_sid: names_sid[sid] = name socketID"
                 +socketSessionManager.names_sid);
        System.out.println("lang_sid: lang_sid[sid] =언어 "
                +socketSessionManager.lang_Sid);
        String filepath= ttsRecognizer.TTSActivator("hello, my name is james", "tts_out");
        System.out.println(filepath);

         //        speechRecognition.activateRecognizers("ko-KR","녹음-_9_");

//        CompletableFuture<Boolean> is = fileIOManager.convertWebmToWav("녹음web_","output");

  //        HttpServletRequest  httpServletRequest=(HttpServletRequest)request;
//        Cookie[] cookies= httpServletRequest.getCookies();
//        Cookie cookie= searchJsessionid(cookies);
//        if (cookies!=null){
//            System.out.println("Cookie"+cookie.getValue());
//            System.out.println(socketSessionManager.names_sid.get(cookie.getValue()));
//        }
//         List<Object> allPrincipals =sessionRegistry.getAllPrincipals();
//        System.out.println(allPrincipals.size());
//         boolean is = allPrincipals.stream()
//                .map(UserDetails.class::cast)
//                .anyMatch(client -> sessionRegistry.getAllSessions(client, false)
//                        .stream()
//                        .anyMatch(sessionInformation -> sessionInformation.getSessionId().equals(cookie.getValue())));
//        System.out.println(is);
        return ResponseEntity.ok("hello");
    }




    public Cookie searchJsessionid(Cookie[] cookies){
        if (cookies!=null){
            for(Cookie eacookie:cookies){
                if (eacookie.getName().equals("JSESSIONID")){
                    return eacookie;
                }
            }
        }
        return null;
    }

}
