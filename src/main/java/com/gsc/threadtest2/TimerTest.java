package com.gsc.threadtest2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	// private static Timer timer = new Timer(true);

	private static Timer timer1 = new Timer();

	private static Timer timer2 = new Timer();
	
	private static long l = 0;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static TimerTask task1 = new TimerTask() {

		@Override
		public void run() {
			System.out.println("task1 excuteing,time = "
					+ dateFormat.format(new Date()) + " l=" + ++l);
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	};

	private static TimerTask task2 = new TimerTask() {

		@Override
		public void run() {
			System.out.println("task2 excuteing,time = "
					+ dateFormat.format(new Date()));
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	public static void main(String[] args) {

		String dataStr = "2016-12-24 21:18:00";
		Date time = null;
		try {
			time = dateFormat.parse(dataStr);
			// 如果有多个任务，则执行某任务时，需要前面的队列执行完成（队列）
//			timer1.schedule(task1, time, 2000);
			timer1.scheduleAtFixedRate(task1, time, 120000);
			// timer2.schedule(task2, time);
//			timer1.cancel();
			Thread.sleep(120000);
			System.out.println("main is end!!");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
