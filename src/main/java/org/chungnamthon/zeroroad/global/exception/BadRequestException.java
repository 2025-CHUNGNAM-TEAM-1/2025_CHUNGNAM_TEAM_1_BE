package org.chungnamthon.zeroroad.global.exception;

import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;

public class BadRequestException extends RootException {

    public BadRequestException(ErrorStatus message) {
        super(message);
    }

}