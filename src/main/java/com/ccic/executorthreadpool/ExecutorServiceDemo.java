package com.ccic.executorthreadpool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by 8000600758 on 2018/11/28.
 */
public class ExecutorServiceDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<String>> res = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Future future = executor.submit(new Task());
            res.add(future);
        }

        for (Future<String> f : res) {
            System.out.println(f.get() + " is returning");
        }
    }


    static class  Task implements Callable<String> {
        @Override
        public String call() {
            System.out.println(Thread.currentThread().getName() + " is running");
            return Thread.currentThread().getName();
        }

    }
}