
/**
 * 
 * Groups must populate the stubs below in order to implement 
 * the 3 phases of the requirements for this class
 * Note that no other public methods or objects are allow.
 * 
 * Private methods and objects may be used
 * 
 */

/**
 * This implementation must make use of the synchronized keyword,
 * together with the java.lang.Object  methods .wait()
 * and .notifyAll().
 * note that .notify() may also be used as an alternative
 * to .notifyAll().
 * No classes can be used from the java concurrent package
 * (java.util.concurrent).
 * *
 *
 */


public class IntrinsicSync implements Synchronisable {

	Phase phase;

	// Constructor
	IntrinsicSync (Phase p){
		this.phase = p; // Phase of testing being performed
	}

	private class Group{
		private final int threadLimit = 4;
		private int count = 0;
		private int released = 0;
		private int groupID;

		Group(int id) {this.groupID = id;}
		Group(){}

		private void waitThreads(){
			try {
				this.put();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private synchronized void put() throws InterruptedException {
			while(count == threadLimit){
				wait();
			}

			count++;
			//System.out.println("Passed put" + count);
			this.take();

			if(released == 4){
				count = 0;
				released = 0;
				notifyAll();
			}
		}

		private synchronized void take() throws InterruptedException {
			while(count < threadLimit){
				wait();
			}
			notifyAll();
			released++;
		}

	}
	private final Group fourThreads = new Group();

	@Override
	public void waitForThreads() {
		fourThreads.waitThreads();
	}

	private Group[] group = new Group[1];

	@Override
	public void waitForThreadsInGroup(int groupId) {
		// TODO Auto-generated method stub
	}

	private synchronized Group[] expandArray(int groupId){
		// check if the group array can support the new group
		if (group.length < groupId+1){
			// deepcopy the existing array
			Group[] tempArray = new Group[group.length];
			System.arraycopy(group, 0, tempArray, 0, group.length);

			// create the new array with a sufficient number of group allocations
			group = new Group[groupId+1];

			// deepcopy the temp array back into the new one
			System.arraycopy(tempArray, 0, group, 0, tempArray.length);
		}

		return group;
	}




	@Override
	public void finished(int groupId) {
		// TODO Auto-generated method stub
	}
}
