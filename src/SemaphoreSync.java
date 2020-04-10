

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

	private class Group {
		final Semaphore Groups = new Semaphore(4,true);
		private int groupID;

		Group(int id) {this.groupID = id;}
		Group(){}
	}


	SemaphoreSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
	}
	final Semaphore waiting = new Semaphore(4,true);
	final Semaphore empty = new Semaphore(1,true);

	@Override
	public void waitForThreads() {
		try {

			waiting.acquire();
			while(empty.availablePermits() == 0){}

			//Thread inside when here

			while (waiting.availablePermits() != 0);
			empty.tryAcquire();

			waiting.release();
			//Thread out when here
			if(waiting.availablePermits() == 4){
				empty.release();
			}

		} catch (Exception e) {
			System.out.println("Semaphore exception " + e.toString());
		} finally {
			waiting.release();
		}
	}

	private Group group[] = new Group[1];

	@Override
	public void waitForThreadsInGroup(int groupId) {
		// TODO Auto-generated method stub


		

	}
	@Override
	public void finished(int groupId) {
		// TODO Auto-generated method stub
	}
}
