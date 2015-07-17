package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: DecreaseSpeedCommand
Class Description: Command to decrease the player tank's speed.
**********************************************************************/

public class DecreaseSpeedCommand extends AbstractAction {

	private GameWorld gmw;
	
	public DecreaseSpeedCommand() {
		//name it
		super("Decrease Speed");
	}
	
	public DecreaseSpeedCommand(GameWorld g) {
		//name it
		super("Decrease Speed");
		
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Decrease Speed requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		//is the game NOT paused?
		if (gmw.getGameState() == true) {
			gmw.decreaseSpeed();
			gmw.notifyObservers();
		}
		else {
			System.out.println("The game is currently paused. Unpause the game to input commands");
		}
	}
}
