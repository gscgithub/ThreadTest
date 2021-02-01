package com.gsc.threadtest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitSignalTest {

	public static void main(String[] args) {
		ASService service = new ASService();
		new ASThreadA(service).start();
		new ASThreadB(service).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String who = "all";
		System.out.println("signal " + who);
		service.signal(who);
	}
}

class ASService {

	private Lock lock = new ReentrantLock();

	private Condition conditionA = lock.newCondition();

	private Condition conditionB = lock.newCondition();

	public void awaitCA() {

		try {
			System.out.println("method awaitCA begin");
			lock.lock();
			System.out.println("method awaitCA await");
			conditionA.await();
			System.out.println("method awaitCA end");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void awaitCB() {

		try {
			System.out.println("method awaitCB begin");
			lock.lock();
			System.out.println("method awaitCB await");
			conditionB.await();
			System.out.println("method awaitCB end");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void signal(String who) {

		try {
			lock.lock();
			who = who.toLowerCase();
			if (who.equals("a")) {
				conditionA.signalAll();
			} else if (who.equals("b")) {
				conditionB.signalAll();
			} else if (who.equals("all")) {
				conditionA.signalAll();
				conditionB.signalAll();
			} else {
				System.out.println("no one");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

class ASThreadA extends Thread {

	private ASService service;

	public ASThreadA(ASService service) {
		this.service = service;
	}

	@Override
	public void run() {
		service.awaitCA();
	}
}

class ASThreadB extends Thread {

	private ASService service;

	public ASThreadB(ASService service) {
		super();
		this.service = service;
	}

	@Override
	public void run() {
		service.awaitCB();
	}
}
