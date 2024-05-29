package org.socketio.demo.config.excetions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.socketio.demo.errors.exception.CustomException;
import org.socketio.demo.errors.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
@apiNote "AuthenticationFilter" 클래스는 스프링의 "RestControllerAdvice"로 선언된 "GlobalExceptionHandler" 클래스와 직접적인 연결이 없기에 예외 발생해도 "GlobalExceptionHandler"가 호출되지 않음."
SecurityDoFilterAuthenticationEntryPoint 를 통해
 @see org.springframework.security.web.AuthenticationEntryPoint

 */
@Component
public class SecurityDoFilterAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        Throwable cause = authException.getCause();
        if (authException instanceof CustomAuthenticationException customaryException) {
            System.out.println("customaryException");
            ErrorCode errCode = customaryException.getErrorCode();
                response.sendError(errCode.getStatus(), errCode.getMessage());
        }
        else{
            System.out.println("SC_AUTH_EXCEPTION");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }
    }

}
