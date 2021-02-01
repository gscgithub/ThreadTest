package com.gsc.threadtest2;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {

	public static void main(String[] args) {
		
		String operateType1 = "read";
		String operateType2 = "read";
		
		RRWLTService service = new RRWLTService();
		new RRWLTThread(operateType1, service).start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new RRWLTThread(operateType2, service).start();
	}
}

class RRWLTService {
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void method(String operateType) {
		
		try {
			System.out.println(Thread.currentThread().getName() + " try get " 
									+ operateType + " lock");
			if(operateType.equals("read")) {
				lock.readLock().lock();
				System.out.println(Thread.currentThread().getName() + " has get " 
						+ operateType + " lock\t" + System.currentTimeMillis());
				Thread.sleep(3000);
			} else if(operateType.equals("write")){
				lock.writeLock().lock();
				System.out.println(Thread.currentThread().getName() + " has get " 
						+ operateType + " lock\t" + System.currentTimeMillis());
				Thread.sleep(3000);
			} else {
				System.out.println("unknow operate type");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(operateType.equals("read")) {
				lock.readLock().unlock();
			} else if(operateType.equals("write")){
				lock.writeLock().unlock();
			}
		}
	}
	
}

class RRWLTThread extends Thread {
	
	private String operateType;
	
	private RRWLTService service;

	public RRWLTThread(String operateType, RRWLTService service) {
		super();
		this.operateType = operateType;
		this.service = service;
	}


	@Override
	public void run() {
		
		service.method(operateType);
		
	}
}
