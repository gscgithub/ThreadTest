package com.gsc.threadtest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * test
 * lock.getHoldCount()    	                                                  返回当前线程保持此锁的次数。
 * lock.getQueueLength()  	                                                  返回等待获取该锁的线程数
 * lock.getWaitQueueLength(condition)      返回一个 collection，它包含可能正在等待与此锁相关给定条件的那些线程。
 * boolean hasQueuedThread(Thread thread)  查询给定线程是否正在等待获取此锁。 
 * boolean hasQueuedThreads() 			          查询是否有些线程正在等待获取此锁。 
 * boolean hasWaiters(Condition condition) 查询是否有些线程正在等待与此锁有关的给定条件。
 *  
 * @author guoshaocheng
 *
 */
public class ReentrantLockOthersApiTest {

	public static void main(String[] args) {
		
		final ReentrantLock lock = new ReentrantLock();
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					lock.lock();
					//查询当前线程保持此锁的次数。
					System.out.println("HoldCount:" + lock.getHoldCount());
					System.out.println("QueueLength:" + lock.getQueueLength());
//					System.out.println("WaitQueueLength:" + lock.getWaitQueueLength(condition));
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		};
		for(int i = 0;i < 10; i++) {
			new Thread(runnable).start();
		}
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock.lock();
		lock.unlock();
		lock.lock();
		lock.unlock();
		lock.lock();
		lock.unlock();
		//查询当前线程保持此锁的次数。
		System.out.println("\nmain HoldCount:" + lock.getHoldCount());
		//返回等待获取该锁的线程数
		System.out.println("main QueueLength:" + lock.getQueueLength() + "\n");
//		System.out.println("WaitQueueLength:" + lock.getWaitQueueLength(condition));
	}
}
