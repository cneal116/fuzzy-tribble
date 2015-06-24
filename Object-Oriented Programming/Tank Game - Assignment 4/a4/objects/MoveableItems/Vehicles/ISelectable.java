package a4.objects.MoveableItems.Vehicles;

import java.awt.Graphics;
import java.awt.Point;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13
   
   Class: ISelectable
   Class Description: Defines the ISelectable object interface. Selectable
   					  Objects inherit these methods.
**********************************************************************/

public interface ISelectable {
	public void setSelected(boolean yesNo);
	public boolean isSelected();
	public boolean contains(Point p);
	public void draw(Graphics g);
}
