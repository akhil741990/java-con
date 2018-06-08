package com.soul.concurrency;

public class QNode {

	
	private enum WaitStatus{
		
		CANCELLED(1),
		SIGNLED(2),
		CONDITION(3),
		PROPAGATE(4);
		
		private final int code;
		WaitStatus(final int code) {
			this.code = code;
		}
		
	}
	WaitStatus waitStatus;
	private QNode prev;  // Used to handle cancellation
	private QNode next;  // Used to implement blocking mechanics, the next-links are an optimization so that we don't usually need a backward scan.)
	private QNode tail;
	private QNode head;
	Thread enQueuerThread;
	QNode nextWaiter; 
	
	public QNode(Thread enQueuerThread, WaitStatus status) {
		this.enQueuerThread = enQueuerThread;
		this.waitStatus = status;
	}
	
	public QNode(Thread enQueuerThread, QNode nextWaiter) {
		this.enQueuerThread = enQueuerThread;
		this.nextWaiter = nextWaiter;
	}
	
	
	public void addWaiter(QNode n) {
		
	}
	
	public void unparkSuccessor(QNode n) {
		
	}
	
	public void cancelAquire() {
		
	}
	
	
	public void doReleaseShared() {
		
	}
}
