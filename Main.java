import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static int lowerLimit, upperLimit, numberOfThreads;
    public static volatile int sharedCtr;
    private static long[] runtimes = new long[3]; 

    public static void main(String[] args) {
        int L = 1000000;
        numberOfThreads = 1;
        lowerLimit = (int) Math.floor(L / 2);
        upperLimit = L;
        
        System.out.println("============== Running LLLock Implementation *VERY SLOW FOR HIGH THREAD COUNTS* ==============");
        testLLLock();
        System.out.println("============== Running OTLock Implementation ==============");
        testOTLock();
        System.out.println("============== Running Atomic Counter Implementation ==============");
        testAtomicCounter();

        System.out.println(String.format("Runtimes: \n LLLock: %d \n OTLock: %d \n Atomic Counter: %d", runtimes[0], runtimes[1], runtimes[2]));
    }

    public static void testAtomicCounter(){
        AtomicInteger atomicCtr = new AtomicInteger(lowerLimit);

        List<Thread> threads = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new Thread(new PrintPrimes3(atomicCtr, upperLimit));
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
        runtimes[2] = runtime;

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
        for (int i = 0; i < numberOfThreads; i++) {
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
        runtimes[1] = runtime; 

    }
    
    public static void testLLLock(){
        sharedCtr = lowerLimit;
        LLLock lock = new LLLock(numberOfThreads);
        
        List<Thread> threads = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++) {
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
        runtimes[0] = runtime;
    }
}