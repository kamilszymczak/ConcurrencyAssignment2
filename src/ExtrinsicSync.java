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

	// Constructor 
	ExtrinsicSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
	}

	private class Group {
		// from: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Condition.html
		private final ReentrantLock lock = new ReentrantLock();
		private final Condition notFull  = lock.newCondition();
		private final Condition notEmpty = lock.newCondition();

		private final int threadLimit = 4;
		private int count, realesed = 0;

		private int groupID;

		Group(int id) {
			this.groupID = id;
		}

		Group(){}

		private void waitThreads(){
			try {
				put();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		private void put() throws InterruptedException {
			lock.lock();
			try {
				//block thread if 4 threads already waiting
				if (count == threadLimit)
					notFull.await();

				++count;
				//System.out.println("Passed put" + count);
				take();

				//when released is 4 signal to unblock threads until 4 more threads are inside take()
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
				if (count < threadLimit)
					notEmpty.await();

				notEmpty.signalAll();
				realesed++;

				System.out.println("After signal" + realesed);

			} finally {
				lock.unlock();
			}
		}
	}

	private final Group fourThreads = new Group();

	@Override
	public void waitForThreads() {
		// each thread accesses the waitThreads() method from the fourThreads Group class
		fourThreads.waitThreads();
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
