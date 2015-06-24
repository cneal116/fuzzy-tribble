package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: TankBlockedCommand
Class Description: Calls the method within GameWorld that indicates
					the tank is blocked.
**********************************************************************/

public class TankBlockedCommand extends AbstractAction {

	private GameWorld gmw;
	
	public TankBlockedCommand() {
		//name it
		super("Tank Blocked");
	}
	
	public TankBlockedCommand(GameWorld g) {
		//name it
		super("Tank Blocked");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Tank blocked requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		gmw.tankAndLandscapeCollided();
		gmw.notifyObservers();
	}
}
