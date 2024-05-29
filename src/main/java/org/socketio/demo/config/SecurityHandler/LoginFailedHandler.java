package org.socketio.demo.config.SecurityHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
@Component
public class LoginFailedHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseEntity<String> entity =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login failed");
        System.out.println("Login failed");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");

        PrintWriter writer  =  response.getWriter();
        writer.write(Objects.requireNonNull(entity.getBody()));
        writer.flush();
    }
}
