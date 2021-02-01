package com.gsc.threadtest;

import java.util.ArrayList;
import java.util.List;

public class ProdCustOperQueueTest {

	public static void main(String[] args) {
		new PCOQCustomer().start();
		new PCOQCustomer().start();
		new PCOQCustomer().start();
		new PCOQProduct().start();
		new PCOQProduct().start();
		new PCOQProduct().start();
	}
}

class PCOQueue {
	
	private static List<Object> list = new ArrayList<Object>();
	
	public static void add() {
		synchronized (list) {
			while(list.size() > 0) {
				try {
					System.out.println("add is waiting!");
					list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			list.add("");
			System.out.println("add a object,list's size= " + list.size());
			list.notifyAll();
		}
	}
	
	public static void remove() {
		synchronized (list) {
			while(list.size() < 1) {
				try {
					System.out.println("remove is waiting!");
					list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			list.remove(0);
			System.out.println("remove a object,list's size= " + list.size());
			list.notifyAll();
		}
	}
	
}

class PCOQProduct extends Thread{
	
	@Override
	public void run() {
		while(true) {
			PCOQueue.add();
		}
	}
}

class PCOQCustomer  extends Thread{
	
	@Override
	public void run() {
		while(true) {
			PCOQueue.remove();
		}
	}
}