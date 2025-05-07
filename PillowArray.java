//PillowArray.java: Pillow.java got too long, this class is here now.
import java.awt.Graphics;

//Contains all the pillows that exist
public class PillowArray
{
	private Pillow[] pillows;
	
	int pickedUp = -1; //This specially indicates the pillow pickedUp by the player

	//Create a customizable pillow number
	public PillowArray(int numPillows)
	{
		pillows = new Pillow[numPillows];
		for (int i = 0; i < numPillows; i++)
		{
			//i % 10 ensures all digits exist in the "world"
			pillows[i] = new Pillow(i % 10);
		}
	}
	
	public void paintPillows(Graphics g)
	{
		for (int i = 0; i < pillows.length; i++)
		{
			pillows[i].paintPillow(g);
		}
	}
	
	//Move ALL the pillows
	public void moveX(double amt)
	{
		for (int i = 0; i < pillows.length; i++)
		{
			if (i != pickedUp)
			{
				pillows[i].moveX(amt);
			}
		}
	}
	
	public void moveY(double amt)
	{
		for (int i = 0; i < pillows.length; i++)
		{
			if (i != pickedUp)
			{
				pillows[i].moveY(amt);
			}
		}
	}

	//Certain pillows will have velocity: this moves them accordingly.
	public void movePillows()
	{
		for (int i = 0; i < pillows.length; i++)
		{
			pillows[i].move();
		}
	}

	//Pick up the closest, reasonably close pillow
	public void pickUp()
	{
		double closest = 200; //The maximum legal distance for picking up
		int index = -1;

		double currentDist;
		for (int i = 0; i < pillows.length; i++)
		{
			try
			{
				currentDist = pillows[i].getDist(500, 400);
				//Make sure it's an appropriate distance(ensured by the closest's initial value)
				//AND it the closest pillow
				//AND it isn't just the same pillow we are already holding
				if (currentDist <= closest && i != pickedUp)
				{
					index = i;
					closest = currentDist;
				}
			}
			catch (NotAPillowException err) {System.out.println("Huh?");} //This exception should never be thrown
		}

		//If there is a close pillow, which is not being thrown
		if (index != -1 && !pillows[index].beingThrown())
		{
			//Drop the current pillow, if there is one: there shouldn't be though
			if (pickedUp != -1)
			{
				pillows[pickedUp].drop();
			}

			//Then pick it up
			pickedUp = index;
			pillows[pickedUp].setPicked();
		}
	}

	//Helpful for the bot's implementation, and the required number. This number simulates the math problem
	public CycledPillow getClosestTo(int at_x, int at_y, int num)
	{
		double closest = -1; //Just get a certain pillow's distance. If it's correct, we are kind of lucky
		int index = -1; //Use this pillow just because we need some preset one.
		double tempDist = -1; //This is used to check each possible distance
		double currentDist = -1; //This is the "working" distance of the bot from the pillow
		int closestModX = -2; //The modification of the coordinates based on the cycling
		int closestModY = -2;
		int currentModX = -2;
		int currentModY = -2;
		//That is, the distance of the pillow that is being currently checked
		for (int i = 0; i < pillows.length; i++)
		{
			//The reason for all this xmod behavior is to manage the cyclical behavior
			//Get the closest based on all these nine possible positions
			/*
			 * X \ X / X
			 * X - O - X
			 * X / X \ X
			 * 
			 * where O is the "actual" position.
			 * Take the one that is closest to the position.
			 * This will be the true distance.
			 */
			if (i != -1 && i != pickedUp && pillows[i].numberIs(num)) //Don't see the pickedUp pillows the same way as the others.
			{
				for (int xmod = -1; xmod <= 1; xmod++)
				{
					for (int ymod = -1; ymod <= 1; ymod++)
					{
						try
						{
							//MAX_X is the amount everything loops by (same with MAX_Y on the y-axis)
							tempDist = pillows[i].getDist(at_x + (xmod * Pillow.MAX_X), at_y + (ymod * Pillow.MAX_Y));
							//Only really consider how close the "looped" pillow is if we have looped yet
							if ((xmod == -1 && ymod == -1) || (tempDist <= currentDist)) //If it's closer than a looped version, then that's the "real distance"
							{
								currentDist = tempDist;
								currentModX = xmod; //Remember how much cycling and the direction occured, so that the bot knows which way to go
								currentModY = ymod;
							}
						}
						catch (NotAPillowException err) {} //Should never really occur
					}
				}
				if (currentDist <= closest || closest == -1) //If no pillows have been checked, this is closest by default
				{
					closest = currentDist;
					closestModX = currentModX;
					closestModY = currentModY;
					index = i;
				}
			}
		}
		CycledPillow myPillow = new CycledPillow(); //Indicates no pillow could be found
		if (index != -1)
		{
			myPillow = new CycledPillow(pillows[index], -closestModX, -closestModY); //We use minus because this is representing a modified PILLOW POSITION
												      //However, previously we modified the PLAYER POSITION
												      //So, It's really in the opposite direction
		}
		return myPillow;
		//return new CycledPillow(pillows[index], closestModX, closestModY); //Return the closest pillow
	}
	
	//Show the picked up pillow, presumably above everything else
	public void showPickedUp(Graphics g)
	{
		//Make sure SOMETHING is picked up
		if (pickedUp != -1)
		{
			pillows[pickedUp].paintPillow(g);
		}
	}

	public void throwPillow(int x, int y) //Mouse coordinates indicate direction: however, they are modified to represent distance from center
	{
		pillows[pickedUp].throwPillow(x - 540, y - 440); //Throws the picked up pillow
		pickedUp = -1;
	}

	public boolean holdingPillow()
	{
		return pickedUp != -1; //Returns false if not holding pillow, true if it is.
	}
}
