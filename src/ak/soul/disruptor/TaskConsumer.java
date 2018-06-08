package ak.soul.disruptor;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskConsumer implements Consumer<TaskPoolQueueEntry>{
	
	private AtomicInteger sequence;
	private Disruptor<TaskPoolQueueEntry> disruptor;
	
	public TaskConsumer(Disruptor<TaskPoolQueueEntry> disruptor) {
		this.sequence = new AtomicInteger(-1);
		this.disruptor = disruptor;
	}
	
	public AtomicInteger getSequence(){
		return this.sequence;
	}

	@Override
	public TaskPoolQueueEntry consume() {
		sequence.incrementAndGet();
		TaskPoolQueueEntry entry = this.disruptor.get(this); 
		return entry;
	}
	
}
