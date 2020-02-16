/* 
Author: Abdullah Al-Wadeiah
Class: CPSC 561, Distributed Algorithms

This implementation of LLLock uses Lamport's Bakery algorithm.
The following was used as a reference:
    The Art of Multiprocessor Programming by Maurice Herlihy and Nir Shavit, page 32
*/

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class LLLock {
    private volatile List<Boolean> flag;
    private volatile List<Integer> ticket;
    private static int numberOfThreads;

    public LLLock(int n){
        flag = new ArrayList<>();
        ticket = new ArrayList<>();
        numberOfThreads = n;

        for(int i = 0; i < n; i++){
            flag.add(false);
            ticket.add(0);
        }
    }

    public void lock(){
        // Execute doorway() to get ticket number
        int id = (int) Thread.currentThread().getId() % numberOfThreads;
        doorway(id);
        for(int k = 1; k <= flag.size(); k++){
           while(flag.get(k) && (ticket.get(k) < ticket.get(id) || ( ticket.get(k) == ticket.get(id) && k < id))) { /* do nothing */ }
        }
    }

    public void unlock(int id){
        flag.set(id, false);
    }

    private void doorway(int id){
        flag.set(id, true);
        Integer max = Collections.max(ticket);
        ticket.set(id, max + 1);
    }
}