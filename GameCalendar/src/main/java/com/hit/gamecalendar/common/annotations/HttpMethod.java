package main.java.com.hit.gamecalendar.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HttpMethod {
    EHttpMethod method() default EHttpMethod.None;
    String template() default "";
    boolean hasParams() default false;
}
