

/**
 * 
 * Groups must populate the stubs below in order to implement 
 * the 3 phases of the requirements for this class
 * Note that no other public methods or objects are allow.
 * 
 * Private methods and objects may be used
 * 
 */

import java.util.concurrent.Semaphore;

public class SemaphoreSync implements Synchronisable {

	Phase phase; 

	// Constructor 
	SemaphoreSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
	}

	@Override
	public void waitForThreads() {
		// TODO Auto-generated method stub

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
