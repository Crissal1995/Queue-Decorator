package queue.wrapper.exception;

/**
 * The custom exception launched from the class {@link queue.wrapper.QueueWrapperLimit}
 * when the number of tasks in the waiting set is greather than (or equal to) the
 * {@code limit} that we have specified.
 * 
 * @author Cristiano Salerno
 */
public class TaskLimitReached extends Exception {

	private static final long serialVersionUID = 7469587493604534775L;

}
