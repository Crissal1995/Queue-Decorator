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
 * <p>This Queue is implemented with the use of a State Vector, an array
 * with the same size as the queue, but with type {@code State}, in order to
 * monitor the state of every queue slot.
 * 
 * @author Cristiano Salerno
 */
public class QueueVector implements Queue {
	
	// Enum used for descerning state into the state vector.
	private enum State {
		EMPTY, FULL, USING
	}
	
	// The actual content of the queue.
	private int[] data;
	
	// The state vector, for managing slots of the queue.
	private State[] vector;
	
	// The max number of elements the queue can hold.
	private int maxElem;
	
	// The number of how many elements are in the queue.
	private int count;
	
	public QueueVector(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Cannot create a " 
					+ "queue with a non-positive number of elements");
		}

		maxElem = size;
		data = new int[maxElem];
		vector = new State[maxElem];
		
		count = 0;
		
		for(int i = 0; i < maxElem; ++i)
			vector[i] = State.EMPTY;
	}
	
	/**
	 * Find the first slot in the state vector, such that state searched and state vector 
	 * in the found slots are equal. 
	 * @param state The state to search in the state vector.
	 * @return The position in the state vector.
	 */
	private int find(State state) {
		// Protection against wrong usage.
		if(state == State.USING) {
			throw new IllegalArgumentException("Cannot "
					+ "search for an USING state");
		}
		
		// Search the whole state vector.
		for(int i = 0; i < maxElem; ++i) {
			if(vector[i] == state) {
				return i;
			}
		}
		
		// If not found, return a negative value.
		return -1;
	}
	
	@Override
	public boolean isEmpty() {
		return count == 0;
	}

	@Override
	public boolean isFull() {
		return count == maxElem;
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
		// If we are here, but the queue is full, the wrapper is not doing well its job.
		if (isFull())
			throw new AssertionError();
		
		// If we are here, we are sure that there will be a free slot.
		int position = find(State.EMPTY);
		
		// Unuseful statement.
		// While we're inside this block, thanks to the wrapper,
		// all the code will be executed in mutual exclusion, 
		// so there is no need to modify the state position 
		// found beforehand with 'find()' into USING.
		//
		// vector[position] = State.USING;
		
		// We write in that position our element.
		data[position] = elem;
		
		// We modify the state of the slot in which we writed.
		vector[position] = State.FULL;

		// Increment the count of elems in the queue.
		count += 1;

		// Debug purpose
		System.out.println("[P] Produced " + elem + "; count elems: " + getCount());
	}

	@Override
	public int retrieve() {
		if (isEmpty())
			throw new AssertionError();
		
		int position = find(State.FULL);

		int elem = data[position];
		
		vector[position] = State.EMPTY;

		count -= 1;

		// Debug purpose
		System.out.println("\t[C] Consumed " + elem + "; count elems: " + getCount());

		return elem;
	}
	
}
