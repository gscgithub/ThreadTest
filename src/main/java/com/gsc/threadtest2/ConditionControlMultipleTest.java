package com.gsc.threadtest2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionControlMultipleTest {

	private static Lock lock = new ReentrantLock();

	private static Condition conditionA = lock.newCondition();

	private static Condition conditionB = lock.newCondition();

	private static Condition conditionC = lock.newCondition();

	private static String signal = "A";

	public static void main(String[] args) {

		Runnable rA = new Runnable() {
			public void run() {
				try {
					lock.lock();
					while (!signal.equals("A")) {
						conditionA.await();
					}
					System.out.println("thread a running");
					conditionB.signalAll();
					signal = "B";
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		};
		Runnable rB = new Runnable() {
			public void run() {
				try {
					lock.lock();
					while (!signal.equals("B")) {
						conditionB.await();
					}
					System.out.println("thread b running");
					conditionC.signalAll();
					signal = "C";
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		};
		Runnable rC = new Runnable() {
			public void run() {
				try {
					lock.lock();
					while (!signal.equals("C")) {
						conditionC.await();
					}
					System.out.println("thread c running");
					conditionA.signalAll();
					signal = "A";
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		};
		for(int i = 0 ; i < 5 ; i++) {
			new Thread(rA).start();
			new Thread(rB).start();
			new Thread(rC).start();
		}
	}
}
