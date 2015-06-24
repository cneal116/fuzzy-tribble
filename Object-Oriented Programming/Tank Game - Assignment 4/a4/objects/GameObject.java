package a4.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;


/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: GameObject
Class Description: Parent class to all game objects. This is an 
                  Abstract class.
**********************************************************************/

public abstract class GameObject implements IDrawable, ICollider {
	
 	private Color color;
 	private Point p = new Point();
 	private float xLocation;
 	private float yLocation;
 	
 	//private AffineTransform myAT = new AffineTransform();
 	//private AffineTransform myRotation = new AffineTransform();
 	//private AffineTransform myTranslation = new AffineTransform();
 	//private AffineTransform myScale = new AffineTransform();
 	
	public GameObject() {
		
	}
	
	public double getXLocation(AffineTransform myAT) {
		//return (float) getP().getX();
		return myAT.getTranslateX(); 
	}
	
	public double getYLocation(AffineTransform myAT) {
		//return (float) getP().getY();
		return myAT.getTranslateY();
	}
	
	/*
	public void setLocation(float xLoc, float yLoc) {
		p.setLocation(yLoc, yLoc);
	}*/
		
	public Color getColor() {
		return color;
	}

	public void status() {
		
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void draw(Graphics2D g2d) {
	
	}
	
	public boolean collidesWith(ICollider otherObject) {
		return false;
	}
	
	public void handleCollision(ICollider otherObject) {
		
	}
}
