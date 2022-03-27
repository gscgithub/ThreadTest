package com.gsc.threadtest3;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ArrayList<Future<Object>> futures = new ArrayList<>();
        for(int i=0; i<3; i++) {
            futures.add(executorService.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    System.out.println(Thread.currentThread().getName() + " is running");
                    try {
                        Thread.sleep(new Random().nextInt(15000));
                        System.out.println(Thread.currentThread().getName() + " sleep end");
                        cyclicBarrier.await();
                        System.out.println(Thread.currentThread().getName() + " is continue running");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }));
        }
        for(int i = 0; i < 3; i++) {
            try {
                futures.get(i).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("main is end");
        executorService.shutdown();
    }
}
