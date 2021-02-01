package com.gsc.threadtest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockOthersApiTest2 {

	public static void main(String[] args) {
		
		final ReentrantLock lock = new ReentrantLock(true);
		
		Runnable r1 = new Runnable() {
			public void run() {
				try {
					Thread.sleep(20);
					System.out.println(">>>>>>>>>>>>>>>>>>> try get lock");
					boolean tryLock = lock.tryLock(3, TimeUnit.SECONDS);
					System.out.println(">>>>>>>>>>>>>>>>>>> ttt try Lock:" + tryLock);
//					lock.lock();
//					System.out.println(">>>>>>>>>>>>>>>>>>> ttt has get lock");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
//					if(lock.isHeldByCurrentThread()) {
//						lock.unlock();
//					}
					lock.unlock();
				}
			}
		};
		
		new Thread(r1).start();
		
		Runnable r2 = new Runnable() {
			public void run() {
				try {
					System.out.println("r" + Thread.currentThread().getName() + "\ttry get lock");
					lock.lock();
					System.out.println("r" + Thread.currentThread().getName() + "\thas get lock");
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		};
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(30, Integer.MAX_VALUE, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(60));
		for(int i=0; i<20; i++) {
//			executor.execute(r2);
			Thread thread = new Thread(r2);
			thread.setName("--r" + i);
			thread.start();
			try {
				thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
