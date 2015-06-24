package a4;

import java.util.Observable;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: IObserver
Class Description: Defines the methods to be used within an 'Observer' type
					class, such as ScoreView or MapView.
**********************************************************************/

public interface IObserver {
	public void update(IObservable proxy, Object arg);
}
