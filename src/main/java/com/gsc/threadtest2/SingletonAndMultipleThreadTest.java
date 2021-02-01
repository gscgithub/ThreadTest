package com.gsc.threadtest2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import org.junit.Test;

public class SingletonAndMultipleThreadTest {

	public static void main(String[] args) {
//		SingletonStaticClass.Testttt;
		
	}
	
	@Test
	public void singletonLazyLoadSerializableTest() {
		
		SingletonLazy object = SingletonLazy.getInstance();
		System.out.println("object hashCode:" + object.hashCode());
		//写入文件
		try {
			FileOutputStream fos = new FileOutputStream(new File("sssFile.txt"));
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//读取对象
		try {
			FileInputStream fis = new FileInputStream(new File("sssFile.txt"));
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(fis);
			SingletonLazy object2 = (SingletonLazy) ois.readObject();
			System.out.println("object2 hashCode:" + object2.hashCode());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void staticSingletonSerializableTest() {
		
		SingletonStaticClass object = SingletonStaticClass.getInstance();
		System.out.println("object hashCode:" + object.hashCode());
		//写入文件
		try {
			FileOutputStream fos = new FileOutputStream(new File("sssFile.txt"));
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//读取对象
		try {
			FileInputStream fis = new FileInputStream(new File("sssFile.txt"));
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(fis);
			SingletonStaticClass object2 = (SingletonStaticClass) ois.readObject();
			System.out.println("object2 hashCode:" + object2.hashCode());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}


class SingletonLazy implements Serializable{
	
	private static final long serialVersionUID = -1L;

	private static SingletonLazy object;
	
	private SingletonLazy() {
		super();
	}


	public static SingletonLazy getInstance() {
		//DCL双重锁检查机制
		if(object == null) {
			synchronized (SingletonLazy.class) {
				if(object == null) {
					object = new SingletonLazy();
				}
			}
		}
		return object;
	}
}

/**
 * 使用静态内置类实现单例模式
 * @author guoshaocheng
 *
 */
class SingletonStaticClass implements Serializable{
	
	private static final long serialVersionUID = -1088923155803333617L;

	private static class SingletonStaticClassHandle {
		private static SingletonStaticClass object = new SingletonStaticClass();
	}
	
	protected static class Testttt {
	}

	private SingletonStaticClass() {
	}
	
	public static SingletonStaticClass getInstance() {
		return SingletonStaticClassHandle.object;
	}
	
	protected Object readResolve() throws ObjectStreamException {
		System.out.println("调用了readResolve方法");
		return SingletonStaticClassHandle.object;
	}
	
}
