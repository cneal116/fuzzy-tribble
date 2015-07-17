package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;


/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: IncreaseSpeedCommand
Class Description: Calls the method within GameWorld that increases the
					player tank's speed.
**********************************************************************/

public class IncreaseSpeedCommand extends AbstractAction {

	private GameWorld gmw;
	
	public IncreaseSpeedCommand() {
		//name it
		super("Increase Speed");
	}
	
	public IncreaseSpeedCommand(GameWorld g) {
		//name it
		super("Increase Speed");
		
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Increase Speed requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		if (gmw.getGameState() == true) {
			gmw.increaseSpeed();
			gmw.notifyObservers();
		}
		else {
			System.out.println("The game is currently paused. Unpause the game to input commands");
		}

	}
}
