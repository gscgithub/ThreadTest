package com.gsc.threadtest3;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " is running");
                    try {
                        Thread.sleep(new Random().nextInt(20000) + 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                    System.out.println(Thread.currentThread().getName() + " has finished");
                }
            }).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        System.out.println("after countDownLatch.await()");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}
