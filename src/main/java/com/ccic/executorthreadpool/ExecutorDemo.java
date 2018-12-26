package com.ccic.executorthreadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 8000600758 on 2018/11/28.
 */
public class ExecutorDemo {

    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            executor.execute(new Task());
        }
    }

}

class Task implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running");
    }
}

