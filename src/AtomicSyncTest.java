import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AtomicSyncTest {

    //Note it is expected that you may need multiple tests for each phase
    //Hence the structure below



    // Create 4 threads, each calls atomic and are released when 4 have been called
    @Test
    void testPhase1a() {

        final AtomicSync atomic = new AtomicSync(Phase.ONE);
        //Create some threads
        //test method atomic.waitForThreads()
        Thread threads[] = new Thread[5];
        for (Thread thread : threads){
            thread = new Thread(new ThreadTester(atomic));
            thread.start();
        }

        fail("Not yet implemented");
    }
    @Test
    void testPhase1b() {
        AtomicSync atomic = new AtomicSync(Phase.ONE);
        //Create some threads
        //test method atomic.waitForThreadsInGroup
        fail("Not yet implemented");
    }
    //etc

    @Test
    void testPhase2a() {
        AtomicSync atomic = new AtomicSync(Phase.TWO);
        //Create some threads
        //test method atomic.waitForThreadsInGroup
        fail("Not yet implemented");
    }
    //etc

    @Test
    void testPhase3a() {
        AtomicSync atomic = new AtomicSync(Phase.THREE);
        //Create some threads
        //test method atomic.finished
        fail("Not yet implemented");
    }
    //etc

}
