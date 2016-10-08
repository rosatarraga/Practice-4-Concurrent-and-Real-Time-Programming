/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunnel2;

//import tunnel1.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class TrafficController {

    int nBluecars = 0;
    int nRedcars = 0;
    final int MAX = 3;
    final ReentrantLock l = new ReentrantLock(true);
    final Condition redQueue = l.newCondition();
    final Condition blueQueue = l.newCondition();
    boolean blueTurn;
    int counter = 0;
   
    private void CheckTurn() {
        if (counter == MAX) {
            counter = 0;
            blueTurn = !blueTurn;

        }

    }

    public void redEnters() throws InterruptedException {
        l.lock();
        try {
            if (nBluecars != 0 || (blueTurn && l.hasWaiters(blueQueue))) {//blueQueue knows if any blue car is waiting
                redQueue.await();
            }

            nRedcars++;
            counter++;
            CheckTurn();
        } finally {
            l.unlock();
        }
    }

    public void blueEnters() throws InterruptedException {
        l.lock();
        try {
            if (nRedcars != 0 || (!blueTurn && l.hasWaiters(redQueue))) {
                blueQueue.await();
            }
            nBluecars++;
            counter++;
            CheckTurn();
        } finally {
            l.unlock();
        }
    }

    public void blueExits() throws InterruptedException {
        l.lock();
        try {
            nBluecars--;

            if(nBluecars==0){
                redQueue.signal();
            }
        } finally {
            l.unlock();
        }
    }

    public void redExits() throws InterruptedException {
        l.lock();
        try {
            nRedcars--;

            if(nRedcars==0){
                blueQueue.signal();
            }
        } finally {
            l.unlock();
        }
    }

}
