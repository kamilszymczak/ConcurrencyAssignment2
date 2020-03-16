
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
		IntrinsicSync sync = new IntrinsicSync(Phase.TWO);
		//Create some threads
		//test method sync.waitForThreadsInGroup
		fail("Not yet implemented");
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
