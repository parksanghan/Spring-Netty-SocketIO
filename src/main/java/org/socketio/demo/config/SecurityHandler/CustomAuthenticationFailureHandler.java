package org.socketio.demo.config.SecurityHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.socketio.demo.config.excetions.SecurityDoFilterAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler  implements AuthenticationFailureHandler {
    private final SecurityDoFilterAuthenticationEntryPoint entryPoint;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        entryPoint.commence(request, response, exception);
    }

}
