public class ThreadTester implements Runnable {
    AtomicSync atomic = null;
    SemaphoreSync semp = null;
    ExtrinsicSync extS = null;
    IntrinsicSync intS = null;
    Init constr;

    enum Init {ATOMIC, SEMAPHORE, EXTRINSIC, INTRINSIC}

    public ThreadTester(AtomicSync a){
        this.atomic = a;
        constr = Init.ATOMIC;
    }

    public ThreadTester(SemaphoreSync s){
        this.semp = s;
        constr = Init.SEMAPHORE;
    }

    public ThreadTester(ExtrinsicSync e){
        this.extS = e;
        constr = Init.EXTRINSIC;
    }

    public ThreadTester(IntrinsicSync i){
        this.intS = i;
        constr = Init.INTRINSIC;
    }

    private void pickMethod(){
        switch (constr){
            case ATOMIC: atomic.waitForThreads();
                break;
            case SEMAPHORE: semp.waitForThreads();
                break;
            case EXTRINSIC: extS.waitForThreads();
                break;
            case INTRINSIC: intS.waitForThreads();
                break;
        }
    }

    public void run(){
        this.pickMethod();
        System.out.println("This is one of 4 syncronized threads executing");
    }
}
