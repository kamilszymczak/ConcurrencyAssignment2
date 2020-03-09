import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

public class AtomicSyncTest {

    //Note it is expected that you may need multiple tests for each phase
    //Hence the structure below



    // Create 4 threads, each calls atomic and are released when 4 have been called
    @Test
    void testPhase1a() {
        final int initThreads = 15;
        final AtomicSync atomic = new AtomicSync(Phase.ONE);
        //Create some threads
        //test method atomic.waitForThreads()
        Thread threads[] = new Thread[initThreads];

        for (int i = 0 ; i < initThreads; i++){
            threads[i] = new Thread(new ThreadTester(atomic));
            threads[i].start();
        }

        
         try{ Thread.sleep(initThreads+20);} catch (Exception e){System.out.println("Exception "+e.toString());}

        Stack threadStack = new Stack<Thread>();

        for (Thread startedThreads : threads){
            if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
        }

        assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");

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
        // pass thread group to waitForThreadsInGroup with thread

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
