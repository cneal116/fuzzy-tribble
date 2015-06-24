package a4.objects;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.Iterator;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: GameObjectsCollection
Class Description: Contains the logic for the Game Collection as well
					as the Iterator.
**********************************************************************/

public class GameObjectsCollection implements ICollection {
	
	private ArrayList<GameObject> list;
	
	public GameObjectsCollection() {
		list = new ArrayList<GameObject>();
	}

	public void add(GameObject newObject) {
		list.add(newObject);
		//System.out.println(newObject + "added");
	}

	public Iterator getIterator() {
		return new GameArrayListIterator();
	}
		
	
	private class GameArrayListIterator implements Iterator<GameObject> {
		int currentElementIndex;
		
		public GameArrayListIterator() {
			currentElementIndex = -1;
		}
	
		
		public boolean hasNext() {
			if (list.size() <= 0) {
				return false;
			}
			if (currentElementIndex == list.size() - 1) {
				return false;
			}
			else {
				return true;
			}
		}
		
		
		public void remove() {
			list.remove(currentElementIndex);
			currentElementIndex--;
		}

		
		public GameObject next() {
			currentElementIndex++;
			GameObject gObj = list.get(currentElementIndex);
			return gObj;
		}
	}

}
