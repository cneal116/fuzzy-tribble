package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: TurnLeftCommand
Class Description: Calls the method within GameWorld that tells a player
					tank to turn left.
**********************************************************************/

public class TurnLeftCommand extends AbstractAction {

	private GameWorld gmw;
	
	public TurnLeftCommand() {
		//name it
		super("Turn Left");
	}
	
	public TurnLeftCommand(GameWorld g) {
		//name it
		super("Turn Left");
		
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Turn left requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		if (gmw.getGameState() == true) {
			gmw.turnLeft(); 
			gmw.notifyObservers();
		}
		else {
			System.out.println("The game is currently paused. Unpause the game to input commands");
		}

	}

}
