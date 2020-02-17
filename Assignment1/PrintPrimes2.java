public class PrintPrimes2 implements Runnable{

    private OTLock[] locks;
    private int lowerLimit;

    public PrintPrimes2(OTLock[] locks, int lower){
        this.locks = locks;
        this.lowerLimit = lower;
    }

    @Override
    public void run() {
        int num;
        for (int i = 0; i < locks.length; i++) {
            if(locks[i].lock()){
                num = lowerLimit + i;
                if(isPrime(num)) System.out.println(num);
            }
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