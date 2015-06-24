package a4;

import java.io.File;
import java.net.MalformedURLException;
import java.applet.Applet;
import java.applet.AudioClip;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: Sound
Class Description: Sound object to facilitate playing sounds.
**********************************************************************/

public class Sound {
	AudioClip myClip;
	
	public Sound(String fileName) {
		try {
			File file = new File(fileName);
			if (file.exists()) {
				myClip = Applet.newAudioClip(file.toURI().toURL());
			}
			else {
				throw new RuntimeException("Sound: file not found: "+ fileName);
			}
		}
		catch (MalformedURLException e) {
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
	}
	
	public void play() {
		myClip.play();
	}
	
	public void stop() {
		myClip.stop();
	}
	
	public void loop() {
		myClip.loop();
	}
}
