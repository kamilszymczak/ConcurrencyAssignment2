

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


	SemaphoreSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
	}
	final Semaphore waiting = new Semaphore(4,true);

	@Override
	public void waitForThreads() {
		// TODO Auto-generated method stub
		try {waiting.acquire();} catch (Exception e){System.out.println("Semaphore exception"+e.toString());}
		while(waiting.availablePermits() > 0){
		}

		waiting.release();
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
