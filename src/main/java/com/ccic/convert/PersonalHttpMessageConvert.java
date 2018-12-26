package com.ccic.convert;

import com.alibaba.fastjson.JSON;
import com.ccic.annotation.PersonalAnnotation;
import com.ccic.controller.Person;
import org.apache.commons.lang3.AnnotationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 8000600758 on 2018/11/13.
 */

/**
 * 自定义MessageConvert
 */
@Configuration
public class PersonalHttpMessageConvert extends AbstractGenericHttpMessageConverter {
    private Map<String , Method> urlMap = new HashMap();
    private HttpServletRequest request;
    private String contextPath;
    private static boolean hasRead = false;
//    private   HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//    @Value("${springMacroRequestContext.contextPath}")
//    private String  contextPath;
    @Autowired
    ServletRegistrationBean servletRegistrationBean;
    @Override
    protected void writeInternal(Object o, @Nullable Type type, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
//        super.writeInternal(o,type,httpOutputMessage);
        System.out.println(123);
        httpOutputMessage.getBody().flush();
    }


    @Override
    public Object read(Type type, @Nullable Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
//        super.read(type,aClass,httpInputMessage);
        InputStream inputStream = httpInputMessage.getBody();
        byte[] btes = new byte[1024];
        String requestBody = "";
        while (inputStream.read(btes) != -1){
            requestBody = String.join("",requestBody,new String(btes));
        }
        return JSON.toJavaObject(JSON.parseObject(requestBody),(Class)type);
    }

    @Override
    public boolean canRead(final Type type, @Nullable Class aClass, @Nullable MediaType mediaType) {
        //初始化获取HttpRequest
        this.getRequest();
        //获取ContextPath
        this.contextPath = request.getContextPath();
        Method operatorMethod;
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            PersonalAnnotation personalAnnotation = method.getAnnotation(PersonalAnnotation.class);
            if (null == personalAnnotation)
                continue;
            List<String> valueList = Arrays.stream(personalAnnotation.getClass().getMethods()).filter(m -> m.getName().equals("value")).map(m -> {
                try {
                    return (String) m.invoke(personalAnnotation, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());

            break;
        }
        //获取Controller类中的所有URI方法路径
        try {
            urlMap= parseControllerUrl(aClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (urlMap.size() > 0) {
            Method method =urlMap.get(request.getRequestURI());
            if(null != method && method.isAnnotationPresent(PersonalAnnotation.class)){
                hasRead = true;
                return true;
            }

        }
        return false;
    }

    private boolean decide(Annotation m) {
        Class clazz = m.annotationType();
        if (clazz.equals(GetMapping.class) || clazz.equals(PostMapping.class) || clazz.equals(RequestMapping.class)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean canWrite(@Nullable MediaType mediaType) {
//        if(hasRead)
//            return true;
        return false;
    }

    @Override
    protected Object readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    private Map<String, Method> parseControllerUrl(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, Method> classUrlMap = new HashMap<>();
        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        Object objectUrl = "";
        if(null != requestMapping){
            objectUrl = requestMapping.getClass().getMethod("value").invoke(requestMapping);
        }
        String parentUrl = objectUrl==null?"":objectUrl.toString();
        Method[] methods = clazz.getMethods();

        for(Method method : methods){
            String  requestMappigUrl = method.getAnnotation(RequestMapping.class) == null?"":method.getAnnotation(RequestMapping.class).value()[0];
            String  postMappigUrl =method.getAnnotation(PostMapping.class) == null?"":method.getAnnotation(PostMapping.class).value()[0];
            String  getMappigUrl =method.getAnnotation(GetMapping.class) == null?"":method.getAnnotation(GetMapping.class).value()[0];
            if(requestMappigUrl != null || postMappigUrl !=null || getMappigUrl  != null){
                String  url = String.join("",contextPath,parentUrl,requestMappigUrl,postMappigUrl,getMappigUrl);
                classUrlMap.put(url,method);
            }

        }

        return classUrlMap;
    }

    private void getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        this.request = request;
    }
}
