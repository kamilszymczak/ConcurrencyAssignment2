import org.jetbrains.annotations.NotNull;

public class ThreadTester implements Runnable {
    AtomicSync atomic;

    public ThreadTester(AtomicSync a){
        this.atomic = a;
    }

    public void run(){
        System.out.println("This is one of 4 threads executing");
    }

    private void threadTask() {
        atomic.waitForThreads();
        this.run();
    }
}
