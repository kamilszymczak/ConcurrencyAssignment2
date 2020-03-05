import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.Random;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemaphoreSyncTest {

    private int largestMultipleFour(int x){
        while (x % 4 != 0){
            x--;
        }
        return x;
    }

    @Test
    void testPhase1a() {
        Random rand = new Random();
        final int initThreads = rand.nextInt(50);
        final int expectedExecution = largestMultipleFour(initThreads);
        int isTerm = 0;
        System.out.println("Running "+initThreads+" Threads");
        final SemaphoreSync semp = new SemaphoreSync(Phase.ONE);
        //Create some threads
        //test method semp.waitForThreads()
        Thread threads[] = new Thread[initThreads];
        ThreadTester test[] = new ThreadTester[initThreads];

        for (int i = 0 ; i < initThreads; i++){
            test[i] = new ThreadTester(semp);
            threads[i] = new Thread(test[i]);
            threads[i].start();
        }

        for (int i = 0 ; i < initThreads; i++){
            try{threads[i].join();} catch (Exception e){
                System.out.println("Thread Join exception "+ e);
            }
            if (test[i].terminated == true) isTerm ++;
        }


        try{ Thread.sleep(initThreads*2+50);} catch (Exception e){System.out.println("Exception "+e.toString());}




        Stack threadStack = new Stack<Thread>();

        for (Thread startedThreads : threads){
            if (startedThreads.getState() == Thread.State.TERMINATED) threadStack.push(startedThreads);
        }

        assertEquals (expectedExecution, isTerm, "Incorrect instantiated terminations");
        assertEquals(0, threadStack.size() % 4, "Number of terminated threads should be a multiple of 4");

    }
}
