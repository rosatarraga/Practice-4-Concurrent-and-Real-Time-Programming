/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crosswalk1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class TrafficController {

    int nPed = 0;
    int nCars = 0;

    synchronized public void carEnters() throws InterruptedException {
        if (nPed > 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }

        nCars++;
    }

    synchronized public void carExits() throws InterruptedException {
        nCars--;
        notify();
    }

    synchronized public void pedestrianEnters() throws InterruptedException {

        if (nCars > 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }

        nPed++;
    }

    synchronized public void pedestrianExits() {

        nPed--;
        notify();

    }

}
