/* 
Author: Abdullah Al-Wadeiah
Class: CPSC 561, Distributed Algorithms

This implementation of OTLock uses a modified Filter lock algorithm to meet criteria of the assignment.
The following was used as a reference:
    The Art of Multiprocessor Programming by Maurice Herlihy and Nir Shavit, page 29
*/

public class OTLock {
    private volatile int[] level;
    private volatile int[] victim;
    private int numberOfThreads;
    private boolean lockAcquired;
    
    public OTLock(int n){
        level = new int[n];
        victim = new int[n];
        numberOfThreads = n;
        lockAcquired = false;

        for(int i = 0; i < n; i++){
            level[i] = -1;
            victim[i] = -1;
        }
    }

    public boolean lock(){
        if (lockAcquired) return false;

        int id = (int) Thread.currentThread().getId() % numberOfThreads;
        
        for(int i = 1; i < numberOfThreads; i++){
            level[id] = i;
            victim[i] = id;
            for(int k = 0; k < numberOfThreads; k++)
                while(k != id && level[k] >= i && victim[i] == id ) {/* do nothing*/}
        }

        if (lockAcquired){
            level[id] = 0;
            return false;
        } else {
            lockAcquired = true;
            level[id] = 0;
            return true;
        }
    }
}