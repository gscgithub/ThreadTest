package com.gsc.threadtest;

public class JoinInterruptTest {

	public static void main(String[] args) {
		
		JITThread1 jitThread1 = new JITThread1();
		jitThread1.start();
		
		JITThread2 jitThread2 = new JITThread2(jitThread1);
		jitThread2.start();
		
		try {
			Thread.sleep(10000);
			jitThread1.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class JITThread1 extends Thread {
	@Override
	public void run() {
		while(true) {
			System.out.println("JITThread1 is running!");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class JITThread2 extends Thread {
	
	private JITThread1 jThread1;
	
	
	public JITThread2(JITThread1 jThread1) {
		super();
		this.jThread1 = jThread1;
	}


	@Override
	//重写方法前边可以加synchronize
//	public synchronized void run() {
	public void run() {
		System.out.println("JITThread2 is running!");
		try {
			jThread1.join();
			System.out.println("JITThread2 is end in TRY!");
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("JITThread2 is end in CATHC!");
		}
	}
}