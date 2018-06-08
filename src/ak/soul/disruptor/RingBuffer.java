package ak.soul.disruptor;

public interface RingBuffer<T extends QueueEntry> {

	public T get(int sequence);
	public void add(int sequence, T entry);
	public int getCapacity();
	public boolean isFull();
	public boolean isEmpty();
}
