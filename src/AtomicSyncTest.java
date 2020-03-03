import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AtomicSyncTest {

    //Note it is expected that you may need multiple tests for each phase
    //Hence the structure below

    @Test
    void testPhase1a() {
        AtomicSync atomic = new AtomicSync(Phase.ONE);
        //Create some threads
        //test method atomic.waitForThreads()

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
