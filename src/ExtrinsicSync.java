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
	private final ReentrantLock[] locks = new ReentrantLock[4];
	private final ReentrantLock releaseLock = new ReentrantLock();
	private final ReentrantLock enterLock = new ReentrantLock();
	private int lockedCounter = 0;
	private int threadsWaiting = 0;

 	// from: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Condition.html
	private final ReentrantLock lock = new ReentrantLock();
	final Condition notFull  = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	private final int threadLimit = 4;
	private final Object[] items = new Object[4];
	private int putptr, takeptr, count;

	// Constructor 
	ExtrinsicSync (Phase p){ 
		this.phase = p; // Phase of testing being performed
		//Fix for Exception errors when locks are in array
		for(int i = 0; i < locks.length; i++){
			locks[i] = new ReentrantLock();
		}
	}

	private void put() {
		lock.lock();
		try {
			//wait if full aka 4 threads in
			while (count == threadLimit)
				try {notFull.await();}catch (Exception e){/* some exception */}
			++count;
				//if count is 4 then call take() method?
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	private Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count != threadLimit)
				notEmpty.await();
			Object x = items[takeptr];
			if (++takeptr == items.length) takeptr = 0;
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}


	@Override
	public void waitForThreads() {

		enterLock.lock();
		lockedCounter ++;
		enterLock.unlock();




		for (int i = 0; i < locks.length; i++){
			if(locks[i].tryLock()){

			}
		}


		//Note to self:- Lock can only be released by the thread that locked it

		//only let 4 threads to go to the loop others wait outside until all 4 threads leave/unlock
//		enterLock.lock();
//		try{
//			while(lockedCounter >= 4){ }
//			lockedCounter++;
//			System.out.println("Locked counter:- " + lockedCounter);
//		} finally {
//			enterLock.unlock();
//		}
//
//
//		//loop through locks if locked to one of them then after unlock break loop
//		for(int i = 0; i < locks.length; i++){
//			//if in if statement then lock acquired
//			if(locks[i].tryLock()){
//				try{
//					while(lockedCounter < 4);
//				} finally {
//					locks[i].unlock();
//					//System.out.println("Thread unlocked:- " + Thread.currentThread().getName());
//					releaseLock.lock();
//					try{
//						releasedCounter++;
//						if(releasedCounter == 4){
//							System.out.println("Resetting counters");
//							releasedCounter = 0;
//							lockedCounter = 0;
//						}
//					} finally {
//						releaseLock.unlock();
//					}
//					//if released 4 threads then reset counters
//					//System.out.println("Thread released incr:- " + releasedCounter);
//				}
//				break;
//			}
//		}


		//STILL LEAVING THIS JUST INCASE
		//check if all locks are locked if unlocked then lock one and skip for loop since only has to lock one
//		loopLock.lock();
//		for(ReentrantLock lock : locks) {
//			if (!lock.isLocked()) {
//				lock.lock();
//				loopLock.unlock();
//				try{
//					lockedCounter++;
//					while (lock.isLocked()) { System.out.println("LOCKED");};
//				} finally {
//					if(lockedCounter >= 4) {
//						loopLock.lock();
//						try {
//							lock.unlock();
//							lockedCounter = 0;
//						} finally {
//							loopLock.unlock();
//						}
//
//					}
//				}
//				continue;
//			}
//		}

		//thread is here if all 4 locks are locked (so actually no need to have lockedCounter?)
		//if all locks are locked unlock them (this should be above for loop so that batch of 4 threads that are unlocked don't execute this if statement)


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
