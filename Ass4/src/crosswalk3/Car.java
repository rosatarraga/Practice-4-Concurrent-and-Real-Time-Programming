/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crosswalk3;

/**
 *
 * @author usuario
 */
//import pruebagrafica.*;
import crosswalk2.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Car implements Runnable{

   
    public static final int VELOCIDAD = 20; //INVERSA 

    private final static int tunnelX   = 530;//

    private final static int tunnelYLeft = 120;// punto parada peatones
    
    private final static int tunnelYMid = 410;//

    
    private final static int tunnelYRight = 320;// punto salida peatones

    private final static int tunnelXLeft = 290; //210; punto salida coche
    
    private final static int tunnelXMid = 410;
    
    
    private final static int tunnelXRight = 530;//610; punto parada cohe
    private final static int totalWidth = 900;
    private final static int totalHigh = 440;

    private final static int initX  = totalWidth; // posiciÃ³n salida coche

    private final static int initY  = 220;// altura en la que comienza el coche el grafico
    private final static int outLeft = -200;
    private final static int outRight = totalWidth + 200;
    private final static int outDown = totalHigh+200;


    int xpos, ypos; 
    Car inFront;
    Image image;
    TrafficController controller;

    public Car(Car inFront, Image image, TrafficController controller) {
	
	this.inFront = inFront;
        this.image = image;
	this.controller  = controller;
        
         xpos = inFront == null ? outLeft : Math.max(initX,inFront.getX()+90);
         ypos = initY;
       
    }
    

    public void move() {
	int xposOld =  xpos;
        int yposOld =  ypos;
//	
            if (xpos-inFront.getX() > 100) {
                xpos -= 4;
                if (xpos <= tunnelXRight && xposOld > tunnelXRight) try {
                    controller.carEnters();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                }
           
		else if (xpos <= tunnelXLeft && xposOld > tunnelXLeft) try {
                    controller.carExits();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                }
            
	}
    }



    public void run() {
	boolean outOfSight =  xpos < -80;
        while (!outOfSight) {
            move();
	    outOfSight =  xpos < -80;
	    try {
		Thread.sleep(VELOCIDAD);
	    } catch (InterruptedException e) {}
	    }
	xpos =  outLeft;
       
    }

    public int getX() {return xpos;}
    public int getY() {return ypos;}
    
    public void draw(Graphics g) {
	g.drawImage(image,xpos,ypos,null);
    }
}
