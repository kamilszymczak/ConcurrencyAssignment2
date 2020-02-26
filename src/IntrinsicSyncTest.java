

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
		//Create some threads
		//test method sync.waitForThreads()
		
		fail("Not yet implemented");
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
