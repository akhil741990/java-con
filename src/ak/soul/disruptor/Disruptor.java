package ak.soul.disruptor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;



public class Disruptor<T extends QueueEntry> {

		private RingBuffer<T> ringBuffer;
		private Set<Producer<T>> producers;
		private Set<Consumer<T>> consumers;
		
		private  AtomicInteger currentSequence;  
		public Disruptor(int capacity) {
			//TODO : Add check for capacity (its must be power of 2)
			this.ringBuffer = new TaskPoolRingBufferImp<T>(capacity);
			this.currentSequence = new AtomicInteger(-1);
			this.producers = new HashSet<Producer<T>>();
			this.consumers = new HashSet<Consumer<T>>();
		}
		
		public void addConsumer(Consumer<T> consumer){
			this.consumers.add(consumer);
		}
		
		public void addProcuder(Producer<T> producer){
			if(this.producers.size() == 0){
				this.producers.add(producer);
			}else{
				//Current implementation only allows single producer
				System.out.println("Only Single Producer is allowed");	
			}
			
		}
		
		public Disruptor(RingBuffer<T> ringBuffer) {
			this.ringBuffer = ringBuffer;
		}
		
		public RingBuffer<T> getRingBuffer() {
			return ringBuffer;
		}
		
	
		public AtomicInteger getCurrentSequence(){
			return currentSequence;
		}
		
		public int add(T entry){
			int index = this.currentSequence.incrementAndGet();
			int count = 0; 
			for(Consumer<T> consumer : consumers){
				if((consumer.getSequence().get() % this.ringBuffer.getCapacity()) != (index % this.ringBuffer.getCapacity())){
					count++;
				}else{
					// sequence to be claimed is conflicting with one of the consumers
					while(consumer.getSequence().get() != index){ 
						System.out.println(Thread.currentThread().getName()    +":Spinning on Memory Barrier");
					}
					if(!this.ringBuffer.isFull()){
						this.ringBuffer.add(index, entry);
					}else{
						while(this.ringBuffer.isFull()){
							System.out.println(Thread.currentThread().getName()    +":RingBuffer is full, waiting  for free slot");
						}
						this.ringBuffer.add(index, entry);
					}
					break;
				}
			}
			if(count == consumers.size() ){ // sequence to be claimed is not conflicting with any of the Consumers
				
				if(!this.ringBuffer.isFull()){
					this.ringBuffer.add(index, entry);
				}else{
					while(this.ringBuffer.isFull()){
						System.out.println(Thread.currentThread().getName()    +":RingBuffer is full, waiting for free slot");
					}
					this.ringBuffer.add(index, entry);
				}
				
			}
			return index;
		}
		
		
		public T get(Consumer<T> currentConsumer){
			
			if(currentConsumer.getSequence().get() <= this.getCurrentSequence().get()){
				for(Consumer<T> consumer: consumers){
					
					if(currentConsumer != consumer){
						if((currentConsumer.getSequence().get() % this.getRingBuffer().getCapacity()) == (consumer.getSequence().get() % this.ringBuffer.getCapacity())){
							currentConsumer.getSequence().incrementAndGet();
						}
					}
					
					
				}
				
				if(currentConsumer.getSequence().get() <= this.getCurrentSequence().get()){
					return this.ringBuffer.get(currentConsumer.getSequence().get());
				}else{
					while(currentConsumer.getSequence().get() < this.getCurrentSequence().get()){
						System.out.println(Thread.currentThread().getName()    +": Spinning on Memory Barrier");
					}
					return this.ringBuffer.get(currentConsumer.getSequence().get());
				}
									
			}else{
				while(currentConsumer.getSequence().get() >= this.getCurrentSequence().get()){
					System.out.println(Thread.currentThread().getName()    +":Spinning on Memory Barrier");
				}
				return this.ringBuffer.get(currentConsumer.getSequence().get());
			}
			
		}
}
