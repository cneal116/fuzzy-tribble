package a4.objects.LandscapeItems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import a4.Sound;
import a4.objects.ICollider;
import a4.objects.IDrawable;
import a4.objects.LandscapeItem;
import a4.objects.MoveableItems.Projectiles.Missile;
import a4.objects.MoveableItems.Vehicles.Tank;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: Rock
Class Description: Defines a concrete game object called a rock
                  item. Child class of Landscape.java.
**********************************************************************/

public class Rock extends LandscapeItem {
	
	private static Random generator = new Random();
	
	private int width;
	private int height;
	
	private float randomX;
	private float randomY;
	
	//a4
	private AffineTransform myRotation, myTranslation, myScale;
	
	public Rock() {
		setwidth();
		setheight();
		generateRandomLocation();
		
		//getP().setLocation(randomX, randomY);
		
		setColor(Color.cyan);
		
		//a4
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();
		
		translate((double) randomX, (double) randomY);
	}
	
	public Rock(Sound s) {
		setwidth();
		setheight();
		//rockColor = Color.cyan;
		generateRandomLocation();
		//p.setLocation(randomX, randomY);
		
		//getP().setLocation(randomX, randomY);
		
		setColor(Color.cyan);
		
		//a4
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();
		
		translate((double) randomX, (double) randomY);
		//translate((double) 200, (double) 200);
	}
	
   public void setwidth() {
	   width = (1 + generator.nextInt(20));
   }
   
   public void setheight() { 
	   height = (1 + generator.nextInt(20));
   }
   
   public int getWidth() {
	   return width;
   }
   
   public int getHeight() {
	   return height;
   }
   
	public void generateRandomLocation() {
		  randomX = (float) (generator.nextInt(1024));
		  randomY = (float) (generator.nextInt(1024)); 
	}
	
	public void status() {
		System.out.println("Rock: loc=" + getP().getX() + " " + getP().getY() + " color=" + getColor() + " width=" + getWidth() + " height=" + getHeight());
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
	
	public AffineTransform getTranslation() {
		return myTranslation;
	}

	public void draw(Graphics2D g2d) {
		//int centerX = (int) getP().getX();
		//g.fillRect( (((int) getP().getX()) - (getWidth()/2)), (((int) getP().getY()) - (getHeight()/2)), getWidth(), getHeight());
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(myScale);
		g2d.transform(myTranslation);
		g2d.transform(myRotation);
		
		g2d.fillRect( -getWidth()/2, -getHeight()/2, getWidth(), getHeight());
		g2d.setTransform(saveAT);
	}
	
}
