package com.ccic.annotation;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RunAs;
import java.lang.annotation.*;

/**
 * Created by 8000600758 on 2018/11/13.
 */
@RequestMapping("/")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PersonalAnnotation {
    String value() default "";
}
