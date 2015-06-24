package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: TurnRightCommand
Class Description: Calls the method within GameWorld that tells a player
					tank to turn right.
**********************************************************************/

public class TurnRightCommand extends AbstractAction {

	private GameWorld gmw;
	
	public TurnRightCommand() {
		//name it
		super("Turn Right");
	}
	
	public TurnRightCommand(GameWorld g) {
		//name it
		super("Turn Right");
		
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Turn right requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		if (gmw.getGameState() == true) {
			gmw.turnRight();
			gmw.notifyObservers();
		}
		else {
			System.out.println("The game is currently paused. Unpause the game to input commands");
		}
	}

}
