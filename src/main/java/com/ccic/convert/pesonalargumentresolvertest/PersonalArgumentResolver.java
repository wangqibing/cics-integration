package com.ccic.convert.pesonalargumentresolvertest;

import javassist.compiler.ast.FieldDecl;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;

/**
 * Created by 8000600758 on 2018/11/19.
 */

public class PersonalArgumentResolver implements HandlerMethodArgumentResolver {
    public PersonalArgumentResolver() {
        System.out.println(123);
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(MyForm.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {
        Assert.notNull(webDataBinderFactory, "无绑定器");
        Class targetType = methodParameter.getParameterType();
        MyForm myForm = methodParameter.getParameterAnnotation(MyForm.class);
        String prefix = getPrefix(myForm, targetType);
        Field[] fields = targetType.getDeclaredFields();
        Object target = targetType.newInstance();
        Object arg = null;//返回类型定义
        WebDataBinder webDataBinder = webDataBinderFactory.createBinder(nativeWebRequest,null,prefix);
        for (Field field : fields){
            field.setAccessible(true);
            String fieldName = field.getName();
            arg = webDataBinder.convertIfNecessary(nativeWebRequest.getParameter(String.join("",prefix,".",fieldName)),field.getType());
            field.set(target,arg);
        }
        return target;

    }

    private String getPrefix(MyForm myForm, Class targetType) {
        return myForm.value().equals("") ?  ClassUtils.getShortNameAsProperty(targetType) : myForm.value();
    }
}
