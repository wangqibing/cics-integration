package com.ccic.controller;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 8000600758 on 2018/11/30.
 */
public class TestGC {
    private Object instance = null;
    private static final int _10M = 10 * 1 << 20;
    // 一个对象占10M，方便在GC日志中看出是否被回收
    private byte[] bigSize = new byte[_10M];
    public String[] tlist = new String[]{"1"};

    @Test
    public  void test1() {
        TestGC objA = new TestGC();
        TestGC objB = new TestGC();

        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        System.gc();

    }
    
    public static void testStatic(){
        System.out.println("father");
        
    }
    
    public void testNormal(){
        System.out.println("normal-father");
        String value = "OK";
        switch (value){
            case "1" : System.out.println(123);
            return;
            case "OK" : System.out.println("OK");
            return;
            default:System.out.println("NO OK");
        }
    }
}