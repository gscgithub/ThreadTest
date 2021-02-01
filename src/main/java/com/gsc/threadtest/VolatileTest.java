package com.gsc.threadtest;

public class VolatileTest {

	//volatile作用是使变量在多个线程间可见
	public static void main(String[] args) {
		
		//测试可见性
		for( int i = 0; i < 100; i++) {
			new Thread(new AtomocTest()).start();
		}
		
		//测试可见性
		/*PrintString printString = new PrintString();
		new Thread(printString).start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("stop thread " + Thread.currentThread().getName());
		printString.setContinuePrint(false);*/
		
	}
	
}

class AtomocTest implements Runnable {
	
	private static int count;

	public synchronized static void addCount() {
		for(int i = 0 ; i < 100; i++) {
			count++;
		}
		System.out.println("count:" + count);
	}
	@Override
	public void run() {
		addCount();
		
	}
	
}
class PrintString implements Runnable {

	private boolean isContinuePrint = true;
	
	public void printString() {
		
		while (isContinuePrint) {
			System.out.println("run printString method,htreadName="
					+ Thread.currentThread().getName());
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public boolean isContinuePrint() {
		return isContinuePrint;
	}

	public void setContinuePrint(boolean isContinuePrint) {
		this.isContinuePrint = isContinuePrint;
	}

	@Override
	public void run() {
		printString();
	}
	
}