package com.gsc.threadtest2;


public class ThreadGroupTest {

	public static void main(String[] args) {

//		test1();
//		test2();
		test3();
	}

	public static void test3() {
		
		ThreadGroup group1 = new ThreadGroup("group1");
		new TGTThread3(group1,"t1").start();
		new TGTThread3(group1,"t2").start();
		new TGTThread3(group1,"t3").start();
		ThreadGroup group2 = new ThreadGroup(group1,"group2");
		new TGTThread3(group2,"t4").start();
		new TGTThread3(group2,"t5").start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		group1.interrupt();
	}
	
	public static void test2() {
		
		ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
		ThreadGroup childGroup = new ThreadGroup("main's child thread group");
		TGTThread1 tgtThread1 = new TGTThread1();
		Thread t1 = new Thread(childGroup, tgtThread1);
		t1.setName("threadA");
		t1.start();
		ThreadGroup childGroupList[] = 
				new ThreadGroup[mainGroup.activeGroupCount()];
		mainGroup.enumerate(childGroupList);
		System.out.println("main group 有几个活动中的子组：" + childGroupList.length);
		for (ThreadGroup threadGroup : childGroupList) {
			System.out.println("活动中的子组名称：" + threadGroup.getName());
			System.out.println("该组中有几个活动中的线程" + threadGroup.activeCount());
			Thread[] listThreads = new Thread[threadGroup.activeCount()];
			threadGroup.enumerate(listThreads);
			for (Thread thread : listThreads) {
				System.out.println("\t子线程名称：" + thread.getName());
			}
		}
		
	}
	
	public static void test1() {
		ThreadGroup group1 = new ThreadGroup("threadGroup1");
		TGTThread1 tgtThread1 = new TGTThread1();
		TGTThread2 tgtThread2 = new TGTThread2();
		Thread t1 = new Thread(group1, tgtThread1);
		Thread t2 = new Thread(group1,tgtThread2);
		t1.start();
		t2.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("线程组 " + group1.getName()
				+ " 活动的线程数："+ group1.activeCount());
		//和在TGTThread1与TGTThread2的run方法中获取组的的结果是不一样的
		System.out.println("t1 and t2 belong to " + 
				t1.getThreadGroup().getName() + ","
				+ t2.getThreadGroup().getName());
	}
}

class TGTThread1 extends Thread {
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(currentThread().getName() + " finish sleep");
		System.out.println(currentThread().getName() + " belong to " 
				+ getThreadGroup().getName());
	}
}

class TGTThread2 extends Thread {
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(currentThread().getName() + " finish sleep");
		System.out.println(currentThread().getName() + " belong to " 
							+ getThreadGroup().getName());
	}
}

class TGTThread3 extends Thread {
	
	public TGTThread3(ThreadGroup group, String name) {
		super(group, name);
	}

	@Override
	public void run() {

		System.out.println(currentThread().getName() + " begin run");
		while(!isInterrupted()) {
			
		}
		System.out.println(currentThread().getName() + " end");
	}
}
