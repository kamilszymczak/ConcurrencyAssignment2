import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * 
 * Example stub JUnit test for class ExtrinsicSync
 * 
 * Note that you need to test each of your 4 classes
 *
 */
class ExtrinsicSyncTest {
	//Note it is expected that you may need multiple tests for each phase 
	//Hence the structure below
	
	@Test
	void testPhase1a() {
		final int initThreads = 9;
		ExtrinsicSync extrinsic = new ExtrinsicSync(Phase.ONE);

		//Create some threads
		Thread threads[] = new Thread[initThreads];

		for (int i = 0 ; i < initThreads; i++){
			threads[i] = new Thread(new ThreadTester(extrinsic));
			threads[i].start();
		}

		try{ Thread.sleep(initThreads);} catch (Exception e){System.out.println("Exception "+e.toString());}

		Stack threadStack = new Stack<Thread>();

		for (Thread startedThreads : threads){
			if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
		}

		assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");

	}	
	@Test
	void testPhase1b() {
		ExtrinsicSync sync = new ExtrinsicSync(Phase.ONE);
		//Create some threads
		//test method sync.waitForThreadsInGroup
		fail("Not yet implemented");
	}	
	//etc
	
	@Test
	void testPhase2a() {
		ExtrinsicSync sync = new ExtrinsicSync(Phase.TWO);
		//Create some threads
		//test method sync.waitForThreadsInGroup
		fail("Not yet implemented");
	}
	//etc
	
	@Test
	void testPhase3a() {
		ExtrinsicSync sync = new ExtrinsicSync(Phase.THREE);
		//Create some threads
		//test method sync.finished
		fail("Not yet implemented");
	}	
	//etc	

}
