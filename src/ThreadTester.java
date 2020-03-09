
public class ThreadTester implements Runnable {
    AtomicSync atomic = null;
    SemaphoreSync semp = null;
    ExtrinsicSync extS = null;
    IntrinsicSync intS = null;
    int gid = 0;
    boolean groupSet = false;
    Init concept;
    public Boolean terminated = false;

    enum Init {ATOMIC, SEMAPHORE, EXTRINSIC, INTRINSIC}

    public ThreadTester(AtomicSync a){
        this.atomic = a;
        this.concept = Init.ATOMIC;
    }

    public ThreadTester(SemaphoreSync s){
        this.semp = s;
        this.concept = Init.SEMAPHORE;
    }

    public ThreadTester(ExtrinsicSync e){
        this.extS = e;
        this.concept = Init.EXTRINSIC;
    }

    public ThreadTester(IntrinsicSync i){
        this.intS = i;
        this.concept = Init.INTRINSIC;
    }

    public ThreadTester(AtomicSync a, int id){
        this.atomic = a;
        this.concept = Init.ATOMIC;
        this.gid = id;
        this.groupSet = true;
    }

    public ThreadTester(SemaphoreSync s, int id){
        this.semp = s;
        this.concept = Init.SEMAPHORE;
        this.gid = id;
        this.groupSet = true;
    }

    public ThreadTester(ExtrinsicSync e, int id){
        this.extS = e;
        this.concept = Init.EXTRINSIC;
        this.gid = id;
        this.groupSet = true;
    }

    public ThreadTester(IntrinsicSync i, int id){
        this.intS = i;
        this.concept = Init.INTRINSIC;
        this.gid = id;
        this.groupSet = true;
    }

    private void pickMethod(){
        if (groupSet == false ){
            switch (concept){
                case ATOMIC: atomic.waitForThreads();
                    break;
                case SEMAPHORE: semp.waitForThreads();
                    break;
                case EXTRINSIC: extS.waitForThreads();
                    break;
                case INTRINSIC: intS.waitForThreads();
                    break;
            }
        } else {
            switch (concept){
                case ATOMIC: atomic.waitForThreadsInGroup(gid);
                    break;
                case SEMAPHORE: semp.waitForThreadsInGroup(gid);
                    break;
                case EXTRINSIC: extS.waitForThreadsInGroup(gid);
                    break;
                case INTRINSIC: intS.waitForThreadsInGroup(gid);
                    break;
            }
        }
    }

    public void run(){
        this.pickMethod();
        System.out.println("This is one of 4 syncronized threads executing");
        terminated = true;
    }
}
