package a4;

import java.awt.event.ActionEvent;

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

public class UndoCommand extends AbstractAction {

	private GameWorld gmw;
	
	public UndoCommand() {
		//name it
		super("Undo");
	}
	
	public UndoCommand(GameWorld g) {
		//name it
		super("Undo");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Undo requested from " + e.getActionCommand() + " " + e.getSource().getClass());
	}

}

