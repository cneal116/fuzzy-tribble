package a4.objects.MoveableItems.Vehicles;

/**********************************************************************

/**********************************************************************

CSC 133 - Assignment 4 - Transformations

Author: Chris Neal
Date:   12/17/13

Class: StrategyA
Class Description: Defines the return type of the A strategy. This strategy
					indicates the enemy tanks should fire every tick of 
					the clock.
**********************************************************************/

public class StrategyA extends Tank implements IStrategy {
	
	public int apply() {
		//this return value tells the tank how it should fire on a 'tick'
		//In this case, fire every time the clock ticks.
		return 1;
	}
}
