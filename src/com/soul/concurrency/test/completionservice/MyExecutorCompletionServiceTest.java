package com.soul.concurrency.test.completionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.soul.concurrency.MyExecutorCompletionService;

public class MyExecutorCompletionServiceTest {

	
	public static void main(String args[]) {
		
		ExecutorService execService = Executors.newFixedThreadPool(16);
		MyExecutorCompletionService<String> compService = new MyExecutorCompletionService(execService);
		
		List<Future<String>> futureList = new ArrayList<>();
		futureList.add(compService.submit(new Task1(15)));
		futureList.add(compService.submit(new Task1(10)));
		futureList.add(compService.submit(new Task1(5)));
		
		for(int i = 0 ;i < futureList.size() ;i ++) {
			try {
				System.out.println("Completed Task result " + compService.take().get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		System.out.println("Program Ended");
	}
}
