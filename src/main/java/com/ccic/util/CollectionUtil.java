package com.ccic.util;

import com.ccic.domain.UserInfo;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 8000600758 on 2018/10/16.
 */
public  class CollectionUtil<T2> {
    private CollectionUtil() {
    }


    public T2 create(Class<T2> clazz){
        T2 t=null;
        try {
             t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static  <T,T1> void copyList(List<T> parseList,List<T1> returnList) throws NoSuchMethodException {
//        Method m = CollectionUtil.class.getMethod("copyList",List.class,List.class);
//        Type[] types = m.getParameterTypes();
//        //因为只有一个参数，所以我们拿第一个就可以了
//        ParameterizedType pt = (ParameterizedType) types[1];
//        //获得原始类型
//        System.out.println(pt.getRawType());
//        for(T t : parseList){
//            T1 t1 = null;
//            BeanUtils.copyProperties(t,t1);
//            returnList.add(t1);
//        }
    }

    public static void main(String[] args) throws NoSuchMethodException {
        UserInfo userInfo  = new UserInfo();
        userInfo.setUserName("小明");
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(userInfo);
        List<UserInfo> returnuserInfo = new ArrayList<>();
        copyList(userInfos,returnuserInfo);
        System.out.println(returnuserInfo.get(0).getUserName());
        
    }
}
