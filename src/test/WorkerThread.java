package test;

import queue.*;

public class WorkerThread extends Thread {
	private Queue queue;
	private boolean isConsumer;
	
	public WorkerThread(Queue q, boolean isCons) {
		queue = q;
		isConsumer = isCons;
	}
	
	public void run() {
		if(isConsumer) {
			try {
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			queue.retrieve();
		} else {
			try {
				Thread.sleep( 500 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			int elem = (int)(Math.random() * 100);
			queue.insert(elem);
		}
	}
}