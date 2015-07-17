package a4.objects.MoveableItems.Projectiles;

import a4.IObserver;
import a4.Sound;
import a4.objects.GameObject;
import a4.objects.ICollider;
import a4.objects.IDrawable;
import a4.objects.IMovable;
import a4.objects.MoveableItem;
import a4.objects.LandscapeItems.Rock;
import a4.objects.LandscapeItems.Tree;
import a4.objects.MoveableItems.Projectile;
import a4.objects.MoveableItems.Vehicles.Tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: PlasmaWave
Class Description: Creates a plasma wave projectile. Handles its vitals/collision.

**********************************************************************/

public class PlasmaWave extends Projectile implements IDrawable {
	
	private int lifetime;
	private Color waveColor;
	private int speed;
	private int direction;
	
	private int timeCount;
	private Sound missileFiredSound;
	private boolean sound;
	
	private int otherXCenter, otherYCenter, otherRadius, thisRadius;
	private boolean hitSomething;
	
	private AffineTransform myRotation, myTranslation, myScale;
	private Point2D topLeft, topRight, bottomLeft, bottomRight;
	
	private static Random generator = new Random();
	private float randomXLeftOuter, randomXLeftInner, randomXRightOuter, randomXRightInner, randomY;
	private double boundingWidth, boundingHeight;
	
	private ArrayList<Point> controlPointVector = new ArrayList<Point>();
	
	public PlasmaWave() {
		
	}
	
	public PlasmaWave(double boxWidth, double boxHeight, double xLoc, double yLoc, int spd, int hed, Color col, Sound s, boolean soundState) {
		waveColor = Color.MAGENTA;
		speed = spd + 5;
		direction = hed;
		setLifetime(1);
		timeCount = 0;
		
		missileFiredSound = s;
		
		setHit(false);
		
		if (soundState == true) {
			//missile was just created (fired), play the sound if its enabled.
			missileFiredSound.play();
		}
		
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();

		boundingWidth = boxWidth;
		boundingHeight = boxHeight;
		generateRandomLocation();
		//as in spiked grenade, create an array of points. They should range from 5times the width of the tank
		//and be set to a random
		
		//4 control points, a bounding box (drawn as well.) that is five times the width of the tank
		//the bottom left/right are fixed to preserve the overall width of the wave.
		bottomLeft = new Point ( (int) randomXLeftOuter, 0 );				//Q0
		bottomRight = new Point ( (int) randomXRightOuter, 0 );				//Q1
		//the top left/right points are variable, random.
		topLeft = new Point( (int) randomXLeftInner, (int) randomY );		//Q2
		topRight = new Point( (int) randomXRightInner, (int) randomY ); 	//Q3
		
		controlPointVector.add((Point) bottomLeft);		//Q0
		controlPointVector.add((Point) topLeft);		//Q1
		controlPointVector.add((Point) topRight);		//Q2
		controlPointVector.add((Point) bottomRight);	//Q3
		
		rotate(direction);
		translate(xLoc, yLoc);
	}
	
	public void generateRandomLocation() {
		  randomXLeftOuter = (float) -(generator.nextInt((int) (5 * (boundingWidth/2))));
		  randomXRightOuter = (float) (generator.nextInt((int) (5 * (boundingWidth/2))));
		  
		  randomXLeftInner = (float) -(generator.nextInt((int) (3 * (boundingWidth/2))));
		  randomXRightInner = (float) (generator.nextInt((int) (3 * (boundingWidth/2))));
		  
		  randomY = (float) (generator.nextInt((int) (3 * boundingHeight))); 
	}
	
	public int getLifetime() {
		return lifetime;
	}
	
	public void setLifetime(int l) {
		lifetime += l;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int d) {
		direction += d;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public Color getColor() {
		return waveColor;
	}
	
	public void move(int elapsedMillisecs, boolean soundState) {
		sound = soundState;
		//get speed
		double spd = getSpeed();
		
		//get direction
		double dir = (double) (-getDirection());
		
		//one pixel per 20 ms, is speed. (check)
		double dist = (spd/20) * ( (double) elapsedMillisecs);
		
		//rotation
		double deltaX = (double) Math.cos(Math.toRadians(90.0 - dir)) * dist;
		double deltaY = (double) Math.sin(Math.toRadians(90.0 - dir)) * dist;
		
		//translate it
		translate(deltaX, deltaY);
		
		timeCount += 1;
		
		//50 counts = 1 second of elapsed time.
		if (timeCount == 50) {
			setLifetime(-1);
			timeCount = 0;
		}
	}
	
	//a4
	public void rotate (double degrees) {
		myRotation.rotate (Math.toRadians(degrees));
	}
	
	public void scale(double sx, double sy) {
		myTranslation.scale(sx, sy);
	}
	
	public void translate (double dx, double dy) {
		myTranslation.translate(dx,  dy);
	}
	
	public void draw(Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(myTranslation);
		g2d.transform(myRotation);
		g2d.transform(myScale);
		
		//Draw lines connecting the control points. 
		g2d.drawLine((int) controlPointVector.get(0).getX(), (int) controlPointVector.get(0).getY(), (int) controlPointVector.get(1).getX(), (int) controlPointVector.get(1).getY());
		g2d.drawLine((int) controlPointVector.get(1).getX(), (int) controlPointVector.get(1).getY(), (int) controlPointVector.get(2).getX(), (int) controlPointVector.get(2).getY());
		g2d.drawLine((int) controlPointVector.get(2).getX(), (int) controlPointVector.get(2).getY(), (int) controlPointVector.get(3).getX(), (int) controlPointVector.get(3).getY());
		
		//drawBezierCurve.
		//pass in the control points
		//control points determine bounds
		drawBezierCurve(controlPointVector, g2d);
		
		g2d.setTransform(saveAT);
		
	}
	
	public void drawBezierCurve(ArrayList<Point> Q, Graphics2D g) {

		//Q0, bottom left point (start)
        int firstX = (int) (Q.get(0).getX());		
        int firstY = (int) (Q.get(0).getY());    
        
        for (float t = 0; t <= 1; t+= 0.01)
        {
            int nXone = 	(int) ( (Q.get(0)).getX() * Math.pow(1-t, 3) ); 			//blending function case 0
            int nXtwo = 	(int) ( (Q.get(1)).getX() * 3 * t * Math.pow(1-t, 2) ); 	//blending function case 1
            int nXthree =	(int) ( (Q.get(2)).getX() * 3 * (1-t) * Math.pow(t, 2) );	//blending function case 2
            int nXfour = 	(int) ( (Q.get(3)).getX() * Math.pow(t, 3) );				//blending function case 3
          	
            int nYone = 	(int) (Q.get(0).getY() * Math.pow(1-t, 3) ); 				//case 0, etc
            int nYtwo = 	(int) (Q.get(1).getY() * 3 * t * Math.pow(1-t, 2) ); 
            int nYthree =	(int) (Q.get(2).getY() * 3 * (1-t) * Math.pow(t, 2) );
            int nYfour = 	(int) (Q.get(3).getY() * Math.pow(t, 3) );
             
            int tnX = nXone + nXtwo + nXthree + nXfour;									//combine them
            int tnY = nYone + nYtwo + nYthree + nYfour;
            
            //draw the line
            g.drawLine(firstX, firstY, tnX, tnY);
            //reset for the next draw
            firstX = tnX;
            firstY = tnY;
        }
	}
		
	public boolean collidesWith(ICollider obj) {
		boolean result = false;
		
		int thisXCenter = (int) getXLocation(myTranslation); 
		int thisYCenter = (int) getYLocation(myTranslation);
		
		//what is the other object?
		if (obj instanceof Tank) {
			otherXCenter = (int) getXLocation(((Tank) obj).getTranslation()); 
			otherYCenter = (int) getYLocation(((Tank) obj).getTranslation());
			otherRadius = ((int) ((Tank) obj).getTankWidth()/2);
		}
		else if (obj instanceof Missile) {
			otherXCenter = (int) getXLocation(((Missile) obj).getTranslation()); 
			otherYCenter = (int) getYLocation(((Missile) obj).getTranslation());
			otherRadius = 5;
		}
		else if (obj instanceof Rock) {
			otherXCenter = (int) getXLocation(((Rock) obj).getTranslation()); 
			otherYCenter = (int) getYLocation(((Rock) obj).getTranslation()); 
			otherRadius = ((Rock) obj).getWidth()/2;
		}
		
		else if (obj instanceof Tree) {
			otherXCenter = (int) getXLocation(((Tree) obj).getTranslation()); 
			otherYCenter = (int) getYLocation(((Tree) obj).getTranslation());
			otherRadius = ((Tree) obj).getDiameter()/2;
		}

		int dx = (int) (thisXCenter - otherXCenter);
		int dy = (int) (thisYCenter - otherYCenter);
		int distBetweenCentersSqr = (dx*dx + dy*dy);
		
		//find the sum of the radii
		
		//the radius for a plasma wave is determined by its largest point from the center.
		if (randomXLeftOuter > randomXRightOuter && randomXLeftOuter > randomXLeftInner && randomXLeftOuter > randomXRightInner) {
			thisRadius = (int) randomXLeftOuter;
		}
		else if (randomXRightOuter > randomXLeftOuter && randomXRightOuter > randomXLeftInner && randomXRightOuter > randomXRightInner) {
			thisRadius = (int) randomXRightOuter;
		}
		else if (randomXLeftInner > randomXLeftOuter && randomXLeftInner > randomXRightOuter && randomXLeftInner > randomXRightInner) {
			thisRadius = (int) randomXLeftInner;
		}
		else {
			thisRadius = (int) randomXRightInner;
		}
		

		int radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) {
			result = true;
		}
		return result;
	}
	
	public AffineTransform getTranslation() {
		return myTranslation;
	}

	
	public void handleCollision(ICollider otherObject) {
		if (((GameObject)otherObject).getColor() == Color.BLUE && otherObject instanceof Tank ) {
			//if a player tank made the hit, award the points
			if (getColor() == Color.darkGray) {
				setHit(true);
			}
			//hit a tank, decrement its armor
			((Tank)otherObject).setArmorStrength(-10);
			setHit(true);
		}
		else {
			setHit(false);
		}
	}
	
	public void setHit(boolean h) {
		hitSomething = h;
	}
	
	public boolean getHit() {
		return hitSomething;
	}
	
}

