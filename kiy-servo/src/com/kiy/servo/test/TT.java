package com.kiy.servo.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TT {
 
    public static volatile int count = 0;
 
    static volatile AtomicInteger i = new AtomicInteger(0);
    public static void inc() {
        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
        i.getAndIncrement();
    }
 
    public static synchronized  void main(String[] args) {
 
        //同时启动1000个线程，去进行i++计算，看看实际结果
 
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    TT.inc();
                }
            }).start();
        }
 
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + TT.i);
    }
}