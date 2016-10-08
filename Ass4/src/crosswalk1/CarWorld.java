/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crosswalk1;

/**
 *
 * @author usuario
 */
//import pruebagrafica.*;
import crosswalk2.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

class CarWorld extends JPanel {

    Image tunnel;
    Image peaton;
    Image blueCar;

    TrafficController controller;

    ArrayList<Car> blueCars = new ArrayList<Car>();
    ArrayList<Pedestrian> peatones = new ArrayList<Pedestrian>();

    public CarWorld() {
	controller = new TrafficController();
        MediaTracker mt = new MediaTracker(this);
	Toolkit toolkit = Toolkit.getDefaultToolkit();


//        En Eclipse utiliza rutas absolutas para los ficheros
        peaton = toolkit.getImage("imageCrosswalk/man1.gif");
        mt.addImage(peaton, 0);
        blueCar = toolkit.getImage("imageCrosswalk/blueJeep.gif");
        mt.addImage(blueCar, 1);
        tunnel = toolkit.getImage("imageCrosswalk/tunnel2.gif");
        mt.addImage(tunnel, 2);

        try {
            mt.waitForID(0);
            mt.waitForID(1);
            mt.waitForID(2);
        } catch (java.lang.InterruptedException e) {
            System.out.println("No puedo cargar una imagen");
        }

	peatones.add(new Pedestrian(null,peaton,null));
	blueCars.add(new Car(null,blueCar,null));
        setPreferredSize(new Dimension(tunnel.getWidth(null),tunnel.getHeight(null)));
    }


    public void paintComponent(Graphics g) {
	g.drawImage(tunnel,0,0,this);
        for (Pedestrian c : peatones) c.draw(g);
        for (Car c : blueCars) c.draw(g);
    }

    public void addCar() {
	SwingUtilities.invokeLater(new Runnable () {
		public void run() {
		    Car c;
                    
			c = new Car(blueCars.get(blueCars.size()-1),blueCar,controller);
			blueCars.add(c);
		    
		        new Thread(c).start();
	        }
	    });
   }
     public void addPeaton() {
	SwingUtilities.invokeLater(new Runnable () {
		public void run() {
		 Pedestrian p;
		     p = new Pedestrian(peatones.get(peatones.size()-1),peaton,controller);
                     peatones.add(p);
		     new Thread(p).start();
	        }
	    });
    }

}
