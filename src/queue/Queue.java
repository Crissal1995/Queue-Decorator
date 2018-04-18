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
	 * @return {@code true} if there isn't any element in the queue,
	 * {@code false} otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Determine if there are as many elements as the queue's size.
	 * 
	 * @return {@code true} if the queue is full,
	 * {@code false} otherwise.
	 */
	public boolean isFull();
	
	/**
	 * Return the size (not the elements' count) of the queue.
	 * 
	 * @return How many elements the queue can contain.
	 */
	public int getSize();
	
	/**
	 * Return the elements' count of the queue.
	 * 
	 * @return How many elements are in the queue. 
	 */
	public int getCount();
	
	/**
	 * Insert an element of type int inside the queue, following
	 * a rule choosen by a wrapper.
	 * 
	 * @param elem The element to insert into the queue.
	 * Follows the object implementation policy.
	 */
	public void insert(int elem);
	
	/**
	 * Retrieve an element of type int from the queue, following
	 * a rule choosen by a wrapper.
	 * 
	 * @return The object to retrieve from the queue. 
	 * Follows the object implementation policy.
	 */
	public int retrieve();
}
