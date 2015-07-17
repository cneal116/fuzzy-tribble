package a4;

import a4.objects.GameObjectsCollection;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: GameWorldProxy
Class Description: creates a safe mirror image of the gameworld for
   					any gameworld observers. Allows them to safely 
   					receive data.
**********************************************************************/

public class GameWorldProxy implements IObservable, IGameWorld {

	private GameWorld realGameWorld;
	
	public GameWorldProxy(GameWorld gw) {
		realGameWorld = gw;
	}

	/*
	 *Please note that methods that were not needed for this assignment have been simply commented
	 *out for later use.
	 */
	
	public void turnRight() {
		//realGameWorld.turnRight();
	}

	
	public void turnLeft() {
		//realGameWorld.turnLeft();
	}

	
	public void increaseSpeed() {
		//realGameWorld.increaseSpeed();
	}

	
	public void decreaseSpeed() {
		//realGameWorld.decreaseSpeed();
	}

	
	public void firePlayerMissile() {
		//realGameWorld.firePlayerMissile();
	}

	
	public void fireEnemyMissile() {
		//realGameWorld.fireEnemyMissile();
	}

	
	public void twoMissilesCollided() {
		//realGameWorld.twoMissilesCollided();
	}

	
	public void tankAndLandscapeCollided() {
		//realGameWorld.tankAndLandscapeCollided();
	}


	public void tickClock() {
		//realGameWorld.tickClock();
	}

	
	public void map() {
		realGameWorld.map();
	}

	
	public void setClock(int t) {
		//realGameWorld.setClock(t);
	}

	
	public int getClock() {
		return realGameWorld.getClock();
	}

	
	public void setScore(int s) {
		//realGameWorld.setScore(s);
	}

	
	public int getScore() {
		return realGameWorld.getScore();
	}

	
	public void setLives(int l) {
		//realGameWorld.setLives(l);
	}

	
	public int getMissile() {
		return realGameWorld.getMissile();
	}

	
	public void setMissile(int m) {
		//realGameWorld.setMissile(m);
	}

	
	public int getLives() {
		return realGameWorld.getLives();
	}

	
	public boolean getSound() {
		return realGameWorld.getSound();
	}

	
	public void setSound(Boolean b) {
		//realGameWorld.setSound(b);
	}

	
	public GameObjectsCollection getTheGameObjectsCollection() {
		return realGameWorld.getTheGameObjectsCollection();
	}

	
	public void setTheGameObjectsCollection(GameObjectsCollection theGameObjectsCollection) {
		//realGameWorld.setTheGameObjectsCollection(theGameObjectsCollection);
	}

	
	public int getNumTanks() {
		return realGameWorld.getNumTanks();
	}

	
	public void setNumTanks(int numTanks) {
		//realGameWorld.setNumTanks(numTanks);
	}

	
	public void addObserver(IObserver obs) {
		realGameWorld.addObserver(obs);
	}

	
	public void notifyObservers() {
		realGameWorld.notifyObservers();
	}
	
	public void Reverse() {
		realGameWorld.Reverse();
	}
}
