package a4.objects.MoveableItems.Projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: Body
Class Description: Defines the body of a genade projectile.

**********************************************************************/

public class Body {
	private int myRadius;
	private Color myColor;
	private AffineTransform myRotation, myTranslation, myScale;
	
	public Body() {
		myRadius = 5;
		myColor = Color.orange;
		
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();	
	}
	
	public int getRadius() {
		return myRadius;
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
	
	public void draw (Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(myTranslation);
		g2d.transform(myRotation);
		g2d.transform(myScale);
		g2d.setColor(myColor);
		
		g2d.fillOval( -getRadius()/2, -getRadius()/2, getRadius(), getRadius());
		
	}
}
