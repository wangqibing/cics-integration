package com.ccic.util;

import org.apache.tomcat.util.http.MimeHeaders;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * Created by 8000600758 on 2018/10/11.
 * 修改httpRequestHead头部的信息
 */
public class ReflectUtil {
    private ReflectUtil(){}
    public static synchronized  void reflectSetparam(HttpServletRequest request, String key, String value){
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        System.out.println("request实现类="+requestClass.getName());
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            System.out.println("coyoteRequest实现类="+o1.getClass().getName());
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            o2.removeHeader(key);
            o2.addValue(key).setString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
