package queue.wrapper;

import java.util.concurrent.locks.*;
import queue.Queue;

/**
 * Wrapper class for Queue Decorator Paradigm.
 * <p>
 * Implements Java Locks.
 * 
 * @author Cristiano Salerno
 */
public class QueueWrapperLock extends QueueWrapper {
	Lock lock;
	Condition freeSpace;
	Condition usedSpace;
	
	public QueueWrapperLock(Queue q) {
		super(q);
		
		lock = new ReentrantLock();
		freeSpace = lock.newCondition();
		usedSpace = lock.newCondition();
	}
	
	@Override
	public void insert(int elem) {
		// 1. We lock the object, so we are sure that only a thread per time can use the resource.
		lock.lock();
		
		// 2. Then we have to wait for our condition to be true, in order to proceed with code.
		try {
			while(queue.isFull()) {
				try {
					freeSpace.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// 3. We insert the element in the queue.
			queue.insert(elem);
			
			// 4. We signal (not ALL) to consumers [because we have two different waiting set].
			usedSpace.signal();
			
		}  finally {
			// 5. We unlock the object for other threads.
			lock.unlock();
		}

	}
	
	@Override
	public int retrieve() {
		// 0. We set the variable to return further.
		int elem = -1;
		
		// 1. We lock the object, so we are sure that only a thread per time can use the resource.
		lock.lock();
		
		// 2. Then we have to wait for our condition to be true, in order to proceed with code.
		try {
			while(queue.isEmpty()) {
				try {
					usedSpace.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// 3. We retrieve the element from the queue.
			elem = queue.retrieve();
			
			// 4. We signal (not ALL) to producers [because we have two different waiting set].
			freeSpace.signal();
			
		}  finally {
			// 5. We unlock the object for other threads.
			lock.unlock();
		}
		
		// 6. We return the variable collected from the queue.
		return elem;
	}
}
