/* 
Author: Abdullah Al-Wadeiah
Class: CPSC 561, Distributed Algorithms

This implementation of LLLock uses Lamport's Bakery algorithm.
The following was used as a reference:
    The Art of Multiprocessor Programming by Maurice Herlihy and Nir Shavit, page 32
*/
import java.util.Arrays;

public class LLLock {
    private volatile boolean[] flag;
    private volatile int[] ticket;
    private static int numberOfThreads;

    public LLLock(int n){
        flag = new boolean[n];
        ticket = new int[n];
        numberOfThreads = n;

        for(int i = 0; i < n; i++){
            flag[i] = false;
            ticket[i] = 0;
        }
    }

    public void lock(){
        // Execute doorway() to get ticket number
        int id = (int) getThreadID();
        doorway(id);
        for(int k = 0; k < numberOfThreads; k++){
           while(k != id && flag[k] && (ticket[k] < ticket[id] || ( ticket[k] == ticket[id] && k < id))) { /* do nothing */ }
        }
    }

    public void unlock(){
        flag[(int) getThreadID()] = false;
    }

    private void doorway(int id){
        flag[id] = true;
        int max = Arrays.stream(ticket).max().getAsInt();
        ticket[id] = max + 1;
    }

    private long getThreadID(){ return Thread.currentThread().getId() % numberOfThreads; }
}