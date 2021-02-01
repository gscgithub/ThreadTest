package com.gsc.threadtest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * test lockInterruptibly
 * @author guoshaocheng
 *
 */
public class ReentrantLockOthersApiTest1 {

	public static void main(String[] args) {
		
		final RLOAService service = new RLOAService();
		Runnable runnable = new Runnable() {
			public void run() {
				service.method1();
			}
		};
		
		Thread threadA = new Thread(runnable);
		threadA.setName("threadA");
		threadA.start();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread threadB = new Thread(runnable);
		threadB.setName("threadB");
		threadB.start();
		
		threadB.interrupt();
		
		System.out.println("main end!");
		
	}
}

class RLOAService {
	
	private Lock lock = new ReentrantLock();
	
	public void method1() {
		
		try {
			
			System.out.println(Thread.currentThread().getName() + " begin run");
//			lock.lock();
			lock.lockInterruptibly();
			for(int i=0; i< Integer.MAX_VALUE; i++) {
				System.out.println(Thread.currentThread().getName() + ",i=" + i + ",random=" + Math.random());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
	}
}







