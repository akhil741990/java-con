package ak.soul.disruptor.test;

import ak.soul.disruptor.Disruptor;
import ak.soul.disruptor.TaskConsumer;
import ak.soul.disruptor.TaskPoolQueueEntry;
import ak.soul.disruptor.TaskProducer;

public class DisruptorTest {

		public static void main(String args[]){
			Disruptor<TaskPoolQueueEntry> disruptor = new Disruptor<>(10);
			
			
			TaskProducer p = new TaskProducer(disruptor);
			TaskConsumer c1 = new TaskConsumer(disruptor);
			TaskConsumer c2 = new TaskConsumer(disruptor);
			TaskConsumer c3 = new TaskConsumer(disruptor);
			
			disruptor.addProcuder(p);
			disruptor.addConsumer(c1);
			disruptor.addConsumer(c2);
			disruptor.addConsumer(c3);
			
			
			Thread producerThread =  new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("Producer Thread  started");
					while(true){
						TaskPoolQueueEntry t = new TaskPoolQueueEntry(); 
						disruptor.add(t)	;
						System.out.println("Produced "+ t);
						try {
							Thread.sleep(1000*2);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
						
				}
			});
			
			producerThread.setName("ProducerThread");
			
			Thread consumerThread1 =  new Thread(new Runnable() {
				
				@Override
				public void run() {
				
					System.out.println("Consumer Thread 1 started");
					while(true){
						
						TaskPoolQueueEntry t = disruptor.get(c1);
						System.out.println("Consumer Thread 1 Consumed "+t);
						try {
							Thread.sleep(1000*10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
			consumerThread1.setName("ConsumerThread1");
			Thread consumerThread2 =  new Thread(new Runnable() {
				
				@Override
				public void run() {
				
					System.out.println("Consumer Thread 2 started");
					while(true){
						
						TaskPoolQueueEntry t = disruptor.get(c1);
						System.out.println("Consumer Thread 2 Consumed "+t);
						try {
							Thread.sleep(1000*20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
		
			consumerThread2.setName("ConsumerThread2");
			Thread consumerThread3 =  new Thread(new Runnable() {
				
				@Override
				public void run() {
				
					System.out.println("Consumer Thread 3 started");
					while(true){
						
						TaskPoolQueueEntry t =   disruptor.get(c1);
						System.out.println("Consumer Thread 3 Consumed "+t);
						try {
							Thread.sleep(1000*30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		
			consumerThread3.setName("ConsumerThread3");
			//Starting Consumers
			
			consumerThread1.start();
			consumerThread2.start();
			consumerThread3.start();
			
			
			// Strating Producer Thread
			
			producerThread.start();
			
			
			try {
				producerThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		
		

		
}
