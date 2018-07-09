package com.soul.concurrency;


import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

public class MyExecutorCompletionService<V> implements CompletionService<V> {

	
	private final BlockingQueue<Future<V>> completionQ ;
	private final Executor exec;
	private final AbstractExecutorService aexec;
	
	public MyExecutorCompletionService(Executor exec, BlockingQueue<Future<V>> completionQ){
		if(exec == null || completionQ == null) {
			throw new NullPointerException();
		}
		this.exec = exec;
		this.completionQ = new LinkedBlockingQueue<>();
		this.aexec = exec instanceof AbstractExecutorService? (AbstractExecutorService)exec : null;
	}
	
	
	public MyExecutorCompletionService(Executor exec){
		if(exec == null) {
			throw new NullPointerException();
		}
		this.exec = exec;
		this.completionQ = new LinkedBlockingQueue<>();
		this.aexec = exec instanceof AbstractExecutorService? (AbstractExecutorService)exec : null;
	}
	
	
	
	private class TaskCompletionResultEnqueuer extends FutureTask<V>{

		private final Future<V> task;
		TaskCompletionResultEnqueuer(RunnableFuture<V> runnableFuture) {
			super(runnableFuture, null);
			this.task = runnableFuture;
		}
		protected void done() {
			completionQ.add(task);
		}
		
	}
	
	private RunnableFuture<V> newTaskFor(Callable<V> task){
			return new FutureTask<V>(task);
	}
	
	
	private RunnableFuture<V> newTaskFor(Runnable task, V result){
		return new FutureTask<V>(task,result);
}
	
	
	@Override
	public Future<V> submit(Callable<V> task) {
		if(task == null ) {
			throw new NullPointerException();
		}
		RunnableFuture<V> f = newTaskFor(task);
		exec.execute(new TaskCompletionResultEnqueuer(f));
		return f;
	}

	@Override
	public Future<V> submit(Runnable task, V result) {
		if(task == null) {
			throw new NullPointerException();
		}
		RunnableFuture<V> f = newTaskFor(task, result);
		exec.execute(new TaskCompletionResultEnqueuer(f));
		return f;
	}

	@Override
	public Future<V> take() throws InterruptedException {
		return this.completionQ.take();
	}

	@Override
	public Future<V> poll() {
		return this.completionQ.poll();
	}

	@Override
	public Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException {
		return this.completionQ.poll(timeout, unit);
	}

}
