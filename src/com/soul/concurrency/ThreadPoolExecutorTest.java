package com.soul.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
	public static void main(String args[]) throws InterruptedException {
		
		Executors.newFixedThreadPool(16);
		ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 16, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		pool.execute(new Runnable() {
			
			@Override
			public void run() {
				 System.out.println(Thread.currentThread().getName());
				System.out.println("Hello");
			}
		});
		
		Thread.sleep(1000*5);
		
		pool.execute(new Runnable() {
			
			@Override
			public void run() {
				 System.out.println(Thread.currentThread().getName());
				System.out.println("How");
			}
		});
		
		Thread.sleep(1000*5);
		
		pool.execute(new Runnable() {
			
			@Override
			public void run() {
				 System.out.println(Thread.currentThread().getName());
				System.out.println("are ");
			}
		});
		
		
		Thread.sleep(1000*5);
		
		
		pool.execute(new Runnable() {
			
			@Override
			public void run() {

				System.out.println("are ");
			}
		});
		
	}
}
