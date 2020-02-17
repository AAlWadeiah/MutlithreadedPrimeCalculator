import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int lowerLimit, upperLimit, numberOfThreads;
    public static volatile int sharedCtr;

    public static void main(String[] args) {
        int L = 10000;
        numberOfThreads = 32;
        lowerLimit = (int) Math.floor(L / 2);
        upperLimit = L;
        sharedCtr = lowerLimit;

        testOTLock();
        
    }

    public static void testAtomicInteger(){

    }

    public static void testOTLock(){
        // Create the OTLocks based on amount of numbers to test
        int numOfOTLocks = upperLimit - lowerLimit;
        OTLock[] locks = new OTLock[numOfOTLocks];
        for (int i = 0; i < numOfOTLocks; i++) {
            locks[i] = new OTLock(numberOfThreads);
        }

        List<Thread> threads = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; ++i) {
            Thread t = new Thread(new PrintPrimes2(locks, lowerLimit));
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

        long stop = System.currentTimeMillis();
        long runtime = stop - start;
        System.out.println("Total runtime was " + runtime + " milliseconds"); 

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

        long stop = System.currentTimeMillis();
        long runtime = stop - start;
        System.out.println("Total runtime was " + runtime + " milliseconds");

        // Reset shared counter
        sharedCtr = lowerLimit;
    }
}