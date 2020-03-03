import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AtomicSyncTest {

    //Note it is expected that you may need multiple tests for each phase
    //Hence the structure below

    @Test
    void testPhase1a() {
        AtomicSync sync = new AtomicSync(Phase.ONE);
        //Create some threads
        //test method sync.waitForThreads()

        fail("Not yet implemented");
    }
    @Test
    void testPhase1b() {
        AtomicSync sync = new AtomicSync(Phase.ONE);
        //Create some threads
        //test method sync.waitForThreadsInGroup
        fail("Not yet implemented");
    }
    //etc

    @Test
    void testPhase2a() {
        AtomicSync sync = new AtomicSync(Phase.TWO);
        //Create some threads
        //test method sync.waitForThreadsInGroup
        fail("Not yet implemented");
    }
    //etc

    @Test
    void testPhase3a() {
        AtomicSync sync = new AtomicSync(Phase.THREE);
        //Create some threads
        //test method sync.finished
        fail("Not yet implemented");
    }
    //etc

}
