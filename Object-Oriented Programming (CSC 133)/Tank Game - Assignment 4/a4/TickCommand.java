package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: TickCommand
Class Description: Calls the method within the GameWorld that ticks the game
					clock and moves items.
**********************************************************************/

public class TickCommand extends AbstractAction {

	private GameWorld gmw;
	
	public TickCommand() {
		//name it
		super("Tick");
	}
	
	public TickCommand(GameWorld g) {
		//name it
		super("Tick");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Tick requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		gmw.tickClock();
		gmw.notifyObservers();
	}
}
