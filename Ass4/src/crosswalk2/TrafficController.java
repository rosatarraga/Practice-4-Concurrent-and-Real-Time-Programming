/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crosswalk2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */

public class TrafficController {

    int nCars=0;
    int nPedestrians=0;
    final ReentrantLock l = new ReentrantLock(true);
    final Condition pedQueue=l.newCondition();
    final Condition carQueue=l.newCondition();

    public void carEnters() throws InterruptedException {
	l.lock();
        try{
            if(l.hasWaiters(pedQueue)|| nPedestrians>0) carQueue.await();
            nCars++;
            
        }finally{
            l.unlock();
        }
    }
    public void carExits() throws InterruptedException{
	l.lock();
        try{
            nCars--;
            pedQueue.signalAll();
            
        }finally{
            l.unlock();
        }
    }

    public void pedestrianEnters() throws InterruptedException {
	
        l.lock();
        try{
           if(nCars>0) pedQueue.await();
            nPedestrians++;
        }finally{
            l.unlock();
        }
        
    }

     public void pedestrianExits() {
	 
        l.lock();
        try{
            nPedestrians--;
            if(!l.hasWaiters(pedQueue)) carQueue.signal();
        }finally{
            l.unlock();
        }
	 
    }

   

}