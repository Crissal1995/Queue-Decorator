package test;

import queue.*;
import queue.implementation.*;
import queue.wrapper.*;

public class TestMain {
	public static void main(String[] args) {
		
		// Instantiate a constant variable for modularity.
		final int NUM_THREAD = 100;
				
		// Instantiate a pool of threads.
		Thread[] pool = new Thread[NUM_THREAD];
		
		// Instantiate a Queue object.
		// RHS can be any object that implements Queue interface,
		// so:
		// 	- any Object that implements Queue interface,
		//  - any Wrapper that implements the Queue Decorator Paradigm.
		Queue q = new QueueWrapperLock( new QueueVector(5) );
		
		// Instantiate and start all the NUM_THREAD threads, using the following rule:
		// 		- even i) Consumer threads
		// 		-  odd i) Producer threads
		for(int i = 0; i < NUM_THREAD; ++i) {
			if(i % 2 == 0) {
				pool[i] = new WorkerThread(q, true);
			} else {
				pool[i] = new WorkerThread(q, false);
			}
			
			pool[i].start();
		}
		
		// Join all the NUM_THREAD threads.
		for(int i = 0; i < NUM_THREAD; ++i) {
			try {
				pool[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}