package queue.wrapper;

import queue.Queue;
import queue.wrapper.exception.*;

/**
 * Wrapper class for Queue Decorator Paradigm.
 * <p>
 * Implements a {@code limit}, defined as the number of how many tasks
 * (i.e. processes and threads) can be at the same time in the waiting set,
 * while tasks that will come when the waiting queue is full will
 * throw an exception.
 * 
 * @author Cristiano Salerno
 */
public class QueueWrapperLimit extends QueueWrapper {
	
	// Maximum amount of thread that can be put in the waiting set.
	// The (limit + 1)th thread will fail to execute, and then throw an exception.
	private int limit;
	
	// Current count of how many threads are inside the waiting set.
	private int count;
	
	// We use another wrapper in order to do the operations, while in this class
	// we will write code just for managing the waiting set.
	private QueueWrapperSync qsync;
	
	public QueueWrapperLimit(Queue q, int num) {
		super(q);
		
		if( num <= 0 ) {
			throw new IllegalArgumentException("Cannot set a limit with "
					+ "a negative (or zero) waiting set maximum.");
		}
		
		qsync = new QueueWrapperSync(q);
		
		limit = num;
		count = 0;
	}
	
	@Override
	public void insert(int elem) {		
		// 1. If the waiting set is full, throws an exception.
		synchronized(this) {
			if( count >= limit ) {
				try {
					throw new TaskLimitReached();
				} catch (TaskLimitReached e) {
					System.out.println("\t\t\t\t[P] TaskLimitReached");
				}
				
				return;
			}
		}

		// 2. We can proceed normally, using the other wrapper we made before.
		// We increment the count, so even if the thread get scheduled out,
		// the incoming threads will know there is a thread pending here.
		synchronized(this) {
			count += 1;
		}

		// 3. Insert the element in the queue, thanks to the wrapper.
		qsync.insert(elem);

		// 4. Decrement the count.
		synchronized(this) {
			count -= 1;
		}
	}

	@Override
	public int retrieve() {
		// 1. If the waiting set is full, throws an exception.
		synchronized(this) {
			if( count >= limit ) {
				try {
					throw new TaskLimitReached();
				} catch (TaskLimitReached e) {
					System.out.println("\t\t\t\t[C] TaskLimitReached");
				}
				
				return -1;
			}
		}

		// 2. We can proceed normally, using the other wrapper we made before.
		// We increment the count, so even if the thread get scheduled out,
		// the incoming threads will know there is a thread pending here.
		synchronized(this) {
			count += 1;
		}

		// 3. Retrieve the element from the queue, thanks to the wrapper.
		int elem = qsync.retrieve();

		// 4. Decrement the count.
		synchronized(this) {
			count -= 1;
		}

		// 5. Return the value obtained.
		return elem;
	}
	
}

