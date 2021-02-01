package com.gsc.threadtest2;

import org.junit.Test;

public class ThreadStateTest {

	public static void main(String[] args) {
		
	}
	
	@Test
	public void test1() {
		StateTestThread1 state = new StateTestThread1();
		System.out.println("original thread state: " + state.getState());
		state.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("execute start thread state: " + state.getState());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("execute start thread state: " + state.getState());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end thread state: " + state.getState());
	}
}

class StateTestThread1 extends Thread {
	
	@Override
	public void run() {
		try {
			for(int i = 0 ; i < 10000; i++) {
				Math.random();
			}
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
