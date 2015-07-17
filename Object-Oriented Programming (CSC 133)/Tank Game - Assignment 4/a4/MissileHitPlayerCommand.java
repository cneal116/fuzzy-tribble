package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: MissileHitPlayerCommand
Class Description: Calls the method within GameWorld that indicates a 
					random missile has struck a random tank.
**********************************************************************/

public class MissileHitPlayerCommand extends AbstractAction {

	private GameWorld gmw;
	
	public MissileHitPlayerCommand() {
		//name it
		super("Missile Hit Tank");
	}
	
	public MissileHitPlayerCommand(GameWorld g) {
		//name it
		super("Missile Hit Tank");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Missile hit tank requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		//VERIFY 
		gmw.tankAndMissileCollided();
		gmw.notifyObservers();
	}
}
