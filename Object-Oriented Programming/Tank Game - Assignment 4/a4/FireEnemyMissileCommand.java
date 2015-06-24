package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: FireEnemyMissileCommand
Class Description: Command to invoke firing a missile from a random
				  Enemy Tank. Not implemented/called in this assignment.
**********************************************************************/

public class FireEnemyMissileCommand extends AbstractAction {

	private GameWorld gmw;
	
	public FireEnemyMissileCommand() {
		//name it
		super("Fire Enemy Missile");
	}
	
	public FireEnemyMissileCommand(GameWorld g) {
		//name it
		super("Fire Enemy Missile");
		
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Fire Enemy Missile requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		//is the game NOT paused?
		if (gmw.getGameState() == true) {
			gmw.fireEnemyMissile();
			gmw.notifyObservers();
		}
		else {
			System.out.println("The game is currently paused. Unpause the game to input commands");
		}

	}

}