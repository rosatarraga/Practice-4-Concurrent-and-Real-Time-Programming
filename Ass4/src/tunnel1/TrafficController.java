/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunnel1;

/**
 *
 * @author usuario
 */
public class TrafficController {

     int nBluecars=0;
     int nRedcars=0;
    
    synchronized public void redEnters() throws InterruptedException {
        if(nBluecars!=0) wait();
        nRedcars++;
 
    }

    synchronized public  void blueEnters() throws InterruptedException {
        if(nRedcars!=0) wait();
	nBluecars++;
    }

     synchronized public  void blueExits() {
    	nBluecars--;
        if(nBluecars==0) notify();
    	 
    }

    synchronized public  void redExits() {
        nRedcars--;
	if(nRedcars==0) notify();

    }
}