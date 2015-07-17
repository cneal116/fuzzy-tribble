package a4.objects;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: MoveableItem
Class Description: Defines an abstract game object called a Moveable
                  item. Parent class of Projectile.java and Vehicle.java.
**********************************************************************/

public abstract class MoveableItem extends GameObject implements IMovable {

   private int direction;
   private int speed;
   
   public MoveableItem() {
	   
   }
   
   abstract public void move(); 
   
   public void move(int time, boolean s) {
	   
   }
 
   public void setSpeed(int i) {
	   speed += i;
   }
      
   public int getSpeed() {
	   return speed;
   }
   
   public int getDirection() {
	   return direction;
   }
}
