package com.gsc.threadtest;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeThreadTest {

	public static void main(String[] args) {
		PipedInputStream pipedInputStream = new PipedInputStream();
		PipedOutputStream pipedOutputStream = new PipedOutputStream();
		try {
			pipedInputStream.connect(pipedOutputStream);
			new PipedWriteThread(pipedOutputStream).start();
			Thread.sleep(3000);
			new PipedReaderThread(pipedInputStream).start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class PipedReaderThread extends Thread {
	
	private PipedInputStream pipedInputStream;
	
	public PipedReaderThread(PipedInputStream pipedInputStream) {
		super();
		this.pipedInputStream = pipedInputStream;
	}


	@Override
	public void run() {
		
		byte[] bs = new byte[20];
		String readData = "";
		try {
			int index = pipedInputStream.read(bs);
			while(index != -1){
				readData = readData + "," + new String(bs,0,index);
				index = pipedInputStream.read(bs);
				System.out.println("readData:" + readData);
			}
			System.out.println("read end");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pipedInputStream != null) {
				try {
					pipedInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class PipedWriteThread extends Thread {
	
	private PipedOutputStream pipedOutputStream;

	public PipedWriteThread(PipedOutputStream pipedOutputStream) {
		super();
		this.pipedOutputStream = pipedOutputStream;
	}
	
	@Override
	public void run() {

		try {
			for(int i = 1; i <= 300; i++) {
				pipedOutputStream.write(("" + i).getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pipedOutputStream != null) {
				try {
					pipedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}
}