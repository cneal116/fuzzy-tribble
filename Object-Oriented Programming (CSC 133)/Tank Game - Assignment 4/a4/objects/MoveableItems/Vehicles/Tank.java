package a4.objects.MoveableItems.Vehicles;

import a4.Sound;
import a4.objects.GameObject;
import a4.objects.ICollider;
import a4.objects.IDrawable;
import a4.objects.IMovable;
import a4.objects.LandscapeItems.Rock;
import a4.objects.LandscapeItems.Tree;
import a4.objects.MoveableItems.Vehicle;
import a4.objects.MoveableItems.Projectiles.Missile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
//might not need point...
import java.awt.Point;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.io.File;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: Tank
Class Description: Defines a concrete game object called a tank.
                  Child class of Vehicle.java.
**********************************************************************/

public class Tank extends Vehicle implements IDrawable, ISelectable {

	private static Random generator = new Random();
	private int armorStrength;
	private int missileCount;
	private Color tankColor;
	private float randomX, randomY;

	private int direction;
 	private int speed;
 	private boolean isBlocked;
 	
 	private IStrategy strategy;
 	
 	private int tankWidth;
 	private int tankHeight;
 	private int barrelLength;

 	private int otherRadius;
 	private int otherXCenter;
 	private int otherYCenter;
 	private boolean isSelected;
 	private int calculatedAngle;
 	
 	private boolean isSoundOn;
	private Sound missileHitSound, rockHitSound;
	private double adjustedX, adjustedY;
	
	private AffineTransform myRotation, myTranslation, myScale, inverseAllAT, allAT;
 	private int grenadeCount;
 	private int plasmaCount;
 	
	
	//a default enemy tank without a strategy.
	public Tank() {
		setArmorStrength(10);
		setMissileCount(10);
		tankColor = Color.blue;
		generateRandomLocation();
		//getP().setLocation(randomX, randomY);
		setBlocked(false);
		tankWidth = 26;
		tankHeight = 26;
		barrelLength = 30;
	}
	
	//Any tank with a strategy. Enemy tanks are 'blue'
	//Player tanks are 'dark grey.'
	public Tank(Color theColor, IStrategy strategy, Sound missileHit, Sound rockHit) {
		setArmorStrength(10);
		setMissileCount(500);
		tankColor = theColor;
		generateRandomLocation();
		
		setBlocked(false);
		
		this.strategy = strategy;
		
		tankWidth = 26;
		tankHeight = 26;
		barrelLength = 30;
		missileHitSound = missileHit;
		rockHitSound = rockHit;
		
		setGrenadeCount(20);
		setPlasmaCount(20);
		
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();
		
		allAT = new AffineTransform();
		
		translate((double) randomX, (double) randomY);
	}
	
	public void setGrenadeCount(int i) {
		grenadeCount += i;
	}
	
	public int getGrenadeCount() {
		return grenadeCount;
	}
	
	public void setPlasmaCount(int i) {
		plasmaCount += i;
	}
	
	public int getPlasmaCount() {
		return plasmaCount;
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
	}
	
	
	public void move(int elapsedMillisecs, boolean soundState) {
		//is the sound on?
		isSoundOn = soundState;

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
		
	}
	
	public void setDirection(int d) {
		direction += d;
		if (direction >= 360) {
			direction = 0;
		}
		if (direction < 0) {
			direction = 355;
		}
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setSpeed(int s) {
		if (speed >= 0) {
			speed += s;
			
			if (speed < 0) {
				speed = 0;
			}
		}	
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getArmorStrength() {
		return armorStrength;
	}

	public void setArmorStrength(int a) {
		armorStrength += a;
	}
	
	public int getMissileCount() {
		return missileCount;
	}

	public void setMissileCount(int m) {
		missileCount += m;
	}
	
	public Color getColor() {
		return tankColor;
	}
	
	public void generateRandomLocation() {
		  randomX = (float) (generator.nextInt(1024));
		  randomY = (float) (generator.nextInt(1024)); 
	}
	
	public boolean getBlocked() {
		return isBlocked;
	}
	
	public void setBlocked(boolean b) {
		isBlocked = b;
	}
	
	public int executeStrategy() {
		return strategy.apply();
	}
	
	public IStrategy getStrategy() {
		return strategy;
	}
	
	public void setStrategy(IStrategy s) {
		this.strategy = s;
	}
	
	public int getTankWidth() {
		return tankWidth;
	}
	
	public int getTankHeight() {
		return tankHeight;
	}
	
	public int getBarrelLength() {
		return barrelLength;
	}

	public void status() {
		System.out.println("Tank: loc=" + getP().getX() + " " + getP().getY() + " color=" + getColor() + " speed=" + getSpeed() + " heading=" + getDirection() + " armor=" + getArmorStrength() + " missiles=" + getMissileCount());
	}
	
	//a4
	public void rotate (double degrees) {
		setDirection((int) degrees);
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
	
	public void resetRotation() {
		myRotation.setToIdentity();
	}
	
	public AffineTransform getTranslation() {
		return myTranslation;
	}
	
	public AffineTransform getRotation() {
		return myRotation;
	}
	
	public void draw(Graphics2D g2d) {
		
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(myScale);
		allAT = (AffineTransform) myScale.clone();
		
		g2d.transform(myTranslation);
		allAT.concatenate(myTranslation);
		
		g2d.transform(myRotation);
		
		//draw the barrel
		g2d.drawLine(0, 0, 0, getBarrelLength());
		
		if (isSelected() == true) {
			g2d.fillRect(-getTankWidth()/2, -getTankHeight()/2, getTankWidth(), getTankHeight());
		}
		else {
			g2d.drawRect( -getTankWidth()/2, -getTankHeight()/2, getTankWidth(), getTankHeight());
		}
		
		g2d.setTransform(saveAT);
	}
	
	
	public boolean collidesWith(ICollider obj) {
		boolean result = false;
		
		//for clarity, where is the center?
		int thisXCenter = (int) getXLocation(myTranslation); //+ (getTankWidth()/2));
		int thisYCenter = (int) getYLocation(myTranslation); //+ (getTankHeight()/2));
		
		//System.out.println("tank color is " + getColor() + "Location is " + thisXCenter + " " + thisYCenter);

		//center of the other object...
		//what is the other object?
		if (obj instanceof Tank) {
			//otherXCenter = ((int) ((Tank) obj).getP().getX()); 
			//otherYCenter = ((int) ((Tank) obj).getP().getY()); 
			otherXCenter = (int) getXLocation(((Tank) obj).getTranslation()); 
			otherYCenter = (int) getYLocation(((Tank) obj).getTranslation()); 
			otherRadius = getTankWidth()/2;
			//System.out.println("tank color is " + getColor() + "Location is " + thisXCenter + " " + thisYCenter);
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
		//find dist between centers (use square, to avoid taking roots)
		int dx = (int) (thisXCenter - otherXCenter);
		int dy = (int) (thisYCenter - otherYCenter);
		int distBetweenCentersSqr = (dx*dx + dy*dy);
		
		//find the sum of the radii
		int thisRadius = getTankWidth()/2;

		//int otherRadius = 20;
		int radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) {
			result = true;
		}
		return result;
	}
		
	
	public void handleCollision(ICollider otherObject) {
		//first off, what hit the tank?
		//another tank?
		if (getColor() != Color.BLUE) {
			if (otherObject instanceof Tank || otherObject instanceof Rock) {
				//instead of stopping the tank and forcing the user to turn, this does that for them
				//it stops, reverses its heading, and continues
				//this is to make bumping into something less frustrating and makes gameplay more seamless.
				adjustAngle();
				
			}
		}
		//what if a missile hit us?
		if (otherObject instanceof Missile) {
			if (isSoundOn == true) {
				//rocks and tanks share the same collision sound (ie: if a tank hits another tank or a rock)
				missileHitSound.play();
			}
		}
		//unstick the objects.
		move();
	}

	
	public void setSelected(boolean yesNo) {
		isSelected = yesNo;
	}

	
	public boolean isSelected() {
		return isSelected;
	}

	
	public boolean contains(Point2D c) {	
		
		try {
			inverseAllAT = allAT.createInverse();
		} catch (NoninvertibleTransformException e1) { }
		Point2D localPoint = inverseAllAT.transform(c, null);

		
		int l = ((int) localPoint.getX()) - getTankWidth()/2;
		int r = ((int) localPoint.getX()) + getTankWidth()/2;
		//top Y Bounds, bottom Y Bounds
		int t = ((int) localPoint.getY()) + getTankHeight()/2;
		int b = ((int) localPoint.getY()) - getTankHeight()/2;
		
		
		//System.out.println("Stats - L: " + l + " R: " + r + " T: " + t + " B: " + b);
		
		//are we inside the shape?
		if ( (int) getXLocation(myTranslation) <= r && (int) getXLocation(myTranslation) >= l && (int) getYLocation(myTranslation) <= t && (int) getP().getY() >= b) {
			//yes, set it to selected.
			//System.out.println("I see a tank..");
			return true;
			
		}
		else {
			//System.out.println("no tank here...");
			return false;
		}
	}
	
	public void adjustAngle() {
		int angle = getDirection();
		if (angle > 180) {
			calculatedAngle = angle - 180;
		}
		else {
			calculatedAngle = angle + 180;
		}
		//zero out the angle, and set it to whatever we just calculated
		setDirection(-angle);
		setDirection(calculatedAngle);
		//now change the rotation
		getRotation().setToRotation(Math.toRadians(calculatedAngle));
		//play the sound
		if (isSoundOn == true) {
			//rocks and tanks share the same collision sound (ie: if a tank hits another tank or a rock)
			rockHitSound.play();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}
}
