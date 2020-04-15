
public class ThreadTester implements Runnable {
    AtomicSync atomic = null;
    SemaphoreSync semp = null;
    ExtrinsicSync extS = null;
    IntrinsicSync intS = null;
    public int gid = 0;
    Phase phase;
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


    public ThreadTester(AtomicSync a, int id, Phase phase){
        this.atomic = a;
        this.concept = Init.ATOMIC;
        this.gid = id;
        this.phase = phase;
    }

    public ThreadTester(SemaphoreSync s, int id, Phase phase){
        this.semp = s;
        this.concept = Init.SEMAPHORE;
        this.gid = id;
        this.phase = phase;
    }

    public ThreadTester(ExtrinsicSync e, int id, Phase phase){
        this.extS = e;
        this.concept = Init.EXTRINSIC;
        this.gid = id;
        this.phase = phase;
    }

    public ThreadTester(IntrinsicSync i, int id, Phase phase){
        this.intS = i;
        this.concept = Init.INTRINSIC;
        this.gid = id;
        this.phase = phase;
    }

    private void pickMethod(){
        if (phase == Phase.ONE){
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
        //System.out.println("This is a thread from group: "+gid+" executing");

        if(phase == Phase.THREE){
            switch (concept){
                case ATOMIC: atomic.finished(gid);
                    break;
                case SEMAPHORE: semp.finished(gid);
                    break;
                case EXTRINSIC: extS.finished(gid);
                    break;
                case INTRINSIC: intS.finished(gid);
                    break;
            }
        }

        terminated = true;
    }
}
