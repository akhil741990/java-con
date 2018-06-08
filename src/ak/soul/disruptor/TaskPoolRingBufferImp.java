package ak.soul.disruptor;

import java.util.concurrent.atomic.AtomicInteger;


public class TaskPoolRingBufferImp<T extends QueueEntry> implements RingBuffer<T>{
	
	
	private int capacity;
	private AtomicInteger currentSize;
	private QueueEntry[] buffer;
	
	TaskPoolRingBufferImp(int capacity){
		this.capacity = capacity;
		this.currentSize = new AtomicInteger(0);
		buffer = new QueueEntry[capacity];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int sequence) {
		this.currentSize.decrementAndGet();
		System.out.println();
		System.out.println("Entry removed from index :"+(sequence%capacity));
		return (T) buffer[sequence%capacity];
	}

	@Override
	public void add(int sequence, T entry) {
		this.currentSize.incrementAndGet();
		buffer[sequence % capacity] = entry;
		System.out.println("Entry added @ index "+(sequence % capacity));
	}

	@Override
	public int getCapacity() {
		return this.capacity;
	}

	@Override
	public boolean isFull() {
		if(this.currentSize.get() == this.capacity)
			return true;
		return false;
	}

	@Override
	public boolean isEmpty() {
		if(this.currentSize.get() == 0)
			return true;
		return false;
	}

	

}
