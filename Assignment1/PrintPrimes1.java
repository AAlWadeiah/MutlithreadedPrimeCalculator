public class PrintPrimes1 implements Runnable{

    private LLLock lock;
    private int primeCtr, lowerLimit, upperLimit;;

    public PrintPrimes1(LLLock lock, int upper, int lower){
        this.lock = lock;
        this.primeCtr = lower;
        this.upperLimit = upper;
    }
    
    @Override
    public void run() {
        int n;
        do {
            lock.lock();
            n = primeCtr;
            primeCtr++;
            lock.unlock();
        }
        while (primeCtr <= upperLimit);

    }

    static boolean isPrime(long n){
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(n%2 == 0 || n%3 == 0) return false;
        long sqrtN = (long)Math.sqrt(n)+1;
        for(long i = 6; i <= sqrtN; i += 6) {
            if(n%(i-1) == 0 || n%(i+1) == 0) return false;
        }
        return true;	
    }
}