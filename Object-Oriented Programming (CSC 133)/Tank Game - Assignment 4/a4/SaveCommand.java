package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: SaveCommand
Class Description: Placeholder for a 'save' command. Only knows that it
					was invoked, but currently has no functionality other
					than an output string.
**********************************************************************/

public class SaveCommand extends AbstractAction {

	private GameWorld gmw;
	
	public SaveCommand() {
		//name it
		super("Save");
	}
	
	public SaveCommand(GameWorld g) {
		//name it
		super("Save");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Save requested from " + e.getActionCommand() + " " + e.getSource().getClass());
	}

}

