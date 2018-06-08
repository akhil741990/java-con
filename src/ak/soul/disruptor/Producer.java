package ak.soul.disruptor;

public interface Producer<T extends QueueEntry> {
	public int getSequence();
	public void produce(T entry);
}
