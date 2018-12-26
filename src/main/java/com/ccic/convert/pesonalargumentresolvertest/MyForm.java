package com.ccic.convert.pesonalargumentresolvertest;

import java.lang.annotation.*;

/**
 * Created by 8000600758 on 2018/11/20.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyForm {
    String value() default "";
}
