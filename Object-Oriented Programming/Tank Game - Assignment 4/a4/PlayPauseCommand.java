package a4;

import java.awt.event.ActionEvent;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: SoundCommand
Class Description: Calls the method within GameWorld that sets the sound
					to on or off.
**********************************************************************/

public class PlayPauseCommand extends AbstractAction {

	private GameWorld gmw;
	private JButton bt, ct;
	
	public PlayPauseCommand() {
		//name it
		super("Pause");

	}
	
	public PlayPauseCommand(GameWorld g, JButton b, JButton c) {
		//name it
		super("Pause");

		gmw = g;
		bt = b;
		ct = c;
			
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Play/Pause requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		if (bt.getText() == "Pause") {
			bt.setText("Play");
			//enable the reverse button
			ct.setEnabled(true);
		}
		else {
			bt.setText("Pause");
			ct.setEnabled(false);
		}
		gmw.flipState();
		gmw.notifyObservers();
	}
}

