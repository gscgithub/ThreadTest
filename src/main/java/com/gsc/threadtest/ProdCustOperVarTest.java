package com.gsc.threadtest;

/**
 * 消费者与生产者操作变量实例
 * @author guoshaocheng
 *
 */
public class ProdCustOperVarTest {

	public static void main(String[] args) {
		
		Object lock = new Object();
		Product product = new Product(lock);
		ProdThread prodThread1 = new ProdThread(product);
		ProdThread prodThread2 = new ProdThread(product);
		prodThread1.setName("productThread1");
		prodThread2.setName("productThread2");
		prodThread1.start();
		prodThread2.start();
		
		Customer customer = new Customer(lock);
		CustomerThread customerThread1 = new CustomerThread(customer);
		CustomerThread customerThread2 = new CustomerThread(customer);
		customerThread1.setName("customerThread1");
		customerThread2.setName("customerThread2");
		customerThread1.start();
		customerThread2.start();
		
		
	}
}

class ValueClass {
	
	public static String value = ""; 
}

class Product {

	private Object lock;

	public Object getLock() {
		return lock;
	}

	public void setLock(Object lock) {
		this.lock = lock;
	}
	
	public void prod() {
		
		synchronized (lock) {
			while(true) {
				while(!ValueClass.value.equals("")) {
					try {
						System.out.println(Thread.currentThread().getName() + " will wait☆");
						lock.wait();
						System.out.println(Thread.currentThread().getName() + " wake");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println(Thread.currentThread().getName() + " will product");
				ValueClass.value = "product";
				lock.notifyAll();
			}
			
			
		}
	}

	public Product(Object lock) {
		super();
		this.lock = lock;
	}
	
	
}

class Customer {

	private Object lock;

	public Object getLock() {
		return lock;
	}

	public void setLock(Object lock) {
		this.lock = lock;
	}
	
	public void consume() {
		
		while(true) {
			synchronized (lock) {
				while(ValueClass.value.equals("")) {
					try {
						System.out.println(Thread.currentThread().getName() + " will wait☆");
						lock.wait();
						System.out.println(Thread.currentThread().getName() + " wake");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println(Thread.currentThread().getName() + " will consume");
				ValueClass.value = "";
				lock.notifyAll();
				
			}
		}
		
	}

	public Customer(Object lock) {
		super();
		this.lock = lock;
	}
	
}

class ProdThread extends Thread {
	
	private Product product;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProdThread(Product product) {
		super();
		this.product = product;
	}

	@Override
	public void run() {
		product.prod();
	}
}

class CustomerThread extends Thread {
	
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public void run() {
		customer.consume();
	}

	public CustomerThread(Customer customer) {
		super();
		this.customer = customer;
	}
}


