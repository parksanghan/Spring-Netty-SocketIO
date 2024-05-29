package org.socketio.demo.config.SecurityHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.socketio.demo.config.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("request"+request);

        System.out.println(request.getSession());
        ResponseEntity<String> entity =  ResponseEntity.ok("Login Succeed");

        System.out.println("Login Succeed");
        // Set response status and headers
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");

        // Write the response body
        PrintWriter writer = response.getWriter();
        writer.write(Objects.requireNonNull(entity.getBody()));
        writer.flush();
    }   @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
