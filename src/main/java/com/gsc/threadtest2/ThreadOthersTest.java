package com.gsc.threadtest2;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadOthersTest {

	/**
	 * 异常处理方法
	 * 一：线程调用setUncaughtExceptionHandler方法
	 * 二：线程类调用setDefaultUncaughtExceptionHandler方法
	 * 三：实现ThreadGroup类，重写uncaughtException方法
	 * @param args
	 */
	public static void main(String[] args) {
		
		test3();
	}
	
	/**
	 * 测试三种异常都执行的优先级顺序
	 * 
	 * 三者都有只执行setUncaughtExceptionHandler
	 * 
	 * setDefaultUncaughtExceptionHandler
	 * 		由ThreadGroup的super.uncaughtException(t, e)调用
	 * 
	 * @return
	 */
	public static void test3() {
		
		//setDefaultUncaughtExceptionHandler
		TOTThread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("caught thread exception by"
						+ " setDefaultUncaughtExceptionHandler");
				e.printStackTrace();
			}
		});
		
		//ThreadGroup
		TOTThreadGroup group = new TOTThreadGroup("my ThreadGroup");
		
		//setUncaughtExceptionHandler
		TOTThread t1 = new TOTThread(group,"thread1");
//		TOTThread t1 = new TOTThread();
		/*t1.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("caught thread exception by"
						+ " setUncaughtExceptionHandler");
				e.printStackTrace();
			}
		});*/
		t1.start();
	}

	public static void test2() {
		TOTThread t1 = new TOTThread();
		t1.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("caught thread exception");
				e.printStackTrace();
			}
		});
		t1.start();
	}
	
	public static void test1() {
		TOTThread t1 = new TOTThread();
		t1.start();
	}
}

class TOTThread extends Thread {
	
	
	public TOTThread() {
		super();
	}

	public TOTThread(ThreadGroup group, String name) {
		super(group, name);
	}

	@Override
	public void run() {
		String str = null;
		System.out.println(str.hashCode());
	}
}

class TOTThreadGroup extends ThreadGroup {

	public TOTThreadGroup(String name) {
		super(name);
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		super.uncaughtException(t, e);
		System.out.println("caught exception by ThreadGroup");
		e.printStackTrace();
		this.interrupt();
	}
	
}