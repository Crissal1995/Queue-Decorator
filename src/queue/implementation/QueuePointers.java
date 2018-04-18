package queue.implementation;

import queue.Queue;

/**
 * Actual implementation of Queue interface. It's usable as is, but it's not
 * recommended to use in a multi-threading application, because it doesn't
 * support mutual exclusion and concurrency.
 * For this reason, use it <b>always</b> with a {@link queue.wrapper.QueueWrapper} 
 * (respecting the Decorator Paradigm) if you have a multi-thread application.
 * <p> For a single-thread application, however, there are no concurrency and
 * mutual exclusion to respect and preserve, so there isn't the need of any Wrapper.
 * <p><b>Implementation</b>
 * <p>This Queue is implemented with the use of two pointers; one points to
 * the oldest element in the queue, while the other points to the newest element.
 * Furthermore, the usage of this two pointers follows the FIFO policy, so the 
 * first element inserted in the queue will be also the first to be discarted.
 * 
 * @author Cristiano Salerno
 */
public class QueuePointers implements Queue {
	
	// The actual content of the queue.
	private int[] data;
	
	// The max number of elements the queue can hold.
	private int maxElem;
	
	// The number of how many elements are in the queue.
	private int count;
	
	// The pointer to the oldest element in the queue.
	// Will be the first to be extracted, using a FIFO policy.
	private int oldPtr;
	
	// The pointer to the newest element in the queue.
	private int newPtr;

	public QueuePointers(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Cannot create a " 
					+ "queue with a number negative or zero of elements");
		}

		maxElem = size;
		data = new int[maxElem];

		oldPtr = 0;
		newPtr = 0;
		count = 0;
	}

	@Override
	public boolean isEmpty() {
		return oldPtr == newPtr;
	}

	@Override
	public boolean isFull() {
		return ((newPtr + 1) % maxElem) == oldPtr;
	}

	@Override
	public int getSize() {
		return maxElem;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public void insert(int elem) {
		if (isFull())
			throw new AssertionError();

		data[newPtr] = elem;
		newPtr = (newPtr + 1) % maxElem;

		count += 1;

		// Debug purpose
		System.out.println("[P] Produced " + elem + "; count elems: " + getCount());
	}

	@Override
	public int retrieve() {
		if (isEmpty())
			throw new AssertionError();

		int elem = data[oldPtr];
		oldPtr = (oldPtr + 1) % maxElem;

		count -= 1;

		// Debug purpose
		System.out.println("\t[C] Consumed " + elem + "; count elems: " + getCount());

		return elem;
	}
}
