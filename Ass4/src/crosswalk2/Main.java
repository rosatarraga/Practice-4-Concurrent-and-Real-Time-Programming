/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crosswalk2;

/**
 *
 * @author usuario
 */
public class Main {

    private static void nap(int ms) {
	try {
	    Thread.sleep(ms);
	} catch (InterruptedException e) {}
    }


    public static void main(String[] a) {
	final CarWindow win = new CarWindow();

	win.pack();
	win.setVisible(true);

	new Thread(new Runnable() {
		public void run() {
		    while (true) {
			nap(25);
			win.repaint();
		    }
		}
	    }).start();



    }

}
