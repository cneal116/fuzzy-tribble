package a4.objects;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: IIterator
Class Description: Defines the methods to be used in the GameArrayListIterator 
					within GameObjectsCollection.
**********************************************************************/

public interface IIterator {
	public boolean hasNext();
	public GameObject getNext();
	public boolean remove();
}
