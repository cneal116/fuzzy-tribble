package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: SoundCommand
Class Description: Calls the method within GameWorld that sets the sound
					to on or off.
**********************************************************************/

public class SoundCommand extends AbstractAction {

	private GameWorld gmw;
	
	public SoundCommand() {
		//name it
		super("Sound");
	}
	
	public SoundCommand(GameWorld g) {
		//name it
		super("Sound");
		
		gmw = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Sound requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		gmw.soundFlip();
		gmw.notifyObservers();
	}
}
