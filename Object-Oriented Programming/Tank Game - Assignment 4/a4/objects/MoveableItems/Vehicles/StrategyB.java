package a4.objects.MoveableItems.Vehicles;

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: StrategyB
Class Description: Defines the return type of the B strategy. This strategy
					indicates the enemy tanks should fire every other tick of 
					the clock.
**********************************************************************/

public class StrategyB extends Tank implements IStrategy {
	
	public int apply() {
		//this return value tells the tank how it should fire on a 'tick'
		//In this case, simply fire every other tick.
		return 2;
	}
	
}
