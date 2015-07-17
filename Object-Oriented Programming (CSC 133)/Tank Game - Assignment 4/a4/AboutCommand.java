package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: AboutCommand
Class Description: Displays information about the program.
**********************************************************************/

public class AboutCommand extends AbstractAction {

	private GameWorld gmw;
	
	public AboutCommand() {
		//name it
		super("About");
	}
	
	public AboutCommand(GameWorld g) {
		//name it
		super("About");
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("About requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		//Show a box containing info
		JOptionPane.showMessageDialog(null,
			    "By: Chris Neal\n" + "Class: CSC 133\n" + "Program Version: a4");
	}

}

