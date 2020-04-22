

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
	final Semaphore arrayExpansion = new Semaphore(1,true);
	final Semaphore instantiationLock = new Semaphore(1,true);

	private class Group {

		final Semaphore outerLock = new Semaphore(1, true);
		final Semaphore syncIn = new Semaphore(4,true);
		final Semaphore p3Lock = new Semaphore(4,true);
		final Semaphore syncOut = new Semaphore(0,true);
		final Semaphore numberOfCalls = new Semaphore(4,true);

		private int groupID;

		Group(int id) {this.groupID = id;}
		Group(){}

		private void waitThreads() {
			try {

				// Only allow only 1 thread into the critical area
				outerLock.acquire();

				// phase 3 threads get locked here until 4 threads call finished
				if (phase == Phase.THREE){
					p3Lock.acquire();
				}
				// acquire 1 of 4 permits
				syncIn.acquire();
				while(syncIn.availablePermits() == 0 && syncOut.availablePermits() == 0){
					// all syncIn permits acquired, release 4 permits from the next lock
					syncOut.release(4);
				}
				// let more threads past the outerLock when less than 4 threads are inside
				while(syncIn.availablePermits() != 0 && outerLock.availablePermits() == 0){
					outerLock.release();
				}
				// threads get blocked here until 4 arrive
				syncOut.acquire();

				// release 4 permits (one each thread) to be aqcuired after the outerLock is released
				syncIn.release();

				// the last thread releases the outerLock permit
				// allowing the first of the next 4 threads to enter the sync area
				while (syncOut.availablePermits() == 0 && outerLock.availablePermits() == 0) outerLock.release();

			} catch (Exception e) {
				System.out.println("Semaphore exception " + e.toString());
			}
		}

		private void finished(){
			//

			try{
				numberOfCalls.acquire();
			}catch (Exception e){
				System.out.println(e);
			} finally {
				while (numberOfCalls.availablePermits() == 0) {
					p3Lock.release(4);
					numberOfCalls.release(4);
				}
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

		try {
			while (group.length < groupId+1){
				// 1 thread can modify the Group array at a given time
				try {
					arrayExpansion.acquire();

					// check if the group array can support the new group
					while (group.length < groupId+1) {
						// deepcopy the existing array
						Group tempArray[] = new Group[group.length];
						System.arraycopy(group, 0, tempArray, 0, group.length);

						// create the new array with a sufficient number of group allocations
						group = new Group[groupId + 1 * 2];

						// deepcopy the temp array back into the new one
						System.arraycopy(tempArray, 0, group, 0, tempArray.length);
					}
				} finally {
					arrayExpansion.release();
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());

		} finally {
			while (group[groupId] == null){
				try {
					instantiationLock.acquire();
					while (group[groupId] == null){
						group[groupId] = new SemaphoreSync.Group(groupId);
					}
				} catch (Exception e){
					System.out.println("Exception in instantiate new semaphore group");
				} finally {
					instantiationLock.release();
				}
			}
			group[groupId].waitThreads();
		}


	}
	@Override
	public void finished(int groupId) {
		group[groupId].finished();
	}
}
