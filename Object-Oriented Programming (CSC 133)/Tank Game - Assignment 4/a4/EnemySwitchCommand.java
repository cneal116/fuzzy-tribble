package a4;

import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: EnemySwitchCommand
Class Description: Switches enemy tank strategies.
**********************************************************************/

public class EnemySwitchCommand extends AbstractAction {

	private GameWorld gmw;
	
	public EnemySwitchCommand() {
		//name it
		super("Enemy Switch");
	}
	
	public EnemySwitchCommand(GameWorld g) {
		//name it
		super("Enemy Switch");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Enemy switch requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		//Flips the strategy, whatever it may be.
		gmw.setStrat();
		gmw.notifyObservers();
	}

}
