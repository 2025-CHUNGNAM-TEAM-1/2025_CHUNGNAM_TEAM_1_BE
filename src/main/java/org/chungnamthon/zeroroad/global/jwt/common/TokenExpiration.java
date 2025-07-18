package org.chungnamthon.zeroroad.global.jwt.common;

import lombok.Getter;

@Getter
public enum TokenExpiration {

    ACCESS_TOKEN(2 * 60 * 60 * 1000L),       // 2시간
    REFRESH_TOKEN(7 * 24 * 60 * 60 * 1000L), // 1주일
    TEMPORARY_TOKEN(3 * 60 * 1000L);         // 3분

    private final long expirationMs;

    TokenExpiration(long expirationMs) {
        this.expirationMs = expirationMs;
    }

}