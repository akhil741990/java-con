package ak.soul.disruptor;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskProducer implements Producer<TaskPoolQueueEntry> {
	private AtomicInteger sequence;
	private Disruptor<TaskPoolQueueEntry> disruptor;
	
	public TaskProducer(Disruptor<TaskPoolQueueEntry> disruptor) {
		this.sequence = new AtomicInteger(-1);
		this.disruptor = disruptor;
	}
	public int getSequence(){
		return this.sequence.get();
	}
	
	
	@Override
	public void produce(TaskPoolQueueEntry entry) {
		this.sequence.set(this.disruptor.add(entry));
	}
	
}
