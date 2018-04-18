package test;
import queue.Queue;

public class WorkerThread extends Thread {
	private Queue queue;
	private boolean isConsumer;
	
	public WorkerThread(Queue q, boolean isCons) {
		queue = q;
		isConsumer = isCons;
	}
	
	public void run() {
		
		if(isConsumer) {
			queue.retrieve();
		} else {
			int elem = (int)(Math.random() * 100);
			queue.insert(elem);
		}
		
		try {
			Thread.sleep( 2000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
