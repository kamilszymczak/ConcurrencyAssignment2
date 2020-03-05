public class ThreadTester implements Runnable {
    AtomicSync atomic;
    SemaphoreSync semp;
    ExtrinsicSync extS;
    IntrinsicSync intS;

    public ThreadTester(AtomicSync a){
        this.atomic = a;
    }

    public ThreadTester(SemaphoreSync s){this.semp = s;}

    public ThreadTester(ExtrinsicSync e){this.extS = e;}

    public ThreadTester(IntrinsicSync i){this.intS = i;}

    
    public void run(){

        extS.waitForThreads();
        System.out.println("This is one of 4 syncronized threads executing");
    }

    private void threadTask() {
        atomic.waitForThreads();
        this.run();
    }
}
