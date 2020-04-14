

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
	final Semaphore D = new Semaphore(4,true);

	private class Group {

		final Semaphore A = new Semaphore(1, true);
		final Semaphore B = new Semaphore(4,true);
		final Semaphore C = new Semaphore(0,true);
		private int groupID;

		Group(int id) {this.groupID = id;}
		Group(){}

		private void waitThreads() {
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

	}


	SemaphoreSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
	}

	private final Group fourThreads = new SemaphoreSync.Group();

	@Override
	public void waitForThreads() {
		fourThreads.waitThreads();
	}

	private Group group[] = new Group[1];

	@Override
	public void waitForThreadsInGroup(int groupId) {

		// 1 thread can modify the Group array at a given time
		D.acquireUninterruptibly();
		try {
			// check if the group array can support the new group
			if (group.length < groupId+1) {
				// deepcopy the existing array
				SemaphoreSync.Group tempArray[] = new SemaphoreSync.Group[group.length];
				System.arraycopy(group, 0, tempArray, 0, group.length);

				// create the new array with a sufficient number of group allocations
				group = new SemaphoreSync.Group[groupId + 1];

				// deepcopy the temp array back into the new one
				System.arraycopy(tempArray, 0, group, 0, tempArray.length);
			}

		} catch (Exception e) {
			System.out.println(e.toString());

		} finally {
			if(group[groupId] == null){
				group[groupId] = new SemaphoreSync.Group(groupId);
			}

			D.release();
			group[groupId].waitThreads();
		}

	}
	@Override
	public void finished(int groupId) {
		// TODO Auto-generated method stub
	}
}
