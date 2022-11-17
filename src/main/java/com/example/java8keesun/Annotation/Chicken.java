package com.example.java8keesun.Annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Repeatable(ChickenContainer.class)
public @interface Chicken {

    /**
     * <p>Retention : 이 애노테이션의 정보를 언제까지 유지할 것인가.
     * <p>Target : 애노테이션을 사용을 허용할 위치.
     */
    String value();
}
