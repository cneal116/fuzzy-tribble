package a4;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import a4.GameWorld;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: ScoreView
Class Description: Panel that observes GameWorld. Reads/updates clock, lives, score
					and sound data.
**********************************************************************/

public class ScoreView extends JPanel implements IObserver {
	
	//create 'holder' variables
	private GameWorld gwo;
	private JLabel myProxyLabel1;
	private JLabel myProxyLabel2;
	private JLabel myProxyLabel3;
	private JLabel myProxyLabel4;
	private int clockHolder;
	private int scoreHolder;
	private int livesHolder;
	private boolean soundHolder;
	private String onOffString;

	public ScoreView() {
		
	}
	
	public ScoreView(GameWorld gw) {
		
		//create a local variable to hold the GameWorld.
		gwo = gw;
		//add the ScoreView as an observer to GameWorld.
		gwo.addObserver(this);
		
		
		JPanel topPanel = new JPanel();
		this.add(topPanel, BorderLayout.NORTH);
		JLabel myLabel1 = new JLabel ("Time: 0");
		topPanel.add(myLabel1);
		JLabel myLabel2 = new JLabel (" Score: 0");
		topPanel.add(myLabel2);
		JLabel myLabel3 = new JLabel (" Lives Left: 3");
		topPanel.add(myLabel3);
		JLabel myLabel4 = new JLabel (" Sound: Off");
		topPanel.add(myLabel4);
		
		myProxyLabel1 = myLabel1;
		myProxyLabel2 = myLabel2;
		myProxyLabel3 = myLabel3;
		myProxyLabel4 = myLabel4;
	}
	
	@Override
	public void update(IObservable proxy, Object arg) {
		clockHolder = ((GameWorldProxy) proxy).getClock();
		scoreHolder = ((GameWorldProxy) proxy).getScore();
		livesHolder = ((GameWorldProxy) proxy).getLives();
		soundHolder = ((GameWorldProxy) proxy).getSound();
		
		updateLabels();
	}
	
	public void updateLabels() {
		//get the state of the game sound. Is it on?
		if (soundHolder == true) {
			onOffString = "On";
		}
		else if (soundHolder == false) {
			onOffString = "Off";
		}
		//update the labels
		myProxyLabel1.setText("Time: " + (int) (clockHolder * 0.001));
		myProxyLabel2.setText("Score: " + scoreHolder);
		myProxyLabel3.setText("lives: " + livesHolder);
		myProxyLabel4.setText("Sound: " + onOffString);
	
	}
}
