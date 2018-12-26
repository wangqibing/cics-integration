package com.ccic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.ITestVisitor;
import org.aspectj.weaver.ast.Test;
import org.assertj.core.util.Arrays;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 8000600758 on 2018/12/3.
 */
public class TestGC1 extends TestGC{
    public String[] tlist = new String[]{"2"};
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        TestGC1 testGC1 = new TestGC1();
        testGC1.testStatic();
        testGC1.testNormal();
        System.out.println(testGC1.tlist[0]);
        TestGC testGC = new TestGC();
        testGC.testStatic();
        testGC.testNormal();
        System.out.println(testGC.tlist[0]);
//        Class clazz = HashMap.class;
//        HashMap hashMap = (HashMap) clazz.newInstance();
//        Method method = HashMap.class.getDeclaredMethod("hash",Object.class);
//        method.setAccessible(true);
//        System.out.println(method.invoke(hashMap, new Object[]{testGC1}));

    }

    public static void testStatic(){
        System.out.println("son");
    }

    public void testNormal(){
        lock.lock();
        System.out.println("normal-son");
        String abc = "123";
        String abcItern = abc;
        abcItern = "345";
        System.out.println(abc);
        System.out.println(abc == abcItern);
        lock.unlock();
    }
}
