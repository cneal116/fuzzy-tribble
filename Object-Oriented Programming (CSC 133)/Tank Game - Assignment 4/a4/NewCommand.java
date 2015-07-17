package a4;

import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: NewCommand
Class Description: Placeholder for a 'new' command. Only knows that it
					was invoked, but currently has no functionality other
					than an output string.
**********************************************************************/

public class NewCommand extends AbstractAction {

	private GameWorld gmw;
	
	public NewCommand() {
		//name it
		super("New");
	}
	
	public NewCommand(GameWorld g) {
		//name it
		super("New");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("New requested from " + e.getActionCommand() + " " + e.getSource().getClass());
	}

}
