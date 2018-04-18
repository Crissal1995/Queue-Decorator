package queue.wrapper;

import queue.Queue;

/**
 * Wrapper class for Queue Decorator Paradigm.
 * <p>
 * Implements Java Built-in Monitors.
 * 
 * @author Cristiano Salerno
 */
public class QueueWrapperSync extends QueueWrapper {
	
	public QueueWrapperSync(Queue q) {
		super(q);
	}

	@Override
	synchronized public void insert(int elem) {
		// 1. We wait until the queue is not full anymore.
		while( queue.isFull() ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 2. We insert the elem in the queue.
		queue.insert(elem);
		
		// 3. We notify ALL threads in the waiting set (because there is
		// just one wait set for every thread, producer and consumer).
		notifyAll();
	}

	@Override
	synchronized public int retrieve() {
		// 0. We instantiate the element to return.
		int elem = -1;
		
		// 1. We wait until the queue is not empty anymore.
		while( queue.isEmpty() ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 2. We retrieve the element from the queue.
		elem = queue.retrieve();
		
		// 3. We notify ALL threads in the waiting set (because there is
		// just one wait set for every thread, producer and consumer).
		notifyAll();
		
		// 4. We return the element we collected from the queue.
		return elem;
	}
	
}
