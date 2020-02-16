/* 
Author: Abdullah Al-Wadeiah
Class: CPSC 561, Distributed Algorithms

This implementation of OTLock uses a modified Filter lock algorithm to meet criteria of the assignment.
The following was used as a reference:
    The Art of Multiprocessor Programming by Maurice Herlihy and Nir Shavit, page 29
*/

import java.util.List;
import java.util.ArrayList;

public class OTLock {
    private volatile List<Integer> level;
    private volatile List<Integer> victim;
    private int numberOfThreads;
    private boolean lockAcquired;
    
    public OTLock(int n){
        level = new ArrayList<>();
        victim = new ArrayList<>();
        numberOfThreads = n;
        lockAcquired = false;

        for(int i = 0; i < n; i++){
            level.add(0);
        }
    }

    public boolean lock(){
        if (lockAcquired) return false;

        int id = (int) Thread.currentThread().getId();
        
        for(int i = 0; i < numberOfThreads; i++){
            level.set(id, i);
            victim.set(i, id);
            for(int k = 0; k < numberOfThreads; k++)
                while(k != id && level.get(k) >= i && victim.get(i) == id ) {/* do nothing*/}
        }

        if (lockAcquired){
            level.set(id, 0);
            return false;
        } else {
            lockAcquired = true;
            level.set(id, 0);
            return true;
        }
    }
}