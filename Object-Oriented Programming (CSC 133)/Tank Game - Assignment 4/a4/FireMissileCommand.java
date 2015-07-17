package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: FireMissileCommand
Class Description: Command to invoke firing a missile from the player Tank.
**********************************************************************/

public class FireMissileCommand extends AbstractAction {

	private GameWorld gmw;
	
	public FireMissileCommand() {
		//name it
		super("Fire Missile");
	}
	
	public FireMissileCommand(GameWorld g) {
		//name it
		super("Fire Missile");
		
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Fire Missile requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		if (gmw.getGameState() == true) {
			gmw.firePlayerMissile();
			gmw.notifyObservers();
		}
		else {
			System.out.println("The game is currently paused. Unpause the game to input commands");
		}

	}

}

