package queue.implementation;

import queue.Queue;

/**
 * Actual implementation of Queue interface. It's usable as is, but it's not
 * recommended to use in a multi-threading application, because it doesn't
 * support mutual exclusion and concurrency.
 * <p>
 * For this reason, use it ALWAYS with a {@link queue.wrapper.QueueWrapper} 
 * (respecting the Decorator Paradigm) if you have a multi-thread application;
 * for a single-thread application, however, there are no concurrency and
 * mutual exclusion to respect and preserve, so there isn't the need of a Wrapper.
 * 
 * @author Cristiano Salerno
 */
public class QueueObject implements Queue {
	private int[] data;
	private int maxElem;
	private int oldPtr;
	private int newPtr;
	private int count;

	public QueueObject(int size) {
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
