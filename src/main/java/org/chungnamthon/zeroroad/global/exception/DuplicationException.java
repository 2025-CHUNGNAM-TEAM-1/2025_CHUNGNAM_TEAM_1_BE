package org.chungnamthon.zeroroad.global.exception;

import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;

public class DuplicationException extends RootException {

    public DuplicationException(ErrorStatus message) {
        super(message);
    }

}