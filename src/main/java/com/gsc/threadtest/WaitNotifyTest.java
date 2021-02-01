package com.gsc.threadtest;

public class WaitNotifyTest {

	public static void main(String[] args) {
		
		//当一个线程在wait方法处阻塞时，如果获得锁的线程不显式的调用notify，即使获得锁的线程执行完毕，该线程也将一直阻塞
		/*Object lock = new Object();
		Wait1 wait1 = new Wait1(lock);
		new Thread(wait1).start();
		Notify1 notify1 = new Notify1(lock);
		new Thread(notify1).start();*/
		
		testWN1(new Object());
	}
	
	public static synchronized void testWN1(Object lock) {
		
		try {
			System.out.println("testWN1 before wait");
			WaitNotifyTest.class.wait();
			System.out.println("testWN1 after end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class Wait1 implements Runnable{
	
	private Object lock;

	public Object getLock() {
		return lock;
	}

	public void setLock(Object lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		
		try {
			
			System.out.println("wait before synchronized!");
			Thread.sleep(200);
			synchronized (lock) {
				System.out.println("wait begin!");
				lock.wait();
				System.out.println("wait end!");
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public Wait1(Object lock) {
		super();
		this.lock = lock;
	}
}

class Notify1 implements Runnable{
	
	private Object lock;

	public Object getLock() {
		return lock;
	}

	public void setLock(Object lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		
		try {
			synchronized (lock) {
				Thread.sleep(1000);
				System.out.println("notify begin!");
//				lock.wait();
				System.out.println("notify end!");
				
				lock.notify();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public Notify1(Object lock) {
		super();
		this.lock = lock;
	}
}

class WNRun1 {
	
	private Object lock;
	
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {

			synchronized (lock) {
				
			}
		}
	};
}