package a4.objects.MoveableItems.Projectiles;

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

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: Missile
Class Description: Defines a concrete game object called a missile.
                  Child class of Projectile.java.
**********************************************************************/

public class Missile extends Projectile implements IDrawable {
	
	private int lifetime;
	private Color misColor;
	private int speed;
	private int direction;
	
	private int timeCount;
	private Sound missileFiredSound;
	private boolean sound;
	
	private int otherXCenter, otherYCenter, otherRadius;
	private boolean hitSomething;
	
	//a4
	private AffineTransform myRotation, myTranslation, myScale;
	
	//construct a missile
	public Missile() {
		
	}
	
	public Missile(double xLoc, double yLoc, int spd, int hed, Color col, Sound s, boolean soundState) {
		misColor = col;
		speed = spd + 5;
		direction = hed;
		setLifetime(5);
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

		rotate(direction + 90);
		translate(xLoc, yLoc);
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
		return misColor;
	}
	
	public void move() {
		
		//get current (old) location
		float locX = (float) getP().getX();
		float locY = (float) getP().getY();
		//get direction
		double dir = (double) getDirection();
		//get speed
		int spd = getSpeed();
		
		double deltaX = (double) Math.cos(Math.toRadians(90.0 - dir)) * spd;
		double deltaY = (double) Math.sin(Math.toRadians(90.0 - dir)) * spd;
		
		getP().setLocation((locX + deltaX), (locY + deltaY));
		setLifetime(-1);
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
	
	public void status() {
		System.out.println("Missile: loc=" + getP().getX() + " " + getP().getY() + " color=" + getColor() + " speed=" + getSpeed() + " heading=" + getDirection() + " lifetime=" + getLifetime());
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
	
	public void resetTransform() {
		myTranslation.setToIdentity();
	}
	
	public void draw(Graphics2D g2d) {

		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(myScale);
		g2d.transform(myTranslation);
		g2d.transform(myRotation);
		
		double dir = (double) getDirection();
		
		//tip
		double deltaXBarrel = (double) Math.cos(Math.toRadians(90.0 - dir));  
		double deltaYBarrel = (double) Math.sin(Math.toRadians(90.0 - dir));  
		
		//tip
		double offsetX = 0 + (deltaXBarrel);
		double offsetY = 0 + (deltaYBarrel);
		
		//g2d.fillRect(0, 0, 10, 10);
		Polygon np = new Polygon();
		np.addPoint( (int) offsetX, (int) offsetY+5);
		np.addPoint( (int) offsetX, (int) offsetY-5);
		np.addPoint( (int) offsetX+10, (int) offsetY);
		g2d.fillPolygon(np);
		
		g2d.setTransform(saveAT);
		
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
		int thisRadius = 5;

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
		if (((GameObject)otherObject).getColor() == Color.BLUE && otherObject instanceof Tank) {
			//if a player tank made the hit, award the points
			if (getColor() == Color.darkGray) {
				setHit(true);
			}
			//hit a tank, decrement its armor
			((Tank)otherObject).setArmorStrength(-1);
			//System.out.println("Missile hit a tank!");
		}
		else if (((GameObject)otherObject).getColor() == Color.darkGray && otherObject instanceof Tank) {
			((Tank)otherObject).setArmorStrength(-1);
			///System.out.println("Missile hit you!");
		}
		//what if two missiles collide?
		else if (otherObject instanceof Missile) {
			//set the other missiles' lifetime to zero
			((Missile) otherObject).setLifetime(-getLifetime());
		}
		setLifetime(-getLifetime());
	}
	
	public void setHit(boolean h) {
		hitSomething = h;
	}
	
	public boolean getHit() {
		return hitSomething;
	}
}
