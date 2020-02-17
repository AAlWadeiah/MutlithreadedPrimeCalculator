import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int lowerLimit, upperLimit, numberOfThreads;
    public static volatile int sharedCtr;

    public static void main(String[] args) {
        int L = 1000000;
        numberOfThreads = 32;
        lowerLimit = (int) Math.floor(L / 2);
        upperLimit = L;
        sharedCtr = lowerLimit;

        
    }

    public static void testAtomicInteger(){

    }

    public static void testOTLock(){

    }
    
    public static void testLLLock(){
        List<Thread> threads = new ArrayList<>();
        LLLock lock = new LLLock(numberOfThreads);

        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; ++i) {
            Thread t = new Thread(new PrintPrimes1(lock, upperLimit));
            t.start();
            threads.add(t);
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long finish = System.currentTimeMillis();
        long runtime = finish - start;
        System.out.println("Total runtime was " + runtime + " milliseconds");

        // Reset shared counter
        sharedCtr = lowerLimit;
    }
}