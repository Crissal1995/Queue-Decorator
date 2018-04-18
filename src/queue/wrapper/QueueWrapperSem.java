package queue.wrapper;

import java.util.concurrent.Semaphore;
import queue.Queue;

/**
 * Wrapper class for Queue Decorator Paradigm.
 * <p>
 * Implements Java Semaphores.
 * 
 * @author Cristiano Salerno
 */
public class QueueWrapperSem extends QueueWrapper {
	private Semaphore freeSpace;
	private Semaphore usedSpace;
	
	public QueueWrapperSem(Queue q) {
		super(q);
		
		freeSpace = new Semaphore(q.getSize());
		usedSpace = new Semaphore(0);
	}

	@Override
	public void insert(int elem) {
		try {
			// 1. We wait until a freeSpace slot is available.
			freeSpace.acquire();
			
			// 2. If we're here, we have the possibility to insert an element into the queue.
			// However, we must handle mutual exclusion, and we have 2 possibilites:
			//	i	-	Use of one (or two) mutex semaphores for producers and consumers thread.
			//	ii	-	Use of synchronized block (equivalent to one mutex for both threads).
			synchronized(queue) {
				queue.insert(elem);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 3. In case of exception or not, we have to release a slot for the other semaphore.
			usedSpace.release();
		}
	}

	@Override
	public int retrieve() {
		// 0. We instantiate the variable to return after.
		int elem = -1;
		
		try {
			// 1. We wait until a usedSpace slot is available.
			usedSpace.acquire();
			
			// 2. If we're here, we have the possibility to retrieve an element into the queue.
			// However, we must handle mutual exclusion, and we have 2 possibilites:
			//	i	-	Use of one (or two) mutex semaphores for producers and consumers thread.
			//	ii	-	Use of synchronized block (equivalent to one mutex for both threads).
			synchronized(queue) {
				elem = queue.retrieve();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 3. In case of exception or not, we have to release a slot for the other semaphore.
			freeSpace.release();
		}
		
		return elem;
	}
}
