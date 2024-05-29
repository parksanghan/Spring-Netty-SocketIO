package org.socketio.demo.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;
}
