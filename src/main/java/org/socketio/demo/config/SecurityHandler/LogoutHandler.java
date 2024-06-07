package org.socketio.demo.config.SecurityHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.socketio.demo.config.AuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;


/**
@apiNote Logout 요청 시 또는 웹페이지 Unload logout 요청을 합니다.
 */
@Component
@RequiredArgsConstructor
public class LogoutHandler implements LogoutSuccessHandler {
    private final SessionRegistry sessionRegistry;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println(request);
        Cookie[] cookies = request.getCookies();
        Cookie jsessionIdCookie =null;
        if (cookies!=null){
            cookies = request.getCookies();
            jsessionIdCookie =AuthenticationFilter.searchJsessionid(cookies);
            if(AuthenticationFilter.isValidSession(sessionRegistry,jsessionIdCookie)){
                sessionRegistry.removeSessionInformation(jsessionIdCookie.getValue());
                System.out.println(jsessionIdCookie.getValue()+"delete in session registry ");
            }
        }


        System.out.println("LogoutHandler onLogoutSuccess");
    }
}
