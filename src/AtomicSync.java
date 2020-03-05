
/**
 * 
 * Groups must populate the stubs below in order to implement 
 * the 3 phases of the requirements for this class
 * Note that no other public methods or objects are allow.
 * 
 * Private methods and objects may be used
 * 
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicSync implements Synchronisable {

	Phase phase;
	private final AtomicInteger callCounter = new AtomicInteger(0);
	private final AtomicBoolean locked = new AtomicBoolean(true);

	// Constructor 
	AtomicSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
	}

	@Override
	public void waitForThreads() {

		// for every thread that calls waitForThreads() method
		// lock the thread until exactly 4 threads are locked when that takes place unlock the batch of 4 threads
		if(callCounter.incrementAndGet() < 4){
			locked.set(true);
			//lock threads here
			while(locked.get() == true);

			//set counter to 0
			callCounter.set(0);

		}else locked.set(false);

	}
	@Override
	public void waitForThreadsInGroup(int groupId) {
		// TODO Auto-generated method stub

	}
	@Override
	public void finished(int groupId) {
		// TODO Auto-generated method stub
	}
}

