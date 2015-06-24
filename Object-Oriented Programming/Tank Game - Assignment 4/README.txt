To launch this program from the command prompt, navigate to this folder and type:

java a4.Starter



In General:
	There are 20 tanks, 20 rocks and 25 trees that are randomly generated within a 1024x1024 game area. It is possible to drive the tank/fire projectiles beyond these bounds. If you cannot see the player tank, simply drag the viewport around while holding down left click. 

	If you are ever unfortunate enough to have a tank spawn on top of you, or you respawn on a rock, increase your speed and try moving the barrel. Eventually you will shoot out of it. This is a rare bug. I apologize. Alternatively, fire a plasma wave and destroy the offending tank. that always works. 



Regarding Collision:
	From my understanding, the assignment allowed us a lot of leeway for handling collisions. Every object is able to detect collisions, but I handled it differently for some. For example, a tank can collide with other tanks or rocks, or the edge of the gameworld. When it does, it reverses direction and continues away from the object. I felt this was smoother than setting the tank to a 'blocked' state and forcing the user to find a direction that was not obstructed. It smoothes out the gameplay. It essentially covers all the previous requirements as well. The tank sees that it is blocked; it stops and has to change directions before advancing. This simply does it quickly for the user. Additionally, I allowed tanks to pass through trees. This too felt smoother, so the user isn't constantly running into things. It also adds an interesting dynamic; a player can advance through the tree they are using for cover.  

	Missiles are destroyed when they reach ANY tank. This means enemy tanks can damage each other. They are also destroyed when they collide with a rock. Missiles are NOT able to pass through trees. As I said before, all objects are able to see that they are colliding, but they simply handle things differently.

	Plasma waves can pass through any object, including enemy tanks. They will destroy any tank they come into contact with, and have a short lifespan.

	Grenades are stronger than missiles, but behave the same way. They cannot pass through rocks, trees, or missiles. They are destroyed when they come into contact with a tank.



Strategies:
	The strategies are the same as the ones included in A3. One strategy fires every whole second, the other fires every other second. Strategies are switched every 30 seconds.



Zoom and Pan:
	You can pan by simply by draggin the mouse while holding down left click inside the game window. You can zoom with the mouse wheel (up for zoom in, back for zoom out). As required, you can only zoom/pan while paused.



Regarding The VTM and Selection functionality:
	 My VTM functions the same way as in the lecture slides, but does not rely on other methods to create the transformations. The transformations are made, then saved.

	Unfortunately, I could not restore selection functionality from A3. There is an error when converting the selected point through an inverse affine transformation. I've spent a great deal of time trying to find the source of this error, but I was unsuccessful. The functionality for selection still works, but the program is unable to determine the correct world point. Time did not permit me to solve this issue. My apologies.


On a side note, I'm not sure if you needed the .classpath and .project files, but I included them anyway. The a4 folder contains the java and compiled class files you need.
