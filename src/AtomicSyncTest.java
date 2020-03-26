import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.ZonedDateTime;
import java.util.*;

public class AtomicSyncTest {

    private int largestMultipleFour(int x){
        while (x % 4 != 0){
            x--;
        }
        return x;
    }

    private Thread[] initP1Test(int initThreads){
        final AtomicSync atomic = new AtomicSync(Phase.ONE);
        Thread threads[] = new Thread[initThreads];
        for (int i = 0 ; i < initThreads; i++){
            threads[i] = new Thread(new ThreadTester(atomic));
            threads[i].start();
        }
        return threads;
    }

    private int terminateThreads (Thread threads[], int expectedThreads){
        // counter for the number of terminated threads
        int termThreads = 0;
        int threadsSize = threads.length;

        //Iterator<Thread> it = new ArrayList(Arrays.asList(threads)).iterator();
        //List<Thread> finished = new ArrayList<Thread>();

        // timer variables, start time, updating time and max time to wait.
        final long waitLimit = (threadsSize*20)+500;
        final long beginWait = ZonedDateTime.now().toInstant().toEpochMilli();

        long timeNow = ZonedDateTime.now().toInstant().toEpochMilli();

            //termThreads = finished.size();
        while (termThreads != expectedThreads && (timeNow - beginWait) != waitLimit){
            try{ Thread.sleep(10);} catch (Exception e){System.out.println("Exception "+e.toString());}
            termThreads = 0;

            /* Too much overhead
            while (it.hasNext()){
                Thread temp = it.next();
                if (temp.getState() == Thread.State.TERMINATED){
                    finished.add(temp);
                    it.remove();
                }
            }
            */

            // looping over all the threads every time is much faster
            for (Thread t : threads){
                if (t.getState() == Thread.State.TERMINATED) termThreads++;
            }
            timeNow = ZonedDateTime.now().toInstant().toEpochMilli();
        }
        return termThreads;
    }

    @Test
    void testPhase1a() {
        final int initThreads = 100000;
        final int expectedThreads = largestMultipleFour(initThreads);

        Thread threads[] = initP1Test(initThreads);

        // wait until all threads terminate
        int termThreads = terminateThreads(threads, expectedThreads);

        Stack threadStack = new Stack<Thread>();

        for (Thread startedThreads : threads){
            if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
        }

        assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");
        assertEquals(expectedThreads, termThreads, "Unexpected number of threads have terminated");
    }

    // paramterized test to test a range of thread numbers
    @ParameterizedTest(name="Run {index}")
    @ValueSource(ints = {0, 1, 3, 4, 7, 8, 10, 12, 25, 36, 50, 100, 123, 523})
    public void testPhase1b(int initThreads) throws Throwable {
        final int expectedThreads = largestMultipleFour(initThreads);
        Thread threads[] = initP1Test(initThreads);

        int termThreads = terminateThreads(threads, expectedThreads);

        Stack threadStack = new Stack<Thread>();

        for (Thread startedThreads : threads){
            if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
        }
        assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");
        assertEquals(expectedThreads, termThreads, "Unexpected number of threads have terminated");
    }

    @Test
    void testPhase2a() {
        // testing 2 groups

        //Random rand = new Random();
        //int x = rand.nextInt(50);

        // instantiate a random (even) number of threads
        //final int initThreads = (x%2 == 0 ? x : ++x);
        int x = 0;
        final int initThreads = (x%2 == 0 ? x : ++x);
        final int expectedThreadsPerGroup = largestMultipleFour(initThreads/2);
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
        Thread threadG1[] = new Thread[initThreads/2];
        Thread threadG2[] = new Thread[initThreads/2];
        System.arraycopy(threads, 0, threadG1, 0, threadG1.length);
        System.arraycopy(threads, initThreads/2, threadG2, 0, threadG2.length);

        int termThreadsG1 = terminateThreads(threadG1, expectedThreadsPerGroup);
        int termThreadsG2 = terminateThreads(threadG2, expectedThreadsPerGroup);

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

        assertEquals(expectedThreadsPerGroup, groupZeroStack.size(), "Wrong number of threads in group 0");
        assertEquals(expectedThreadsPerGroup, groupOneStack.size(), "Wrong number of threads in group 1");
        assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");
    }
    //etc

    @ParameterizedTest(name="Run {index}")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 8, 10, 11, 12, 25, 26, 36, 50, 100, 122, 522})
    void testPhase2b(int threadBound){
        Random rand = new Random();

        int x = rand.nextInt(threadBound);

        // instantiate a random (even) number of threads
        final int initThreads = (x%2 == 0 ? x : ++x);
        final int expectedThreadsPerGroup = largestMultipleFour(initThreads/2);
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

        Thread threadG1[] = new Thread[initThreads/2];
        Thread threadG2[] = new Thread[initThreads/2];
        System.arraycopy(threads, 0, threadG1, 0, threadG1.length);
        System.arraycopy(threads, initThreads/2, threadG2, 0, threadG2.length);

        int termThreadsG1 = terminateThreads(threadG1, expectedThreadsPerGroup);
        int termThreadsG2 = terminateThreads(threadG2, expectedThreadsPerGroup);

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

        assertEquals(expectedThreadsPerGroup, groupZeroStack.size(), "Wrong number of threads in group 0");
        assertEquals(expectedThreadsPerGroup, groupOneStack.size(), "Wrong number of threads in group 1");
        assertEquals (0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");
    }

    @ParameterizedTest(name="Run {index}")
    @ValueSource(ints = {4, 7, 10, 12, 19, 20, 21, 27, 100/* HAMMER YOUR CPU, 500 */})
    void testPhase2c(int threadBound){
        Random rand = new Random();
        int x = rand.nextInt(threadBound);
        final int numOfGroups = x > 0 ? x : 1;
        final int initThreads = rand.nextInt(threadBound*15);
        System.out.println("Number of threads: "+initThreads);

        AtomicSync atomic = new AtomicSync(Phase.TWO);
        Thread threads[] = new Thread[initThreads];
        int groups[] = new int[numOfGroups + 1];

        for (int i = 0; i < initThreads; i++){
            int gid = rand.nextInt(numOfGroups);

            threads[i] = new Thread(new ThreadTester(atomic, gid));
            System.out.println("Thread "+threads[i]+" assigned to group "+gid);
            threads[i].start();
            groups[gid]++;
        }

        int expectedThreads = 0;

        for (int i = 0; i < numOfGroups; i++){
            System.out.println("Threads terminating from group " + i +": "+ largestMultipleFour(groups[i]) );
            expectedThreads += largestMultipleFour(groups[i]);
        }

        int termThreads = terminateThreads(threads, expectedThreads);

        Stack threadStack = new Stack<Thread>();

        for (Thread startedThreads : threads){
            if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
        }

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
