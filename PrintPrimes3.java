import java.util.concurrent.atomic.AtomicInteger;

public class PrintPrimes3 implements Runnable{

    private AtomicInteger sharedCtr;
    private int upperLimit;

    public PrintPrimes3(AtomicInteger atomicInt, int upper){
        this.sharedCtr = atomicInt;
        this.upperLimit = upper;
    }
    
    @Override
    public void run() {
        int n = 0;
        while (n <= upperLimit) {
            n = sharedCtr.getAndIncrement();
            if(isPrime(n)) System.out.println(n);
        }

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