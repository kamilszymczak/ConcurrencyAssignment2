import org.junit.jupiter.api.Test;

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
		ExtrinsicSync sync = new ExtrinsicSync(Phase.ONE);
		//Create some threads
		//test method sync.waitForThreads()
		
		fail("Not yet implemented");
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
