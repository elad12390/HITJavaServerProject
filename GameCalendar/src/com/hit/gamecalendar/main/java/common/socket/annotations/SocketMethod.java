package com.hit.gamecalendar.main.java.common.socket.annotations;

import com.hit.gamecalendar.main.java.common.socket.enums.ESocketMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SocketMethod {
    ESocketMethod method() default ESocketMethod.None;
    String template() default "";
    boolean hasParams() default false;
}
