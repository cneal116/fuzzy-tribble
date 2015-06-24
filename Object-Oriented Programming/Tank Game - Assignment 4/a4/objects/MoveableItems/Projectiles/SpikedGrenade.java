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

Class: SpikedGrenade
Class Description: Assembles the shapes that create a spiked grenade. Also
				   controls its vitals/information.

**********************************************************************/

public class SpikedGrenade extends Projectile implements IDrawable {
	
	private Body myBody;
	private Spikes [] spikes;
	
	private AffineTransform myRotation, myTranslation, myScale;
	
	private int lifetime;
	private int speed;
	private int direction;
	
	private int timeCount;
	private Sound missileFiredSound;
	private boolean sound;
	
	private int otherXCenter, otherYCenter, otherRadius;
	private boolean hitSomething;
	
	private double flameOffset = 0;
	private double flameIncrement = +0.05;
	private double maxFlameOffset = 0.5;
	
	public SpikedGrenade() {
		
	}
	
	public SpikedGrenade(double xLoc, double yLoc, int spd, int hed, Color col, Sound s, boolean soundState) {
		speed = spd + 5;
		direction = hed;
		setLifetime(3);
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
		
		myBody = new Body();
		myBody.scale(2.5, 1.5);
		spikes = new Spikes [4];
		
		Spikes f0 = new Spikes(); f0.translate(0, 4); f0.scale(0.5, 0.8);
		spikes[0] = f0; //f0.setColor(Color.red);
		
		Spikes f1 = new Spikes(); f1.translate(0, 7); f1.rotate(-90); f1.scale(0.5, 0.5);
		spikes[1] = f1;
		
		Spikes f2 = new Spikes(); f2.translate(0, 4); f2.rotate(180); f2.scale(0.5, 0.8);
		spikes[2] = f2;
		
		Spikes f3 = new Spikes(); f3.translate(0, 7); f3.rotate(90); f3.scale(0.5, 0.5);
		spikes[3] = f3;
		
		translate(xLoc, yLoc);
	}
	
	public void setLifetime(int i) {
		lifetime += i;
	}
	
	public int getLifetime() {
		return lifetime;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getDirection() {
		return direction;
	}

	public void rotate (double degrees) {
		//setDirection((int) degrees);
		myRotation.rotate (Math.toRadians(degrees));
	}
	
	public void scale(double sx, double sy) {
		myTranslation.scale(sx, sy);
	}
	
	public void translate (double dx, double dy) {
		myTranslation.translate(dx,  dy);
	}
	
	public void move(int elapsedMillisecs, boolean soundState) {
		sound = soundState;
		//get speed
		double spd = getSpeed();
		
		//System.out.println("Grenade speed is " + spd);
		
		//get direction
		double dir = (double) (-getDirection());
		
		//one pixel per 20 ms, is speed. (check)
		double dist = (spd/20) * ( (double) elapsedMillisecs);
		
		//rotation
		double deltaX = (double) Math.cos(Math.toRadians(90.0 - dir)) * dist;
		double deltaY = (double) Math.sin(Math.toRadians(90.0 - dir)) * dist;
		
		//translate it
		//.out.println("Move translation for grenade is " + deltaX + " " + deltaY);
		moveSpikes();
		translate(deltaX, deltaY);
		
		timeCount += 1;
		
		
		//50 counts = 1 second of elapsed time.
		if (timeCount == 50) {
			setLifetime(-1);
			timeCount = 0;
		}
	}
	
	public void moveSpikes() {
		//myTranslation.translate(1, 1);
		myRotation.rotate(Math.toRadians(1));
		
		flameOffset += flameIncrement;
		for (Spikes f:spikes) {
			f.translate(0, flameOffset);
		}
		
		if (Math.abs(flameOffset) >= maxFlameOffset) {
			flameIncrement *= -1;
		}
	}
	
	public void draw(Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(myTranslation);
		g2d.transform(myRotation);
		g2d.transform(myScale);
		
		myBody.draw(g2d);
		for (Spikes f : spikes) {
			f.draw(g2d);
		}
		
		g2d.setTransform(saveAT);
		
	}
	
	public boolean collidesWith(ICollider obj) {
		boolean result = false;

		int thisXCenter = (int) getXLocation(myTranslation); //+ (getTankWidth()/2));
		int thisYCenter = (int) getYLocation(myTranslation);//+ (getTankHeight()/2));
		
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
		int thisRadius = 10;

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
			((Tank)otherObject).setArmorStrength(-5);
			setHit(true);
			//System.out.println("Missile hit a tank!");
		}
		else if (((GameObject)otherObject).getColor() == Color.darkGray && otherObject instanceof Tank) {
			((Tank)otherObject).setArmorStrength(-5);
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
