package com.gsc.threadtest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class SomeTest {

	public static void main(String[] args) {
		
		"".intern();
	}
	
	@Test
	public void test1() {
		
		Lock lock = new ReentrantLock();
		lock.lock();
		lock.lock();
		System.out.println("has get lock");
		lock.unlock();
		lock.unlock();
		System.out.println("has unlock");
	}
}
