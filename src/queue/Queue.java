package queue;

/**
 * This interface abstracts the methods associated with a queue.
 * Its type is {@code int}, but with a little more work on it, we can make
 * it generic (aka Template in C++). 
 * <p>
 * A queue is an array-style abstract data type, with the possibility of
 * scrolling the content forward or reverse, and with one or more iterators.
 * 
 * @author Cristiano Salerno
 */
public interface Queue {
	/**
	 * Determine if there are no elements in the queue.
	 * 
	 * @return A boolean, indicating if there are no elements in the queue (true),
	 * or there is at least one element (false).
	 */
	public boolean isEmpty();
	
	/**
	 * Determine if there are as many elements as the queue's size.
	 * 
	 @return A boolean, indicating if there are as many elements as the queue size (true),
	 * or it there is at least one free spot (false).
	 */
	public boolean isFull();
	
	/**
	 * Return the size (not the elements' count) of the queue.
	 * 
	 * @return An int, indicating how many elements the queue can contain.
	 */
	public int getSize();
	
	/**
	 * Return the elements' count of the queue.
	 * 
	 * @return An int, indicating how many elements are in the queue. 
	 */
	public int getCount();
	
	/**
	 * Insert an element of type int inside the queue, following
	 * a rule choosen by a wrapper.
	 * 
	 * @param elem The element to insert into the queue, following the policy
	 * of the object that implements the interface.
	 */
	public void insert(int elem);
	
	/**
	 * Retrieve an element of type int from the queue, following
	 * a rule choosen by a wrapper.
	 * 
	 * @return The object to retrieve from the queue, following the policy
	 * of the object that implements the interface.
	 */
	public int retrieve();
}
