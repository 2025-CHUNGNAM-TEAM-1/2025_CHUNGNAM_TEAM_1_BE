package org.chungnamthon.zeroroad.global.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiExceptions {

    ErrorStatus[] values();

}