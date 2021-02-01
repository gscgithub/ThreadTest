package com.gsc.threadtest;

public class ThreadLocalTest {

	public static void main(String[] args) {
		
		//InheritableThreadLocal可以取到父线程的值
		System.out.println("main's myThreadLocal1:" + TLThread1.myThreadLocal1.get());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main's myInheritableThreadLocal1 " + TLThread1.myInheritableThreadLocal1.get());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		TLThread1 thread1 = new TLThread1();
		thread1.start();
		try {
			thread1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("InheritableThreadLocal~~~ 修改父线程的值，查看子线程的值是否一起改变");
		TLThread1.myInheritableThreadLocal1.set(1111111L);
		
		TLThread1 thread2 = new TLThread1();
		thread2.start();
		
	}
}

class MyThreadLocal1 extends ThreadLocal<Long>{
	
	@Override
	protected Long initialValue() {
		return System.currentTimeMillis();
	}
}

class MyInheritableThreadLocal1 extends InheritableThreadLocal<Long>{
	
	@Override
	protected Long initialValue() {
		return System.currentTimeMillis();
	}
}

class TLThread1 extends Thread {
	
	public static MyThreadLocal1 myThreadLocal1 = new MyThreadLocal1();
	
	public static MyInheritableThreadLocal1 myInheritableThreadLocal1 
												= new MyInheritableThreadLocal1();
	@Override
	public void run() {
		System.out.println("TLThread1's " + myThreadLocal1.get());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("TLThread1's myInheritableThreadLocal1:" + myInheritableThreadLocal1.get());
	}
}