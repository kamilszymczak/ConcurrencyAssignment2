import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

public class AtomicSyncTest {

    private int largestMultipleFour(int x){
        while (x % 4 != 0){
            x--;
        }
        return x;
    }

    @Test
    void testPhase1a() {
        final int initThreads = 123;
        final AtomicSync atomic = new AtomicSync(Phase.ONE);
        //Create some threads
        //test method atomic.waitForThreads()
        Thread threads[] = new Thread[initThreads];

        for (int i = 0 ; i < initThreads; i++){
            threads[i] = new Thread(new ThreadTester(atomic));
            threads[i].start();
        }

        // change thread sleep to while loop testing for expected number of terminated threads instead
        // use a getTimeMilis() function to place a max limit on time waited.
         try{ Thread.sleep(initThreads+500);} catch (Exception e){System.out.println("Exception "+e.toString());}

        Stack threadStack = new Stack<Thread>();

        for (Thread startedThreads : threads){
            if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
        }

        assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");

    }

    // paramterized test to test a range of thread numbers
    @ParameterizedTest(name="Run {index}: threadBound")
    @ValueSource(ints = {0, 1, 3, 4, 7, 8, 10, 12, 25, 36, 50, 100, 123, 523})
    public void testPhase1b(int initThreads) throws Throwable {
        final AtomicSync atomic = new AtomicSync(Phase.ONE);
        Thread threads[] = new Thread[initThreads];
        for (int i = 0 ; i < initThreads; i++){
            threads[i] = new Thread(new ThreadTester(atomic));
            threads[i].start();
        }

        try{ Thread.sleep(initThreads*4+500);} catch (Exception e){System.out.println("Exception "+e.toString());}

        Stack threadStack = new Stack<Thread>();

        for (Thread startedThreads : threads){
            if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
        }
        assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");
    }

    @Test
    void testPhase2a() {
        Random rand = new Random();

        int x = rand.nextInt(50);

        // instantiate a random (even) number of threads
        final int initThreads = (x%2 == 0 ? x : ++x);
        System.out.println("Number of threads: "+initThreads);
        //final int initThreads = 50;
        AtomicSync atomic = new AtomicSync(Phase.TWO);
        Thread threads[] = new Thread[initThreads];

        // Even number required for this thread assignment strategy
        for (int i = 0 ; i < initThreads/2; i++){
            threads[i] = new Thread(new ThreadTester(atomic, 0));
            threads[i+(initThreads/2)] = new Thread((new ThreadTester(atomic, 1)));
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

    @ParameterizedTest(name="Run {index}: threadBound")
    @ValueSource(ints = {1, 3, 4, 7, 8, 10, 12, 25, 36, 50, 100, 123, 523})
    void testPhase2b(int threadBound){
        Random rand = new Random();

        int x = rand.nextInt(threadBound);

        // instantiate a random (even) number of threads
        final int initThreads = (x%2 == 0 ? x : ++x);
        System.out.println("Number of threads: "+initThreads);
        //final int initThreads = 50;
        AtomicSync atomic = new AtomicSync(Phase.TWO);
        Thread threads[] = new Thread[initThreads];

        // Even number required for this thread assignment strategy
        for (int i = 0 ; i < initThreads/2; i++){
            threads[i] = new Thread(new ThreadTester(atomic, 0));
            threads[i+(initThreads/2)] = new Thread((new ThreadTester(atomic, 1)));
            threads[i].start();
            threads[i+(initThreads/2)].start();
        }

        try{ Thread.sleep(initThreads*4+500);} catch (Exception e){System.out.println("Exception "+e.toString());}

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

    @Test
    void testPhase3a() {
        AtomicSync atomic = new AtomicSync(Phase.THREE);
        //Create some threads
        //test method atomic.finished
        fail("Not yet implemented");
    }
    //etc

}
