
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * 
 * Example stub JUnit test for class IntrinsicSync
 * 
 * Note that you need to test each of your 4 classes
 *
 */
class IntrinsicSyncTest {
	//Note it is expected that you may need multiple tests for each phase 
	//Hence the structure below

	private int largestMultipleFour(int x){
		while (x % 4 != 0){
			x--;
		}
		return x;
	}
	
	@Test
	void testPhase1a() {
		IntrinsicSync sync = new IntrinsicSync(Phase.ONE);


		Random rand = new Random();
		int amount = rand.nextInt(2000);
		System.out.println(amount + " threads");
		final int initThreads = amount;


		//Create some threads
		//test method atomic.waitForThreads()
		Thread threads[] = new Thread[initThreads];

		for (int i = 0 ; i < initThreads; i++){
			threads[i] = new Thread(new ThreadTester(sync));
			threads[i].start();
		}


		try{ Thread.sleep(initThreads * 5);} catch (Exception e){System.out.println("Exception "+e.toString());}

		Stack threadStack = new Stack<Thread>();

		for (Thread startedThreads : threads){
			if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
		}

		assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");


	}	
	@Test
	void testPhase1b() {
		IntrinsicSync sync = new IntrinsicSync(Phase.ONE);
		//Create some threads
		//test method sync.waitForThreadsInGroup
		fail("Not yet implemented");
	}	
	//etc
	
	@Test
	void testPhase2a() {
		Random rand = new Random();

		int x = rand.nextInt(50);

		// instantiate a random (even) number of threads
		final int initThreads = (x%2 == 0 ? x : ++x);
		System.out.println("Number of threads: "+initThreads);
		//final int initThreads = 50;
		IntrinsicSync sync = new IntrinsicSync(Phase.TWO);
		Thread threads[] = new Thread[initThreads];

		// Even number required for this thread assignment strategy
		for (int i = 0 ; i < initThreads/2; i++){
			threads[i] = new Thread(new ThreadTester(sync, 0));
			threads[i+(initThreads/2)] = new Thread((new ThreadTester(sync, 1)));
			threads[i].start();
			threads[i+(initThreads/2)].start();
		}

		try{ Thread.sleep(initThreads*2+500);} catch (Exception e){System.out.println("Exception "+e.toString());}

		Stack threadStack = new Stack<Thread>();
		Stack groupZeroStack = new Stack<Thread>();
		Stack groupOneStack = new Stack<Thread>();

		for (Thread startedThreads : threads){
			if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
		}

		for (int i = 0 ; i < initThreads/2; i++){
			if (threads[i].getState() == Thread.State.TERMINATED) groupZeroStack.push(threads[i]);
			if (threads[i+(initThreads/2)].getState() == Thread.State.TERMINATED) groupOneStack.push(threads[i+(initThreads/2)]);
		}

		assertEquals(largestMultipleFour(initThreads/2), groupZeroStack.size(), "Wrong number of threads in group 0");
		assertEquals(largestMultipleFour(initThreads/2), groupOneStack.size(), "Wrong number of threads in group 1");
		assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");
	}
	//etc
	
	@Test
	void testPhase3a() {
		IntrinsicSync sync = new IntrinsicSync(Phase.THREE);
		//Create some threads
		//test method sync.finished
		fail("Not yet implemented");
	}	
	//etc	

}
