package a4;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: IObservable
Class Description: Defines the methods to be used with an 'observerable' class,
					such as GameWorld.
**********************************************************************/

public interface IObservable {
	public void addObserver(IObserver obs);
	public void notifyObservers();
}
