package org.socketio.demo.domain.controller;



import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.socketio.demo.domain.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.socketio.demo.domain.service.AuthUserDetailsService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MyController {
    final private AuthUserDetailsService authUserDetailsService;
    final private PasswordEncoder passwordEncoder;
    private final SessionRegistry sessionRegistry;
    @GetMapping("/*")
    public String index()
    {
        return "../static/index";
    }

  // 시작시 요청 리액트이기에 이후부턴 템플릿을 제공하지 않음 이후에 RESTFUL API 로 동작


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
    public ResponseEntity<String> hello(ServletRequest request) {
        HttpServletRequest  httpServletRequest=(HttpServletRequest)request;
        Cookie[] cookies= httpServletRequest.getCookies();
        Cookie cookie= searchJsessionid(cookies);
        if (cookies!=null){
            System.out.println("Cookie"+cookie.getValue());
        }
         List<Object> allPrincipals =sessionRegistry.getAllPrincipals();
        System.out.println(allPrincipals.size());
         boolean is = allPrincipals.stream()
                .map(UserDetails.class::cast)
                .anyMatch(client -> sessionRegistry.getAllSessions(client, false)
                        .stream()
                        .anyMatch(sessionInformation -> sessionInformation.getSessionId().equals(cookie.getValue())));
        System.out.println(is);
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
