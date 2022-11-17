package com.example.java8keesun.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface ChickenContainer {

    /**
     * Container Annotation의 Retention과 Target은 포함하는 Annotation보다 같거나 넓어야 한다.
     */
    Chicken[] value();
}
