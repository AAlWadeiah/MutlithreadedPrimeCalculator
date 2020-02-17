import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int lowerLimit, upperLimit;
    public static volatile int sharedCtr;

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        int L = 1000000;
        int numberOfThreads = 32;
        lowerLimit = (int) Math.floor(L / 2);
        upperLimit = L;
        sharedCtr = lowerLimit;

        LLLock lock = new LLLock(numberOfThreads);
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; ++i) {
            Thread t = new Thread(new PrintPrimes1(lock, upperLimit));
            t.start();
            threads.add(t);
        }

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
    }
}