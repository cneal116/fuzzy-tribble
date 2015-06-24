package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: ReverseCommand
Class Description: Calls the method within GameWorld that indicates
					the tank is blocked.
**********************************************************************/

public class ReverseCommand extends AbstractAction {

	private GameWorld gmw;
	private JButton ct;
	
	public ReverseCommand() {
		//name it
		super("Reverse");
	}
	
	public ReverseCommand(GameWorld g) {
		//name it
		super("Reverse");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Reverse requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		gmw.Reverse();
		gmw.notifyObservers();
	}
}

