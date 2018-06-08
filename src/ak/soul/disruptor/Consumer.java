package ak.soul.disruptor;

import java.util.concurrent.atomic.AtomicInteger;

public interface Consumer<T extends QueueEntry> {

	public AtomicInteger getSequence(); 
	public T consume();
	
}
