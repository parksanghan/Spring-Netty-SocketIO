package org.socketio.demo.config.excetions;

import org.socketio.demo.errors.exception.CustomException;
import org.socketio.demo.errors.exception.ErrorCode;

public class DuplicateLoginException extends CustomException {
    public static final CustomException EXCEPTION =
            new DuplicateLoginException();

    public DuplicateLoginException() {
        super(ErrorCode.DUPLICATELOGIN);
    }
}
