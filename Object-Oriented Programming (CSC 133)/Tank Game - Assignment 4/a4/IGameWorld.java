package a4;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: IGameWorld
Class Description: Defines the methods to be used in GameWorld and its Proxy.
**********************************************************************/

import a4.objects.GameObjectsCollection;

public interface IGameWorld {
	//specifications here for all GameWorld methods		
	public  void turnRight();
	public void turnLeft() ;
	public void increaseSpeed();
	public void decreaseSpeed();
	public void firePlayerMissile();
	public void fireEnemyMissile();
	public void twoMissilesCollided(); 
	public void tankAndLandscapeCollided(); 
	public void tickClock();
	public void map();
	
	public void setClock(int t);
	public int getClock();
	public void setScore(int s);
	public int getScore();
	public void setLives(int l); 
	public int getMissile();
	public void setMissile(int m);
	public int getLives();
	public boolean getSound();
	public void setSound(Boolean b);
	public GameObjectsCollection getTheGameObjectsCollection();
	public void setTheGameObjectsCollection(GameObjectsCollection theGameObjectsCollection);
	public void addObserver(IObserver obs); 
	public void notifyObservers();
	public int getNumTanks();
	public void setNumTanks(int numTanks);

}
