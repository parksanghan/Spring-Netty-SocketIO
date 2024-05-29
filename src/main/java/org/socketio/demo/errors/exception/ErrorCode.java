package org.socketio.demo.errors.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    BAD_REQUEST(400,"EXCEPTION-400-1","BadRequest"),
    ACCOUNT_LOCKED(401,"EXCEPTION-400-2","AccountLocked"),
    DUPLICATELOGIN(402, "EXCEPTION-400-3","DuplicateLogin"),;

    private final int status;
    private final String code;
    private final String message;
}
