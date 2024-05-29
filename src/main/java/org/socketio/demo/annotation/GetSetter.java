package org.socketio.demo.annotation;

import lombok.Getter;
import lombok.Setter;
import org.sanghan.repository.Person;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

public @interface GetSetter {
 }
