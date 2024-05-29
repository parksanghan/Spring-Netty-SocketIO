package org.sanghan.repository.annotation.SocketSupporter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketOn {
    String endPoint();
    Class<?> requestDto() default  Void.class;
}
