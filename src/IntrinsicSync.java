
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

	private final int threadLimit = 4;
	private int count = 0;
	private int released = 0;

	private Boolean blocked = true;

	private synchronized void put() throws InterruptedException {
		while(count == threadLimit){
			wait();
		}

		count++;
		//System.out.println("Passed put" + count);
		take();

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



	@Override
	public void waitForThreads() {

		try {
			put();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
