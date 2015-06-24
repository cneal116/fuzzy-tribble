package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: HelpCommand
Class Description: Displays user input information that is not obvious,
					such as keyboard input.
**********************************************************************/

public class HelpCommand extends AbstractAction {

	public HelpCommand() {
		//name it
		super("Help");
	}
	
	public void actionPerformed(ActionEvent e) {
		//Display the source of the request
		System.out.println("Quit requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		
		//display help (original from a1)
		/*
		System.out.println("Press r to turn right");
		System.out.println("Press l to turn left");
		System.out.println("Press i to increase speed");
		System.out.println("Press k to decrease speed");
		System.out.println("Press f to fire a missile");
		System.out.println("Press e to fire a missile from a random enemy tank");
		System.out.println("Press t to advance the game time by one");
		System.out.println("Press d to generate a display of core game values");
		System.out.println("Press m to output a map describing the game world");
		System.out.println("Press ? to display help");
		System.out.println("Press q to quit");
		*/
		
		//Show a box containing info
				JOptionPane.showMessageDialog(null,
					    "Press the Right Arrow to turn right\n" + 
					    "Press the Left Arrow to turn left\n" + 
					    "Press the Up Arrow to increase speed\n" +		
					    "Press the Down Arrow to decrease speed\n" +
					    "Press the 'P' key to fire a (devestating) Plasma Wave\n" +
					    "Press the  'G' Key to fire a spiked grenade\n" +
					    "Press the Space Bar to fire a missile\n" +
					    "\n" +
						"The 'help' button displays this menu\n" +
						"The 'quit' button does exactly what you think it does.\n" +
						"\n" +
						"The 'Pause' button will pause the game and allow you to select tanks\n" +
						"Hold 'ctrl' on your keyboard and click to select multiple tanks.\n" +
						"\n" +
						"Selected tanks can have their directions reversed by pressing the 'Reverse' key." +
						"\n" +
						"While paused, press the 'Play' button to resume the game. Any selected tanks will remain highlighted." +
						"\n" +
						"\n" +
						"Various commands are also listed in the 'commands' menu at the top of the window.\n" +
						"the 'file' menu contains various unimplemented commands, but you can quit the game and enable/disable sound from here.\n" +
						"\n" +
						"When a player missile hits a tank, the score is increases by 10 points.\n" +
						"When a player grenade hits a tank, the score is increases by 50 points.\n" +
						"When a player plasma wave hits a tank, the score is increases by 100 points.\n"
						);
	}

}
