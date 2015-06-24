package a4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

import a4.GameWorld;
import a4.objects.GameObject;
import a4.objects.IDrawable;
import a4.objects.MoveableItem;
import a4.objects.LandscapeItems.Rock;
import a4.objects.LandscapeItems.Tree;
import a4.objects.MoveableItems.Projectiles.Missile;
import a4.objects.MoveableItems.Projectiles.SpikedGrenade;
import a4.objects.MoveableItems.Vehicles.Tank;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;


/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: MapView
Class Description: Observes the GameWorld and updates the map information 
					as necessary. 
**********************************************************************/

public class MapView extends JPanel implements IObserver, MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {
	
	private GameWorld gwo;
	
	//size of the map view
	private final int FRAME_WIDTH=800, FRAME_HEIGHT=800;
	private Point prevPoint = null;
	private Point midPoint = null;
	private int adderX, adderY;
	
	private double windowTop, windowBottom, windowRight, windowLeft;
	private double panelWidth, panelHeight;
	
	private AffineTransform worldtoND, ndToScreen, theVTM, inverseVTM;
	
	private AffineTransform panAT;

	
	public MapView() {
		
	}
	
	public MapView(GameWorld gw) {
		gwo = gw;
		gwo.addObserver(this);
		
		//add the map panel to the window
		this.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		this.setBackground(Color.WHITE);
		this.setBorder(new LineBorder(Color.blue, 2));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.addMouseWheelListener(this);
		//this.setSize(200, 200);
		
		worldtoND = new AffineTransform();
		ndToScreen = new AffineTransform();
		theVTM = new AffineTransform();
		
		panAT = new AffineTransform();
		
		//Bounds
		panelWidth = this.getWidth();
		panelHeight = this.getHeight();
		
		windowLeft = 0;
		windowRight = 1024;
		windowTop = 1024;
		windowBottom = 0;
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		//save transform
		Graphics2D g2d = (Graphics2D) g;
		
		AffineTransform saveAT = g2d.getTransform();
		
		//world to ND
		g2d.translate(windowLeft, windowTop);
		g2d.scale(1, -1);
		worldtoND = g2d.getTransform();
		worldtoND.concatenate(panAT);
		
		//nd to screen
		g2d.scale(panelWidth/windowRight, panelHeight/windowTop);
		ndToScreen = g2d.getTransform();

		//VTM
		theVTM = (AffineTransform) ndToScreen.clone();
		theVTM.concatenate(worldtoND);
		
		g2d.transform(theVTM);
		
		Iterator<GameObject> i = gwo.getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			//while there are more items in the collection...
			GameObject h = i.next();
			if(h instanceof IDrawable) {
				g.setColor(h.getColor());
				((IDrawable) h).draw(g2d);
			}
		}
		
		g2d.setTransform(saveAT);
	}
	
	
	private void buildWorldToND(Graphics2D g, int wL, int wR, int wT, int wB) {
		worldtoND.translate(wL, this.getHeight());
		worldtoND.scale(1, -1);
	}
	
	
	public void zoomIn() {
		double h = windowTop - windowBottom;
		double w = windowRight - windowLeft;
		windowLeft += w * 0.05;
		windowRight -= w * 0.05;
		windowTop -= h * 0.05;
		windowBottom += h * 0.05;
		this.repaint();
	}
	
	public void zoomOut() {
		double h = windowTop - windowBottom;
		double w = windowRight - windowLeft;
		windowLeft -= w * 0.05;
		windowRight += w * 0.05;
		windowTop += h * 0.05;
		windowBottom -= h * 0.05;
		this.repaint();
	}
	

	public void update(IObservable proxy, Object arg) {
		
		//a3 does not require text output. Removing this
		//((GameWorldProxy) proxy).map();
		this.repaint();
	}


	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
	
	}

	
	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent e) {
		//only if the game is paused...
		prevPoint = e.getPoint();
		
		if (gwo.getGameState() == false) {
			
			Point2D mouseScreenLoc = e.getPoint();
			
			try {
				inverseVTM = theVTM.createInverse();
			} catch (NoninvertibleTransformException e1) { }
			//mouseWorldPoint = inverseVTM.transform(prevPoint2D, null); 
			Point2D mouseWorldLoc = inverseVTM.transform(mouseScreenLoc, null);

			
			Iterator<GameObject> j = gwo.getTheGameObjectsCollection().getIterator();
			while(j.hasNext()) {
				//while there are more items in the collection...
				GameObject h = j.next();
				if (h instanceof Tank) {
					//if (((Tank) h).contains(prevPoint)) {
					if (((Tank) h).contains(mouseWorldLoc)) {
						((Tank) h).setSelected(true);
					}
					else if (e.isControlDown()) {
						System.out.println("CTRL KEY PRESSED");
					}
					else {
						//System.out.println("You are not pressing control, or have clicked outside a tank");
						((Tank) h).setSelected(false);
					}
				}
			}
		}
		this.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (gwo.getGameState() == true) {
			midPoint = e.getPoint();
			
			adderX = ((int) prevPoint.getX() - (int) midPoint.getX());
			adderY = ((int) prevPoint.getY() - (int) midPoint.getY());

			panAT.translate(-adderX * .05, adderY * .05);
			
			this.repaint();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//System.out.println("Mouse wheel moved...");
		if (gwo.getGameState() == true) {
			int direction = e.getWheelRotation();
		       if (direction < 0) {
		    	   //System.out.println("Wheel moved up");
		    	   zoomIn();
		       } else {
		    	   //System.out.println("Wheel moved down");
		    	   zoomOut();
		       }
		}
	}


}
