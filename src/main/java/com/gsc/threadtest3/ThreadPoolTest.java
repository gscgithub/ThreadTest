package com.gsc.threadtest3;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {
//        new it(){
//            public void method(int i,int j){
//                System.out.println(i+j);
//            }
//        }.method(1, 2);
        new ThreadPoolTest().test4();
    }

    @Test
    public void test4() {
//        ExecutorService executor = Executors.newFixedThreadPool(5);
        ExecutorService executor = new ThreadPoolExecutor(4, 6, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4),Executors.defaultThreadFactory());
        for(int i=0; i<10; i++) {
            executor.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " is runing");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    if(true) {
//                        throw new RuntimeException(Thread.currentThread().getName() + " exception");
//                    }
                    System.out.println(Thread.currentThread().getName() + " is finished");
//                    return Thread.currentThread().getName();
                }
            });
            System.out.println("任务" + (i+1) + "已提交");
        }
        System.out.println("all task submit");
        System.out.println("test4 has end");
    }

    @Test
    public void test3() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ArrayList<Future> futures = new ArrayList<>();
        for(int i=0; i<5; i++) {
            futures.add(executor.submit(new Runnable() {

                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " is runing");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(true) {
//                        throw new RuntimeException(Thread.currentThread().getName() + " exception");
                    }
                    System.out.println(Thread.currentThread().getName() + " is finished");
//                    return Thread.currentThread().getName();
                }
            }));
            System.out.println("任务" + (i+1) + "已提交");
        }
        System.out.println("all task submit");
//        try {
//            System.out.println("futures size:" + futures.size());
//            for (Future<String> future : futures) {
//                System.out.println("future get: " + future.get());
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        System.out.println("test3 has end");
    }

    @Test
    public void test2() {
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ArrayList<Future<String>> futures = new ArrayList<>();
        for(int i=0; i<50;i++) {
            futures.add(executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(Thread.currentThread().getName() + " is runing");
                    Thread.sleep(10000);
                    System.out.println(Thread.currentThread().getName() + " is finished");
                    return Thread.currentThread().getName();
                }
            }));
            System.out.println("任务" + (i+1) + "已提交");
        }
        System.out.println("all task submit");
        try {
            System.out.println("futures size:" + futures.size());
            for (Future<String> future : futures) {
                System.out.println("future get: " + future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("test2 has end");
    }

    @Test
    public void test1() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory()
                , new BlockingHandler());
        ArrayList<Future<String>> futures = new ArrayList<>();
        for(int i=0; i<10;i++) {
            futures.add(executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(Thread.currentThread().getName() + " is runing");
                    Thread.sleep(10000);
                    System.out.println(Thread.currentThread().getName() + " is finished");
                    return Thread.currentThread().getName();
                }
            }));
            System.out.println("任务" + (i+1) + "已提交");
        }
        System.out.println("all task submit");
        try {
            System.out.println("futures size:" + futures.size());
            for (Future<String> future : futures) {
                System.out.println("future get: " + future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("test1 has end");
    }

}

class BlockingHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if(!executor.isShutdown()) {
            try {
                //应该得队列有位置了才会添加成功，没位置时被阻塞了
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class it {
    public void method(int i,int j){};
}