
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

	private class Group {
		private final AtomicInteger syncCounter = new AtomicInteger(0);
		private final AtomicInteger leaveCounter = new AtomicInteger(4);
		private final AtomicBoolean outLock = new AtomicBoolean(false);
		private final AtomicBoolean innerLock = new AtomicBoolean(true);
		int groupID;

		Group(int id) {
			this.groupID = id;
		}
		Group(){
		}

		public void waitThreads() {

			while(!outLock.compareAndSet(false, true)){}

			try{
				if (syncCounter.incrementAndGet() < 4) outLock.set(false);

				else innerLock.set(false);

				while (innerLock.get() == true);

			}catch (Exception e){
				System.out.println("Exception in waitForThreads: "+e.toString());
			}finally {
				if (leaveCounter.decrementAndGet() == 0){
					innerLock.set(true);
					syncCounter.set(0);
					leaveCounter.set(4);
					outLock.set(false);
				}
			}
		}
	}

	// Constructor 
	AtomicSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
	}

	private final Group fourThreads = new Group();

	@Override
	public void waitForThreads() {
		fourThreads.waitThreads();
	}



	private Group group[] = new Group[1];

	@Override
	public void waitForThreadsInGroup(int groupId) {
		if (group.length < groupId+1){
			Group tempArray[] = new Group[group.length];
			for(int i = 0; i < group.length; i++){
				tempArray[i] = group[i];
			}

			group = new Group[groupId+1];

			for(int i = 0; i < tempArray.length; i++){
				group[i] = tempArray[i];
			}
		}

		if (group[groupId] == null){
			group[groupId] = new Group(groupId);
		}
		group[groupId].waitThreads();

	}
	@Override
	public void finished(int groupId) {
		// TODO Auto-generated method stub
		// call finished when thread finshes executing its "work"
	}
}

