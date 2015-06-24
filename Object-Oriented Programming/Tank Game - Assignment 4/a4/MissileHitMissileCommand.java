package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;


/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: MissileHitMissileCommand
Class Description: Calls the method within GameWorld that indicates two 
					missiles have collided.
**********************************************************************/

public class MissileHitMissileCommand extends AbstractAction {

	private GameWorld gmw;
	
	public MissileHitMissileCommand() {
		//name it
		super("Missile Hit Missile");
	}
	
	public MissileHitMissileCommand(GameWorld g) {
		//name it
		super("Missile Hit Missile");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Missile hit missile requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		gmw.twoMissilesCollided();
		gmw.notifyObservers();
	}

}
