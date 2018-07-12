package com.soul.concurrency.test.completionservice;

import java.sql.Time;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task1 implements Callable<String>{

	int waitPeriod = 10;
	public Task1(int waitPeriod) {
		this.waitPeriod = waitPeriod;
	}
	@Override
	public String call() throws Exception {
		
		System.out.println(Thread.currentThread().getName() + " : Processing will be completed in " + waitPeriod + "seconds");
		TimeUnit.SECONDS.sleep(waitPeriod);
		return  Thread.currentThread().getName() +" : " + System.currentTimeMillis() ;
	}

}
