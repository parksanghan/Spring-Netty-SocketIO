package org.socketio.demo.errors;

import org.socketio.demo.errors.exception.CustomException;
import org.socketio.demo.errors.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandler(CustomException e){
        // CustomException 과 관련된 예외를 throw 할 시 이곳에 Handler로서 처리함
        ErrorCode code = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(code.getStatus(), code.getCode(), code.getMessage()),
                HttpStatus.valueOf(code.getStatus()));
    } // 이렇게하면 여기서 던지는 모든 exception 들은 여기서 에러를 던짐
}
