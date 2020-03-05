


/**
 * 
 * Groups must populate the stubs below in order to implement 
 * the 3 phases of the requirements for this class
 * Note that no other public methods or objects are allow.
 * 
 * Private methods and objects may be used
 * 
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ExtrinsicSync implements Synchronisable {

	Phase phase;
	private final ReentrantLock[] locks = new ReentrantLock[4];
	private int lockedCounter = 0;

	// Constructor 
	ExtrinsicSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
	}

	@Override
	public void waitForThreads() {

		//check if all locks are locked if unlocked then lock one and skip for loop since only has to lock one

		for(ReentrantLock lock : locks) {
			if (!lock.isLocked()) {
				lock.lock();
				try{
					lockedCounter++;
					do {}
					while (lock.isLocked());
				} finally {

				}

				continue;
			}
		}

		//thread is here if all 4 locks are locked (so actually no need to have lockedCounter?)

		//if all locks are locked unlock them (this should be above for loop so that batch of 4 threads that are unlocked don't execute this if statement)
		if(lockedCounter >= 4){
			for(ReentrantLock lock : locks){
				lock.unlock();
			}
			lockedCounter = 0;
		}

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
