

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
	final Semaphore A = new Semaphore(1, true);
	final Semaphore B = new Semaphore(4,true);
	final Semaphore C = new Semaphore(0,true);

	@Override
	public void waitForThreads() {
		try {

			A.acquire();

			B.acquire();
			if(B.availablePermits() == 0){
				C.release(4);
			} else {
				A.release();
			}
			C.acquire();
			B.release();
			if (C.availablePermits() == 0) A.release();

		} catch (Exception e) {
			System.out.println("Semaphore exception " + e.toString());
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
