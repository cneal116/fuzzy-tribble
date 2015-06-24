package a4;

import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: QuitCommand
Class Description: Displays a message box asking the user for confirmation
					before exiting the game.
**********************************************************************/

public class QuitCommand extends AbstractAction {

	private GameWorld gmw;
	
	public QuitCommand() {
		//name it
		super("Quit");
	}
	
	public QuitCommand(GameWorld g) {
		//name it
		super("Quit");
	
		//allow access to gameworld
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Quit requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		//verify they want to actually quit
		int result = JOptionPane.showConfirmDialog
				(null, 								//source of event
				"Are you sure you want to exit?",	//display mnessage
				"Confirm Exit",						//Title bar text
				JOptionPane.YES_NO_OPTION,			//button choices
				JOptionPane.QUESTION_MESSAGE);		//prompt icon
		
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		return;	//only if a no was chosen
	}

}
