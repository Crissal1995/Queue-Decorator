package queue.wrapper;

import queue.Queue;

/**
 * Abstract base class for any Queue Wrapper. Serves as base in the decorator paradigm.
 * <p>
 * For methods "implemented", it's up to the implementation of QueueObject how it's done,
 * while {@code insert(int)} and {@code retrieve()} are methods that 
 * HAVE TO be implemented in the Wrapper, based on its policy.
 * 
 * @author Cristiano Salerno
 */
public abstract class QueueWrapper implements Queue {
	
	/**
	 * The Queue object that will be passed to instance of QueueWrapper descendants.
	 */
	protected Queue queue;
	
	/**
	 * Wrap the argument Queue object, in order to prevent deadlocks and interferences between tasks
	 * that are currently working with the queue.
	 * 
	 * @param queue The Queue object to wrap.
	 */
	public QueueWrapper(Queue queue) {
		this.queue = queue;
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public boolean isFull() {
		return queue.isFull();
	}
	
	public int getSize() {
		return queue.getSize();
	}
	
	public int getCount() {
		return queue.getCount();
	}
	
	public abstract void insert(int elem);
	
	public abstract int retrieve();
}
