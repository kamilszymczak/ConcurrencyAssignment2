

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
	final Semaphore D = new Semaphore(1,true);
	final Semaphore E = new Semaphore(1,true);

	private class Group {

		final Semaphore outerLock = new Semaphore(1, true);
		final Semaphore syncIn = new Semaphore(4,true);
		final Semaphore F = new Semaphore(4,true);
		final Semaphore syncOut = new Semaphore(0,true);
		private int groupID;

		Group(int id) {this.groupID = id;}
		Group(){}

		private void waitThreads() {
			try {

				outerLock.acquire();

				syncIn.acquire();
				if(syncIn.availablePermits() == 0){
					syncOut.release(4);
				} else {
					outerLock.release();
				}
				syncOut.acquire();
				syncIn.release();
				if (syncOut.availablePermits() == 0) outerLock.release();

			} catch (Exception e) {
				System.out.println("Semaphore exception " + e.toString());
			}
		}

		private void finished(){
			F.acquireUninterruptibly();
			try {
				if (F.availablePermits() == 0 && phase == Phase.THREE);
				//	System.out.println("in finished");}
			}catch (Exception e) {
				System.out.println(e.toString());
			}finally {
				F.release();
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
			if (group.length < groupId+1){
				// 1 thread can modify the Group array at a given time
				try {
					D.acquire();

					// check if the group array can support the new group
					if (group.length < groupId+1) {
						// deepcopy the existing array
						Group tempArray[] = new Group[group.length];
						System.arraycopy(group, 0, tempArray, 0, group.length);

						// create the new array with a sufficient number of group allocations
						group = new Group[groupId + 1 * 2];

						// deepcopy the temp array back into the new one
						System.arraycopy(tempArray, 0, group, 0, tempArray.length);
					}
				} finally {
					D.release();
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());

		} finally {
			if(group[groupId] == null){
				try {
					E.acquire();
					if(group[groupId] == null){
						group[groupId] = new SemaphoreSync.Group(groupId);
					}
				} catch (Exception e){
					System.out.println("Exception in instantiate new semaphore group");
				} finally {
					E.release();
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
