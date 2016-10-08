/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crosswalk3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class TrafficController {

    volatile int nCars = 0, nPedestrians = 0;
    final ReentrantLock l = new ReentrantLock(true);
    Condition pedQueue = l.newCondition();
    Condition carQueue = l.newCondition();
    int MAXcar = 0; //the maximum will be 5
    int MAXped = 0; // the maximum will be 10
    boolean carTurn = false; //giving priority to pedestrians

    private void checkTurn() {
        if (MAXcar == 5) {
            carTurn = !carTurn;
            MAXcar = 0;
        }
        if (MAXped == 10) {
            carTurn = !carTurn;
            MAXped = 0;
        }
    }

    public void carEnters() throws InterruptedException {

        try {
            l.lock();
            if (nPedestrians > 0 || (!carTurn && l.hasWaiters(pedQueue))) {
                carQueue.await();
            }

            nCars++;
            MAXcar++;
            checkTurn();
        } catch (InterruptedException ex) {
        } finally {
            l.unlock();
        }
    }

    public void carExits() throws InterruptedException {

        try {
            l.lock();
            nCars--;
            if (nCars == 0) {
                pedQueue.signal();
                MAXcar = 0;
            }

        } finally {
            l.unlock();
        }
    }

    public void pedestrianEnters() throws InterruptedException {

        try {
            l.lock();
            if (nCars > 0 || (carTurn && l.hasWaiters(carQueue))) {
                pedQueue.await();
            }
            nPedestrians++;
            MAXped++;
            checkTurn();
        } catch (InterruptedException ex) {
        } finally {
            l.unlock();
        }

    }

    public void pedestrianExits() {

        try {
            l.lock();
            nPedestrians--;
            if (nPedestrians == 0) {
                carQueue.signal();
                MAXped = 0;
            }
        } finally {
            l.unlock();
        }

    }

}
