package a4;

import a4.objects.*;
import a4.objects.LandscapeItems.Rock;
import a4.objects.LandscapeItems.Tree;
import a4.objects.MoveableItems.Vehicle;
import a4.objects.MoveableItems.Projectiles.Missile;
import a4.objects.MoveableItems.Projectiles.PlasmaWave;
import a4.objects.MoveableItems.Projectiles.SpikedGrenade;
import a4.objects.MoveableItems.Vehicles.IStrategy;
import a4.objects.MoveableItems.Vehicles.StrategyA;
import a4.objects.MoveableItems.Vehicles.StrategyB;
import a4.objects.MoveableItems.Vehicles.Tank;

import java.awt.Color;
import java.util.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: GameWorld
Class Description: manipulates world objects and related game state 
                  data. Calls child classes.
**********************************************************************/

public class GameWorld implements IObservable, IGameWorld {

	//now using a collection and an iterator
	private GameObjectsCollection theGameObjectsCollection;
	//arraylist for observers
	private ArrayList<IObserver> obsv = new ArrayList<IObserver>();
	
	private Random generator = new Random();
	private int numTanks;
	private int numRocks;
	private int numTrees;
	
	
	private int clock;
	private int lives;
	private int score;
	private boolean soundBoolean;
	private int missile;
	
	//a3 - Time!
	private int timer;
	//a3 - collision sounds
	//missile hit tank
	private Sound missileHitTankSound;
	//missile fired
	private Sound missileFiredSound;
	//background sound
	private Sound backgroundSound;
	//hit a rock sound
	private Sound hitRockSound;
	//game state determining pause/play
	private boolean gameState;
	//counter for strategies
	private int gameCount;
	//counter to switch strategies according to game time
	private int stratCounter;
	//for the reverse command
	private int calculatedAngle;

	//tank strategy indicator (so they all have the same strategy)
	private IStrategy worldStrat;
	
	public GameWorld() {
		//create the collection
		setTheGameObjectsCollection(new GameObjectsCollection());
		
		//game state variables
		setClock(0);
		setLives(3);
		setScore(0);
		
		String Tanks = "Tanks", Trees = "Trees", Rocks = "Rocks";
		
		//The player has a tank, create it. Player tanks are not assigned a strategy.
		getTheGameObjectsCollection().add(new Tank(Color.darkGray, null, missileHitTankSound, hitRockSound));
		
		
		//prompt the user for input and store it, ignored in a3
		/*
		setNumTanks(getInput(Tanks));
		numRocks = getInput(Rocks);
		numTrees = getInput(Trees);
		*/
		
		//Hard coding values, as directed.
		setNumTanks(20);
		numRocks = 20;
		numTrees = 25;
		
		//send the input to init
		init(getNumTanks(), numRocks, numTrees);
	}
	
	//overloaded constructor if we want to specify a time for the gameworld.
	public GameWorld(int millisecondTime) {
		
		//create sounds
		//missile hit tank
		String soundDir1 = "." + File.separator + "sounds" + File.separator;
		String collisionSoundFile1 = "Bouncer 003.wav";
		String collisionSoundPath1 = soundDir1 + collisionSoundFile1;
		missileHitTankSound = new Sound(collisionSoundPath1);
		//tank fired a missile
		String soundDir2 = "." + File.separator + "sounds" + File.separator;
		String collisionSoundFile2 = "Quick Bass 001.wav";
		String collisionSoundPath2 = soundDir2 + collisionSoundFile2;
		missileFiredSound = new Sound(collisionSoundPath2);
		//background music
		String soundDir3 = "." + File.separator + "sounds" + File.separator;
		String collisionSoundFile3 = "01 A Night Of Dizzy SpellsWAV.wav";
		String collisionSoundPath3 = soundDir3 + collisionSoundFile3;
		backgroundSound = new Sound(collisionSoundPath3);
		//collided with a rock
		String soundDir4 = "." + File.separator + "sounds" + File.separator;
		String collisionSoundFile4 = "bump.wav";
		String collisionSoundPath4 = soundDir4 + collisionSoundFile4;
		hitRockSound = new Sound(collisionSoundPath4);
		
		//what is our default rate of time?
		setWorldTime(millisecondTime);
		
		//create the collection
		setTheGameObjectsCollection(new GameObjectsCollection());
		
		//game state variables
		setClock(0);
		setLives(3);
		setScore(0);
		
		String Tanks = "Tanks", Trees = "Trees", Rocks = "Rocks";
		
		//set the default world strategy (strategy a by default for ALL tanks, except the player)
		worldStrat = new StrategyA();
		
		//The player has a tank, create it. Player tanks are not assigned a strategy.
		getTheGameObjectsCollection().add(new Tank(Color.darkGray, null, missileHitTankSound, hitRockSound));
		
		//prompt the user for input and store it, ignored in a3
		/*
		setNumTanks(getInput(Tanks));
		numRocks = getInput(Rocks);
		numTrees = getInput(Trees);
		*/
		
		//Hard coding values, as directed.
		setNumTanks(20);
		numRocks = 20;
		//numRocks = 1;
		numTrees = 25;
		
		//send the input to init
		init(getNumTanks(), numRocks, numTrees);
		

		
		gameState = true;
	}
	
	private int getInput(String a) {
		Scanner in = new Scanner (System.in);
		System.out.println("Specify number of " + a + ": ");
		int line = in.nextInt();
		return line;
	}
	
	private void init(int a, int b, int c) {
		
		//for every tank, add a new tank.
		for (int i = 0; i < a; i++) {
			//create a new enemy tank and assign it a default strategy.
			Tank t1 = new Tank(Color.blue, worldStrat, missileHitTankSound, hitRockSound);
			getTheGameObjectsCollection().add(t1);
		}
		//for every rock...
		for (int i = 0; i < b; i++) {
			getTheGameObjectsCollection().add(new Rock(hitRockSound));
		}
		//for every tree...
		for (int i = 0; i < c; i++) {
			getTheGameObjectsCollection().add(new Tree());
		}
	}
	
	
	public  void turnRight() {
		//use an iterator to find the player tank within the collection and tell it to turn right.
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			
			GameObject g = i.next();
			
			if(g instanceof Tank) {	
				if (g.getColor() == Color.darkGray) {
					//((Tank) g).setDirection(5);
					//a4 uses AT, set a rotation
					((Tank) g).rotate(-5);
					((Tank) g).setBlocked(false);
				}		
			}
		}
	}
	
	
	public void turnLeft() {
		
		//use an iterator to find the player tank within the collection and tell it to turn left.
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			
			GameObject g = i.next();
			
			if(g instanceof Tank) {
				
				if (g.getColor() == Color.darkGray) {
					//((Tank) g).setDirection(-5);
					//a4 uses AT, set a rotation
					((Tank) g).rotate(5);
					((Tank) g).setBlocked(false);
				}
			}
		}
	}
	
	
	public void increaseSpeed() {
		
		//use an iterator to find the player tank within the collection and tell it to speed up.
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			//while there are more items in the collection...
			GameObject g = i.next();
			//is it a tank?
			if(g instanceof Tank) {
				//its a tank, is it blocked as well?
				if(((Tank) g).getBlocked() == false) {
					//if its not blocked, what color is it. Only the player tank can modify its speed.
					if (g.getColor() == Color.darkGray) {
						((Tank) g).setSpeed(1);
					}
				}
				else {
					//display that the tank is blocked.
					if(((Tank) g).getColor() == Color.darkGray) {
						System.out.println("Tank blocked! Change direction first, then change speed!");
					}
				}
			}
		}
	}
	
	
	public void decreaseSpeed() {
		
		//use an iterator to find the player tank within the collection and tell it to slow down.
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			//while there are more items in the collection...
			GameObject g = i.next();
			//is it a tank?
			if(g instanceof Tank) {
				//its a tank, is it blocked as well?
				if(((Tank) g).getBlocked() == false) {
					//if its not blocked, what color is it. Only the play tank can modify its speed.
					if (g.getColor() == Color.darkGray) {
						((Tank) g).setSpeed(-1);
					}
				}
				else {
					//display that the tank is blocked.
					if(((Tank) g).getColor() == Color.darkGray) {
						System.out.println("Tank blocked! Change direction first, then change speed!");
					}
				}
			}
		}
	}
	
	
	public void firePlayerMissile() {
		
		//use an iterator to find the player tank within the collection and tell it to fire.
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			//while there are more items in the collection...
			GameObject g = i.next();
			//is it a tank?
			if(g instanceof Tank) {
				//what is the color of the tank? We only want the player tank.
				if (g.getColor() == Color.darkGray) {
					//Any missiles left, player tank?
					if (((Tank) g).getMissileCount() != 0) {
						//create a new missile from the player tank's data.
						//originally, missiles were created at a fixed point. this point now lies in the center of the tank
						//additional data is needed when creating a missile.
						
						double dir = (double) ((MoveableItem) g).getDirection();
						int length = ((Tank) g).getBarrelLength();
						
						double deltaXBarrel = -(double) Math.cos(Math.toRadians(90.0 - dir)) * (1*length);
						double deltaYBarrel = (double) Math.sin(Math.toRadians(90.0 - dir)) * (1*length);
						
						double offsetX = ((Tank) g).getTranslation().getTranslateX() + deltaXBarrel;
						double offsetY = ((Tank) g).getTranslation().getTranslateY() + deltaYBarrel;
						
						getTheGameObjectsCollection().add(new Missile(offsetX, offsetY, ((MoveableItem) g).getSpeed(), ((MoveableItem) g).getDirection(), Color.darkGray, missileFiredSound, getSound()));
						
						((Tank) g).setMissileCount(-1);
						setMissile(1);
					}
					else {
						System.out.println("You are out of missiles!");
					}
				}
			}
		}
	}
	
	public void fireSpikedGrenade() {
			
			//use an iterator to find the player tank within the collection and tell it to fire.
			Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
			while(i.hasNext()) {
				//while there are more items in the collection...
				GameObject g = i.next();
				//is it a tank?
				if(g instanceof Tank) {
					//what is the color of the tank? We only want the player tank.
					if (g.getColor() == Color.darkGray) {
						//Any missiles left, player tank?
						if (((Tank) g).getGrenadeCount() != 0) {	
							double dir = (double) ((MoveableItem) g).getDirection();
							int length = ((Tank) g).getBarrelLength();
							
							double deltaXBarrel = -(double) Math.cos(Math.toRadians(90.0 - dir)) * (1*length);
							double deltaYBarrel = (double) Math.sin(Math.toRadians(90.0 - dir)) * (1*length);

							double offsetX = ((Tank) g).getTranslation().getTranslateX() + deltaXBarrel;
							double offsetY = ((Tank) g).getTranslation().getTranslateY() + deltaYBarrel;
							
							getTheGameObjectsCollection().add(new SpikedGrenade(offsetX, offsetY, ((MoveableItem) g).getSpeed(), ((MoveableItem) g).getDirection(), Color.darkGray, missileFiredSound, getSound()));
							
							((Tank) g).setGrenadeCount(-1);
							setMissile(1);
						}
						else {
							System.out.println("You are out of spiked grenades!");
						}
					}
				}
			}
		}
	
	public void firePlasmaWave() {
		
		//use an iterator to find the player tank within the collection and tell it to fire.
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			//while there are more items in the collection...
			GameObject g = i.next();
			//is it a tank?
			if(g instanceof Tank) {
				//what is the color of the tank? We only want the player tank.
				if (g.getColor() == Color.darkGray) {
					//Any missiles left, player tank?
					if (((Tank) g).getPlasmaCount() != 0) {	
						double dir = (double) ((MoveableItem) g).getDirection();
						int length = ((Tank) g).getBarrelLength();
						
						double deltaXBarrel = -(double) Math.cos(Math.toRadians(90.0 - dir)) * (1*length);
						double deltaYBarrel = (double) Math.sin(Math.toRadians(90.0 - dir)) * (1*length);

						double offsetX = ((Tank) g).getTranslation().getTranslateX() + deltaXBarrel;
						double offsetY = ((Tank) g).getTranslation().getTranslateY() + deltaYBarrel;
						
						double widthFactor = ((Tank) g).getTankWidth();
						double heightFactor = ((Tank) g).getTankHeight();
						
						getTheGameObjectsCollection().add(new PlasmaWave(widthFactor, heightFactor, offsetX, offsetY, ((MoveableItem) g).getSpeed(), ((MoveableItem) g).getDirection(), Color.darkGray, missileFiredSound, getSound()));
						
						((Tank) g).setPlasmaCount(-1);
						setMissile(1);
					}
					else {
						System.out.println("You are out of Plasma Waves!");
					}
				}
			}
		}
	}
	
	public void fireEnemyMissile() {
		
		//Note that this method is never called within A2.
		//use an iterator to find a random enemy tank within the collection and tell it to fire.
		int randomTank = generator.nextInt(getNumTanks() + 1);
		int q = 0;
		
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			//while there are more items in the collection...
			GameObject g = i.next();
			//is it a tank?
			if(g instanceof Tank) {
				//if its the random tank we want...
				if (q == randomTank) {
					//if its an enemy tank, ie its blue.
					if (g.getColor() == Color.blue) {
						if (((Tank) g).getMissileCount() > 0) {
							double dir = (double) ((MoveableItem) g).getDirection();
							int length = ((Tank) g).getBarrelLength();
							
							double deltaXBarrel = -(double) Math.cos(Math.toRadians(90.0 - dir)) * (1*length);
							double deltaYBarrel = (double) Math.sin(Math.toRadians(90.0 - dir)) * (1*length);
		
							double offsetX = ((Tank) g).getTranslation().getTranslateX() + deltaXBarrel;
							double offsetY = ((Tank) g).getTranslation().getTranslateY() + deltaYBarrel;
							
							getTheGameObjectsCollection().add(new Missile(offsetX, offsetY, ((MoveableItem) g).getSpeed(), ((MoveableItem) g).getDirection(), Color.blue, missileFiredSound, getSound()));
							
							((Tank) g).setMissileCount(-1);
							setMissile(1);
						}
					}
				}
				q++;
			}
		}
	}

	public void tankAndMissileCollided() {
			
		//Uses an iterator to scan through the collection and hit a random tank with a random missile.
		//the tank's armor is decremented and the tank is removed if necessary. Any missile that strikes
		//a tank is removed.
		
		if (missile != 0) {
			
			//add one to numtanks to include player tank
			
			int randomTank = generator.nextInt(getNumTanks() + 1);
			int q = 0;
			
			
			int randomMissile = generator.nextInt(missile);
			int r = 0;
			
			Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
			while(i.hasNext()) {
				//while there are more items in the collection...
				GameObject g = i.next();
				//is it a tank?
				if(g instanceof Tank) {
					//if its the random tank we want...
					if (q == randomTank) {
						//decrement its armor.
						((Tank) g).setArmorStrength(-1);
						//did this destroy the tank?
						if (((Tank) g).getArmorStrength() == 0) {
							//yes, what sort of tank are we removing?
							if (g.getColor() == Color.blue) {
								//removes the currentElement being looked at. If this statement is true, its a blue tank.
								i.remove();
								//enemy tanks respawn, so add a new one
								getTheGameObjectsCollection().add(new Tank(Color.blue, worldStrat, missileHitTankSound, hitRockSound));
							}
							// remove a player tank
							if (g.getColor() == Color.darkGray) {
								//its a gray tank, remove it
								i.remove();
								//does the player have more lives?
								if (getLives() > 0) {
									//yes, add another player tank and remove a life
									
									getTheGameObjectsCollection().add(new Tank(Color.darkGray, null, missileHitTankSound, hitRockSound));
									
									setLives(-1);
								}
								else {
									System.out.println("You are out of lives! Thanks for playing!");
									System.out.println("Goodbye!");
									System.exit(0);
								}
							}
						}
					}
					//random counter needs to be incremented if there isn't a match.
					q++;	
				}
			}
			
			//remove a missile that stuck the tank.
			Iterator<GameObject> j = theGameObjectsCollection.getIterator();
			while(j.hasNext()) {
				//while there are more items in the collection...
				GameObject h = j.next();
				//is it a missile?
				if(h instanceof Missile) {
					if (r == randomMissile) {
						//did the player hit something?
						if(h.getColor() == Color.darkGray) {
							//yes, give them 10 points...
							//System.out.println("You hit something!");
							setScore(10);
						}
							
						j.remove();
						setMissile(-1);
					}
					//no match found, keep searching.
					r++;
				}
			}	
		}
		else {
			System.out.println("No missiles to remove!");
		}
	}
	
	public void twoMissilesCollided() {
		
		//uses an iterator to find the two missiles in the collection and remove them, at random.
		if (missile > 2) {
			
			//add one to numtanks to include player tank
			int randomMissileOne = generator.nextInt(missile - 1);
			int q = 0;
			
			int randomMissileTwo = generator.nextInt(missile);
			int r = 0;
			
			//if the randoms match, they will attempt to remove the same  missile. Make them generate again.
			while (randomMissileOne == randomMissileTwo) {
				//randomMissileOne++;
				randomMissileOne = generator.nextInt(missile - 1);
				randomMissileTwo = generator.nextInt(missile);
			}
			
			//remove the first random missile
			Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
			while(i.hasNext()) {
				//while there are more items in the collection...
				GameObject g = i.next();
				//is it a tank?
				if(g instanceof Missile) {
					if (q == randomMissileOne) {
						i.remove();
						setMissile(-1);
					}
					q++;
				}
			}
			
			//remove the second random missile
			Iterator<GameObject> j = getTheGameObjectsCollection().getIterator();
			while(j.hasNext()) {
				//while there are more items in the collection...
				GameObject h = j.next();
				//is it a tank?
				if(h instanceof Missile) {
					if (r == randomMissileTwo) {
						j.remove();
						setMissile(-1);
					}
					r++;
				}
			}
		}
		
		//if we have exactly two missiles, there is no need to incorporate a random number, simply remove them both.
		else if (missile == 2) {
			Iterator<GameObject> k = getTheGameObjectsCollection().getIterator();
			while(k.hasNext()) {
				//while there are more items in the collection...
				GameObject b = k.next();
				//is it a tank?
				if(b instanceof Missile) {
					k.remove();
					setMissile(-1);	
				}
			}
		}
		else {
			System.out.println("Less than two missiles present! Fire more missiles first!");
		}
	}
	
	public void tankAndLandscapeCollided() {
		
		//use an iterator to find the player tank within the collection and tell it that its stuck.
		
		//randomly select a tank
		//add one to numtanks to include player tank
		int randomTank = generator.nextInt(getNumTanks() + 1);
		int q = 0;
		
		
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			//while there are more items in the collection...
			GameObject g = i.next();
			//is it a tank?
			if(g instanceof Tank) {
				//if its the random tank we want...
				if (q == randomTank) {
					if (((Tank) g).getSpeed() != 0) {
						((Tank) g).setSpeed(-((Tank) g).getSpeed());
					}
					((Tank) g).setBlocked(true);
				}
				q++;
			}
		}
	}
	
	public void tickClock() {
		//if the game is NOT paused...
		if (getGameState() == true) {
			//advance the clock.
			setClock(getWorldTime());
			
			//increment counters for strategies
			gameCount++;
			stratCounter++;
		
			//every 30 seconds, switch the strategies
			if (stratCounter == 1500) {
				stratCounter = 0;
				setStrat();
			}
			
			
			
			Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
			while(i.hasNext()) {
				//while there are more items in the collection...
				GameObject g = i.next();
				//and its a moveable item
				if(g instanceof MoveableItem) {
					//tell it to move, pass it the time and sound state for the game.
					((MoveableItem) g).move(getWorldTime(), getSound());
				
					if(g instanceof Tank) {
						//if its an enemy tank...
						if(g.getColor() == Color.blue) {
							//strategy A - fire a missile every second.
							if (((Tank) g).executeStrategy() == 1) {
								//if its strategy is A and one second of real time has passed...
								if (gameCount % 50 == 0) {
									//fire, as long as there are missiles in the enemy tank.
									if (((Tank) g).getMissileCount() > 0) {
										gameCount = 0;
										//missiles are to be fired from the tip of the barrel
										double dir = (double) ((MoveableItem) g).getDirection();
										int length = ((Tank) g).getBarrelLength();
										
										double deltaXBarrel = -(double) Math.cos(Math.toRadians(90.0 - dir)) * (1*length);
										double deltaYBarrel = (double) Math.sin(Math.toRadians(90.0 - dir)) * (1*length);
					
										double offsetX = ((Tank) g).getTranslation().getTranslateX() + deltaXBarrel;
										double offsetY = ((Tank) g).getTranslation().getTranslateY() + deltaYBarrel;
										
										getTheGameObjectsCollection().add(new Missile(offsetX, offsetY, ((MoveableItem) g).getSpeed(), ((MoveableItem) g).getDirection(), Color.blue, missileFiredSound, getSound()));
										
										((Tank) g).setMissileCount(-1);
										setMissile(1);
									}
								}
							}
							//Strategy B - fire a missile every other second.
							else if (((Tank) g).executeStrategy() == 2) {
								//if the strategy is B and two seconds have passed
								if (gameCount % 100 == 0) {
									//fire, as long as there are missiles in the enemy tank
									if (((Tank) g).getMissileCount() > 0) {
										gameCount = 0;
										//missiles are to be fired from the tip of the barrel
										double dir = (double) ((MoveableItem) g).getDirection();
										int length = ((Tank) g).getBarrelLength();
										
										double deltaXBarrel = -(double) Math.cos(Math.toRadians(90.0 - dir)) * (1*length);
										double deltaYBarrel = (double) Math.sin(Math.toRadians(90.0 - dir)) * (1*length);
					
										double offsetX = ((Tank) g).getTranslation().getTranslateX() + deltaXBarrel;
										double offsetY = ((Tank) g).getTranslation().getTranslateY() + deltaYBarrel;
										
										getTheGameObjectsCollection().add(new Missile(offsetX, offsetY, ((MoveableItem) g).getSpeed(), ((MoveableItem) g).getDirection(), Color.blue, missileFiredSound, getSound()));
										
										((Tank) g).setMissileCount(-1);
										setMissile(1);
									}
								}
							}
						}
					}
				}
			}
			
			//any collisions?
			Iterator<GameObject> i1 = getTheGameObjectsCollection().getIterator();
			while (i1.hasNext()) {
				GameObject currObj = i1.next();
				//get a collidable object
				if(currObj instanceof ICollider) {
					//check if this object collides with ANOTHER object
					Iterator<GameObject> i2 = getTheGameObjectsCollection().getIterator();
					while(i2.hasNext()) {
						GameObject otherObj = i2.next();
						if (otherObj instanceof ICollider) {
							//as long as its not the same object
							if (otherObj != currObj) {
								//check for collision
								if (currObj.collidesWith(otherObj)) {
									currObj.handleCollision(otherObj);
									if (currObj instanceof Missile) {
										if (((Missile) currObj).getHit() == true) {
											//if this is true, the player has struck an enemy tank.
											setScore(10);
										}
									}
									//grenade collision
									if (currObj instanceof SpikedGrenade) {
										if (((SpikedGrenade) currObj).getHit() == true) {
											//if this is true, the player has struck an enemy tank.
											setScore(50);
										}
									}
									//plasma collision
									if (currObj instanceof PlasmaWave) {
										if (((PlasmaWave) currObj).getHit() == true) {
											//if this is true, the player has struck an enemy tank.
											setScore(100);
										}
									}
								}
							}
						}
					}
				}
			}
			
			//remove old missiles AND dead tanks (tanks with 0 armor)
			//a4, also plasma waves and grenades...
			Iterator<GameObject> j = getTheGameObjectsCollection().getIterator();
			while(j.hasNext()) {
				
				//while there are more items in the collection...
				GameObject h = j.next();
				//is it a missle?
				if(h instanceof Missile) {
					if (((Missile) h).getLifetime() == 0) {
						setMissile(-1);
						j.remove();
						//System.out.println("Missle count: " + getMissile());
					}
				}
				//is it a grenade?
				if(h instanceof SpikedGrenade) {
					if (((SpikedGrenade) h).getLifetime() == 0) {
						setMissile(-1);
						j.remove();
						//System.out.println("Missle count: " + getMissile());
					}
				}
				//is it a wave?
				if(h instanceof PlasmaWave) {
					if (((PlasmaWave) h).getLifetime() == 0) {
						setMissile(-1);
						j.remove();
						//System.out.println("Missle count: " + getMissile());
					}
				}
				//is it a tank?
				if(h instanceof Tank) {
					//if (((Tank) h).getArmorStrength() == 0) {
					if (((Tank) h).getArmorStrength() <= 0) {
						//yes, what sort of tank are we removing?
						if (h.getColor() == Color.blue) {
							//removes the currentElement being looked at. If this statement is true, its a blue tank.
							j.remove();
							//enemy tanks respawn, so add a new one
							getTheGameObjectsCollection().add(new Tank(Color.blue, worldStrat, missileHitTankSound, hitRockSound));
						}
						// remove a player tank
						if (h.getColor() == Color.darkGray) {
							//its a gray tank, remove it
							j.remove();
							//does the player have more lives?
							if (getLives() > 0) {
								//yes, add another player tank and remove a life
								
								getTheGameObjectsCollection().add(new Tank(Color.darkGray, null, missileHitTankSound, hitRockSound));
								
								setLives(-1);
							}
							else {
								//out of lives
								System.out.println("You are out of lives! Thanks for playing!");
								System.out.println("Goodbye!");
								System.exit(0);
							}
						}
					}
				}
			}
			notifyObservers();
		}
	}
	
	public void map() {
		
		//calls the status of every item in the collection.
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			
			GameObject g = i.next();
			g.status();
		}
	}
	
	public void setStrat() {
		
		//switches the enemy tank strategies according to user input.
		//Now acts as a strategy flipper for a3. Any new tank will use the strategy set here.
		
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			//while there are more items in the collection...
			GameObject g = i.next();
			//is it a tank?
			if(g instanceof Tank) {
				//make sure its an enemy tank...
				if (g.getColor() == Color.blue) {
					//whats its strategy? Flip it.
					//its strategy a, flip it to b
					if (((Tank) g).executeStrategy() == 1) {
						//set the world strat, for new tanks that are created when this strat is active.
						worldStrat = new StrategyB();
						((Tank) g).setStrategy((IStrategy) worldStrat);		
					}
					else if (((Tank) g).executeStrategy() == 2 || ((Tank) g).executeStrategy() == 3 || ((Tank) g).executeStrategy() == 4) {
						worldStrat = new StrategyA();
						((Tank) g).setStrategy((IStrategy) new StrategyA());
					}
				}
			}
		}
		
	}
	
	public void setClock(int t) {
		clock += t;
	}
	
	public int getClock() {
		return clock;
	}
	
	public void setScore(int s) {
		score += s;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setLives(int l) {
		lives += l;
	}
	
	public int getMissile() {
		return missile;
	}
	
	public void setMissile(int m) {
		missile += m;
	}
	
	public int getLives() {
		return lives;
	}
	
	public boolean getSound() {
		return soundBoolean;
	}
	
	public void setSound(Boolean b) {
		soundBoolean = b;
	}
	
	public void soundFlip() {
		if (getSound() == true) {
			setSound(false);
			backgroundSound.stop();
		}
		else {
			setSound(true);
			if (getGameState() == true) {
				backgroundSound.loop();
			}
			
		}
	}
	
	public GameObjectsCollection getTheGameObjectsCollection() {
		return theGameObjectsCollection;
	}

	public void setTheGameObjectsCollection(GameObjectsCollection theGameObjectsCollection) {
		this.theGameObjectsCollection = theGameObjectsCollection;
	}

	
	public void addObserver(IObserver obs) {
		//add observers to the observers array list.
		obsv.add(obs);
	}

	
	public void notifyObservers() {
		//create a new proxy for the gameworld
		GameWorldProxy proxy = new GameWorldProxy(this);
		//cycle through observer list, make them call their updates...
		for (int i = 0; i < obsv.size(); i++) {
			IObserver currentObserver = obsv.get(i);
			currentObserver.update((GameWorldProxy)proxy, null);
		}
	}

	public int getNumTanks() {
		return numTanks;
	}

	public void setNumTanks(int numTanks) {
		this.numTanks = numTanks;
	}
	
	public void setWorldTime(int t) {
		timer = t;
	}
	
	public int getWorldTime() {
		return timer;
	}
	
	public void flipState() {
		if (gameState == true) {
			gameState = false;
			if (getSound() == true) {
				backgroundSound.stop();
			}
		}
		else {
			gameState = true;
			
			if (getSound() == true) {
				backgroundSound.loop();
			}
		}
	}
	
	public boolean getGameState() {
		return gameState;
	}
	
	public void Reverse() {
		//for every selected tank (enemy and player), reverse their direction.
		Iterator<GameObject> i = getTheGameObjectsCollection().getIterator();
		while(i.hasNext()) {
			//while there are more items in the collection...
			GameObject g = i.next();
			//is it a tank?
			if(g instanceof Tank) {
				//for every selected tank...
				if (((Tank) g).isSelected() == true) {
					//reverse
					//angle measure will need to be taken into account.
					int angle = ((Tank) g).getDirection();
					
					if (angle > 180) {
						calculatedAngle = angle - 180;
					}
					else {
						calculatedAngle = angle + 180;
					}
					//zero out the angle, and set it to whatever we just calculated
					((Tank) g).setDirection(-angle);
					((Tank) g).setDirection(calculatedAngle);
				}
			}
		}
	}

}
