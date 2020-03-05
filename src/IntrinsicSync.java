
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

	//Create enum for switch
	private enum Next {ZERO,ONE,TWO,THREE}
	private Next order = Next.ZERO;

	private Boolean blocked = true;

	private synchronized void zeroHold(){
		while(blocked);
	}

	private synchronized void oneHold(){
		while(blocked);
	}

	private synchronized void twoHold(){
		while(blocked);
	}

	private synchronized void threeHold(){
		blocked = false;
	}

	@Override
	public void waitForThreads() {
		// TODO Auto-generated method stub
		switch (order) {
			case ZERO: zeroHold();
				order = Next.ONE;
				break;
			case ONE: oneHold();
				order = Next.TWO;
				break;
			case TWO: twoHold();
				order = Next.THREE;
				break;
			case THREE: threeHold();
				order = Next.ZERO;
				break;
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
