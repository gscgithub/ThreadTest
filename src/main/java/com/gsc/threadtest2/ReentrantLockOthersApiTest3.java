package com.gsc.threadtest2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockOthersApiTest3 {

	
	//interrupt会让wait和sleep的线程抛InterruptedException，而运行中的线程不抛
	public static void main(String[] args) {
		
		RLOAT3Service service = new RLOAT3Service();
		RLOAT3Thread thread = new RLOAT3Thread(service);
		thread.start();
		
		try {
			thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread.interrupt();
	}
}

class RLOAT3Service {
	
	private Lock lock = new ReentrantLock();
	
	private Condition condition = lock.newCondition();
	
	public void method1() {
		
		try {
			lock.lock();
			System.out.println("has get lock,begin await");
//			condition.await();
//			Thread.sleep(10000);
//			for(int i =0; i < Integer.MAX_VALUE; i++) {
//				Math.random();
//			}
			condition.awaitUninterruptibly();
			System.out.println("finish await");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

class RLOAT3Thread extends Thread {
	
	private RLOAT3Service service = null;
	
	
	public RLOAT3Thread(RLOAT3Service service) {
		super();
		this.service = service;
	}


	@Override
	public void run() {

		service.method1();
	}
}



