/**
 * 
 * Groups must populate the stubs below in order to implement 
 * the 3 phases of the requirements for this class
 * Note that no other public methods or objects are allow.
 * 
 * Private methods and objects may be used
 * 
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ExtrinsicSync implements Synchronisable {

	Phase phase;

 	// from: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Condition.html
	private final ReentrantLock lock = new ReentrantLock();
	final Condition notFull  = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	private final int threadLimit = 4;
	private int count, realesed = 0;

	// Constructor 
	ExtrinsicSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
	}

	private void put() throws InterruptedException {
		lock.lock();
		try {
			//wait if full aka 4 threads in
			while (count == threadLimit)
				notFull.await();

			++count;
			//System.out.println("Passed put" + count);
			take();

			//when realesed is 4 then means all 4 threads were released can signal to take in next set of 4 threads
			if(realesed == threadLimit){
				count = 0;
				realesed = 0;
				notFull.signalAll();
			}

		} finally {
			lock.unlock();
		}
	}

	private void take() throws InterruptedException {
		lock.lock();
		try {
			//wait until all 4 threads can be taken/released
			while (count < threadLimit)
				notEmpty.await();

			notEmpty.signal();
			realesed++;

			System.out.println("After signal" + realesed);

		} finally {
			lock.unlock();
		}
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
