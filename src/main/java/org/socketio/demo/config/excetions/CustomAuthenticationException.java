package org.socketio.demo.config.excetions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.socketio.demo.errors.exception.CustomException;
import org.socketio.demo.errors.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
@Getter

public class CustomAuthenticationException extends AuthenticationException {

    private  final ErrorCode errorCode;

    public CustomAuthenticationException(CustomException customException) {
        super(customException.getMessage());
        this.errorCode = customException.getErrorCode();
    }
}
