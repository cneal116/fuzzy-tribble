package a4.objects.LandscapeItems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import a4.objects.IDrawable;
import a4.objects.LandscapeItem;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: Tree
Class Description: Defines a concrete game object called a tree.
                  Child Class of LandscapeItem.java.
**********************************************************************/

public class Tree extends LandscapeItem implements IDrawable {
	
	private static Random generator = new Random();
	
	private int diameter;
	
	private Color treeColor;
	
	private float randomX;
	private float randomY;

	//a4
	private AffineTransform myRotation, myTranslation, myScale;
	
	public Tree() {
		setDiameter();
		treeColor = Color.green;
		
		generateRandomLocation();
		//getP().setLocation(randomX, randomY);
		//a4
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();
		
		translate((double) randomX, (double) randomY);
	}
	
   public void setDiameter() {
	   diameter = (1 + generator.nextInt(20));
   }
   
   public int getDiameter() {
	   return diameter;
   }
   
	public Color getColor() {
		return treeColor;
	}
	
	public void generateRandomLocation() {
	  randomX = (float) (generator.nextInt(1024));
	  randomY = (float) (generator.nextInt(1024)); 
	}
	
	public void status() {
		System.out.println("tree: loc=" + getP().getX() + " " + getP().getY() + " color=" + getColor() + " Diameter=" + getDiameter());
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
		
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(myScale);
		g2d.transform(myTranslation);
		g2d.transform(myRotation);
		
		//for clarity, where is the center?
		int xCenter = 0 - (getDiameter()/2);
		int yCenter = 0 - (getDiameter()/2);
		
		g2d.fillOval( xCenter, yCenter, getDiameter(), getDiameter());
		g2d.setTransform(saveAT);
	}
}
