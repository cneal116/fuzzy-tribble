package a4;

import java.util.Observer;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.*;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: Game
Class Description: Constructs and instantiates the game components.
                  Creates all components of the GUI. Creates observers,
                  but does not register them.
**********************************************************************/

public class Game extends JFrame implements ActionListener {
	
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	
	private String output;
	
	//a3
	//timer!
	private Timer timer;
	private final int DELAY_IN_MSEC = 20;
	
	//CONTROLLER
	public Game() {

	  //a3 - GW needs a time in milliseconds to pass to game objects that move.
	  gw = new GameWorld(DELAY_IN_MSEC); 		//create "observable"
	  mv = new MapView(gw);			//create an "Observer" for the map
	  sv = new ScoreView(gw);		//create an "Observer" for the game state data
	  								//note that registration is done within these observers.
	  output = "Blank";				
	  
	  //the following code creates the entire GUI, with the exception of \ScoreView, which adds itself later.
	  setTitle("Tank Combat");
	  
	  //add a placeholder for the map
	  /*
	   //a2 version
	  JPanel centerPanel = new JPanel();
	  centerPanel.setBorder(new EtchedBorder());
	  centerPanel.setBackground(Color.WHITE);
	  centerPanel.setBorder (new LineBorder(Color.blue, 2));
	  this.add(centerPanel, BorderLayout.CENTER);
	  */
	  //a3 version
	  JPanel centerPanel = new JPanel();
	  this.add(centerPanel, BorderLayout.CENTER);
	  this.add(mv, BorderLayout.CENTER);
	  
	  //make a new jpanel, 2 pixel width blue border, in the NORTH of the window.
	  JPanel topPanel = new JPanel();
	  topPanel.setBorder (new LineBorder(Color.blue, 2));
	  this.add(topPanel, BorderLayout.NORTH);
	  this.add(sv, BorderLayout.NORTH);
	  
	  //input mechanism ONE
	  //add a bordered panel with 10x1 grid layout at the left of this frame
	  //removed unused buttons for a3
	  JPanel leftPanel = new JPanel();
	  leftPanel.setBorder(new TitledBorder(" Commands:  "));
	  leftPanel.setLayout(new GridLayout (20, 1));
	  this.add(leftPanel, BorderLayout.WEST);
	  //add buttons to the left panel
	  JButton myLeftButton1 = new JButton ("Enemy Switch");
	  //leftPanel.add(myLeftButton1);
	  JButton myLeftButton2 = new JButton ("Missile Hit Tank");
	  //leftPanel.add(myLeftButton2);
	  JButton myLeftButton3 = new JButton ("Missile Hit Missile");
	  //leftPanel.add(myLeftButton3);
	  JButton myLeftButton4 = new JButton ("Tank Blocked");
	  //leftPanel.add(myLeftButton4);
	  JButton myLeftButton5 = new JButton ("Tick");
	  //leftPanel.add(myLeftButton5);
	  
	  //pause and reverse commands
	  JButton myLeftButton1n = new JButton ("Pause");
	  leftPanel.add(myLeftButton1n);
	  JButton myLeftButton2n = new JButton ("Reverse");
	  myLeftButton2n.setEnabled(false);
	  leftPanel.add(myLeftButton2n);
	  
	  JButton myLeftButton6 = new JButton ("Help");
	  leftPanel.add(myLeftButton6);
	  JButton myLeftButton7 = new JButton ("Quit");
	  leftPanel.add(myLeftButton7);
	  
	  //Safety catch for Fire Missile, remove default binding for space.
	  myLeftButton1.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
	  myLeftButton2.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
	  myLeftButton3.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
	  myLeftButton4.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
	  myLeftButton5.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
	  
	  myLeftButton1n.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
	  myLeftButton7.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
	  
	  myLeftButton6.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
	  myLeftButton7.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
	  
	  //create AbstractActions (commands) to attach to the buttons created above
	  EnemySwitchCommand enemySwitchCommand = new EnemySwitchCommand(gw);
	  MissileHitPlayerCommand missileHitPlayerCommand = new MissileHitPlayerCommand(gw);
	  MissileHitMissileCommand missileHitMissileCommand = new MissileHitMissileCommand(gw);
	  TankBlockedCommand tankBlockedCommand = new TankBlockedCommand(gw);
	  TickCommand tickCommand = new TickCommand(gw);
	  
	  
	  //add commands to buttons
	  PlayPauseCommand playPauseCommand = new PlayPauseCommand(gw, myLeftButton1n, myLeftButton2n);
	  ReverseCommand reverseCommand = new ReverseCommand(gw);
	  HelpCommand helpCommand = new HelpCommand();
	  QuitCommand quitCommand = new QuitCommand();
	  
	  //add commands to buttons
	  myLeftButton1.setAction(enemySwitchCommand);
	  myLeftButton2.setAction(missileHitPlayerCommand);
	  myLeftButton3.setAction(missileHitMissileCommand);
	  myLeftButton4.setAction(tankBlockedCommand);
	  myLeftButton5.setAction(tickCommand);
	  
	  myLeftButton1n.setAction(playPauseCommand);
	  myLeftButton2n.setAction(reverseCommand);
	  myLeftButton2n.setEnabled(false);
	  myLeftButton6.setAction(helpCommand);
	  myLeftButton7.setAction(quitCommand);
	  
	  
	  //input mechanism TWO (key binding events)
	  int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
	  //InputMap imap = centerPanel.getInputMap(mapName);
	  InputMap imap = leftPanel.getInputMap(mapName);
	  //Turn Right
	  TurnRightCommand turnRightCommand = new TurnRightCommand(gw);
	  KeyStroke rightArrowKey = KeyStroke.getKeyStroke("RIGHT");
	  imap.put(rightArrowKey, "turnRight");
	 
	  //ActionMap amap = centerPanel.getActionMap();
	  ActionMap amap = leftPanel.getActionMap();
	  amap.put("turnRight", turnRightCommand);
	  
	  //Turn Left
	  TurnLeftCommand turnLeftCommand = new TurnLeftCommand(gw);
	  KeyStroke leftArrowKey = KeyStroke.getKeyStroke("LEFT");
	  imap.put(leftArrowKey, "turnLeft");
	  amap.put("turnLeft", turnLeftCommand);
	  
	  //Increase Speed
	  IncreaseSpeedCommand increaseSpeedCommand = new IncreaseSpeedCommand(gw);
	  KeyStroke upArrowKey = KeyStroke.getKeyStroke("UP");
	  imap.put(upArrowKey, "increaseSpeed");
	  amap.put("increaseSpeed", increaseSpeedCommand);
	  
	  //Decrease Speed
	  DecreaseSpeedCommand decreaseSpeedCommand = new DecreaseSpeedCommand(gw);
	  KeyStroke downArrowKey = KeyStroke.getKeyStroke("DOWN");
	  imap.put(downArrowKey, "decreaseSpeed");
	  amap.put("decreaseSpeed", decreaseSpeedCommand);
	  
	  //Fire missile
	  FireMissileCommand fireMissileCommand = new FireMissileCommand(gw);
	  KeyStroke spaceKey = KeyStroke.getKeyStroke("SPACE");
	  imap.put(spaceKey, "fireMissile");
	  amap.put("fireMissile", fireMissileCommand);
	  
	  //fire grenade
	  FireSpikedGrenadeCommand fireSpikedGrenadeCommand = new FireSpikedGrenadeCommand(gw);
	  KeyStroke gKey = KeyStroke.getKeyStroke("G");
	  imap.put(gKey, "fireSpikedGrenade");
	  amap.put("fireSpikedGrenade", fireSpikedGrenadeCommand);
	  
	  //fire plasma wave
	  FirePlasmaWaveCommand firePlasmaWaveCommand = new FirePlasmaWaveCommand(gw);
	  KeyStroke pKey = KeyStroke.getKeyStroke("P");
	  imap.put(pKey, "firePlasmaWave");
	  amap.put("firePlasmaWave", firePlasmaWaveCommand);
	  
	  //Switch enemy strategy
	  KeyStroke eKey = KeyStroke.getKeyStroke("E");
	  imap.put(eKey, "switchStrategy");
	  amap.put("switchStrategy", enemySwitchCommand);
	  myLeftButton1.setMnemonic(KeyEvent.VK_E);
	  
	  this.requestFocus();

	  //create the menu bar
		
	  NewCommand newCommand = new NewCommand();
	  SaveCommand saveCommand = new SaveCommand();
	  UndoCommand undoCommand = new UndoCommand();
	  //pass in the target gw for sound. THe method we need is held in gw...
	  SoundCommand soundCommand = new SoundCommand(gw);
	  AboutCommand aboutCommand = new AboutCommand();
	  QuitCommand quitMenuCommand = new QuitCommand();
		
		/*
		with the advent of strategies, this menu item has had its functionality altered. 
		It now changes the strategies of present enemy tanks, exactly like the "enemy switch" button.
		//FireEnemyMissileCommand fireEnemyMissileCommand = new FireEnemyMissileCommand(gw);
		*/
		
	  JMenuBar bar = new JMenuBar();
		
	  JMenu fileMenu = new JMenu("File");
	  	//contains new, save, undo, sound, about, quit
		JMenuItem mItem = new JMenuItem("New");
		mItem.setAction(newCommand);
		fileMenu.add(mItem);
		mItem = new JMenuItem("Save");
		mItem.setAction(saveCommand);
		fileMenu.add(mItem);
		mItem = new JMenuItem("Undo");
		mItem.setAction(undoCommand);
		fileMenu.add(mItem);
			
		JCheckBoxMenuItem soundMenu = new JCheckBoxMenuItem("Sound");
		soundMenu.setAction(soundCommand);
		fileMenu.add(soundMenu);
			
		mItem = new JMenuItem("About");
		mItem.setAction(aboutCommand);
		fileMenu.add(mItem);
		mItem = new JMenuItem("Quit");
		mItem.setAction(quitMenuCommand);
		fileMenu.add(mItem);
		bar.add(fileMenu);				//add file menu to menu bar...
			
		JMenu commandsMenu = new JMenu("Commands");
		//contains commands e, 1, 2, 3
			
		//Strategies has replaced this menu item's previous command and name
		//JMenuItem mItem1 = new JMenuItem("e - Fire Enemy Missile");
		//mItem1.setAction(fireEnemyMissileCommand);
		JMenuItem mItem1 = new JMenuItem("e - Switch Strategies");
		mItem1.setAction(enemySwitchCommand);
		commandsMenu.add(mItem1);
		mItem1.setEnabled(false);
		mItem1 = new JMenuItem("1 - Tank and Missle Collided");
		mItem1.setAction(missileHitPlayerCommand);
		commandsMenu.add(mItem1);
		mItem1.setEnabled(false);
		mItem1 = new JMenuItem("2 - Two Missiles Collided");
		mItem1.setAction(missileHitMissileCommand);
		commandsMenu.add(mItem1);
		mItem1.setEnabled(false);
		mItem1 = new JMenuItem("3 - Tank Collided with Landscape");
		mItem1.setAction(tankBlockedCommand);
		commandsMenu.add(mItem1);
		mItem1.setEnabled(false);
		bar.add(commandsMenu);			//add commands menu to the menu bar...
		this.setJMenuBar(bar);				//add menu bar to frame
	  
		//timer!
		timer = new Timer(DELAY_IN_MSEC, this);
		timer.start();
	  
	  setSize(900, 700);
	  //setSize(700, 700);
	  setResizable(false);
	  setDefaultCloseOperation (EXIT_ON_CLOSE);
	  setVisible(true);

	}


	public void actionPerformed(ActionEvent arg0) {
		gw.tickClock();
		//gw.notifyObservers();
	}
	
}