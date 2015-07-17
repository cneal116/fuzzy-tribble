package a4.objects;

import java.util.Iterator;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: ICollection
Class Description: Defines the methods to be used in GameObjectsCollection.
**********************************************************************/

public interface ICollection {
	public void add(GameObject newObject);
	public Iterator<GameObject> getIterator();

}
