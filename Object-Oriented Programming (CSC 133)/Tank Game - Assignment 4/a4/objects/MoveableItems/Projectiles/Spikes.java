package a4.objects.MoveableItems.Projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: Spikes
Class Description: Defines the spikes of a genade projectile.

**********************************************************************/

public class Spikes {
	private Color myColor;
	private Point2D top, bottomLeft, bottomRight;
	
	private AffineTransform myTranslation;
	private AffineTransform myRotation;
	private AffineTransform myScale;
	
	public Spikes() {
		top = new Point (0,2);
		bottomLeft = new Point (-1, -2);
		bottomRight = new Point (1, -2);
		
		
		myColor = Color.red;
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();	
	}
	
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
		
		g2d.transform(myRotation);
		g2d.transform(myScale);
		g2d.transform(myTranslation);
		
		g2d.setColor(myColor);
		
		int [] xPts = new int [] {(int) top.getX(), (int) bottomLeft.getX(), (int) bottomRight.getX()};
		int [] yPts = new int [] {(int) top.getY(), (int) bottomLeft.getY(), (int) bottomRight.getY()};
		g2d.fillPolygon(xPts, yPts, 3);
		
		g2d.setTransform(saveAT);
	}
}
