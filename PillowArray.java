//PillowArray.java: Pillow.java got too long, this class is here now.
import java.awt.Graphics;

//Contains all the pillows that exist
public class PillowArray
{
	private Pillow[] pillows;
	private PlayerBot player; //To set the pillow
	
	int pickedUp = -1; //This specially indicates the pillow pickedUp by the player
	
	//Number of pillows
	public static final int NUM_PILLOWS = 810;

	//Create a customizable pillow number
	public PillowArray(PlayerBot playerIn)
	{
		pillows = new Pillow[NUM_PILLOWS];
		player = playerIn;
		
		for (int i = 0; i < NUM_PILLOWS; i++)
		{
			//i % 10 ensures all digits exist in the "world"
			pillows[i] = new Pillow(i % 10, i);
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
	//This doesn't use get closest because getClosest is a bit more complicated than necessary
	public boolean pickUp()
	{
		double closest = 200; //The maximum legal distance for picking up
		int index = -1;

		double currentDist;
		for (int i = 0; i < pillows.length; i++)
		{
			try
			{
				if (!pillows[i].isPicked())
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
			}
			catch (NotAPillowException err) {System.out.println("Huh?");} //This exception should never be thrown
		}

		//If there is a close pillow, which is not being thrown
		if (index != -1 && !pillows[index].beingThrown())
		{
			if (pillows[index].getSolution() != player.getSolution())
			{
				return false; //Nope, this won't cut it
			}
			//Drop the current pillow, if there is one: there shouldn't be though
			if (pickedUp != -1)
			{
				pillows[pickedUp].drop();
			}

			//then pick it up
			if (pillows[index].setPicked())
			{
				pickedUp = index;
				player.setPicked(pillows[index]);
			}
		}
		return true;
	}

	//Helpful for the bot's implementation, and the required number. This number simulates the math problem
	public CycledPillow getClosestTo(int at_x, int at_y, int num)
	{
		double closest = -1; //The distance of the closest(so far)
		int index = -1; //The closest's index
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
			if (i != -1 && i != pickedUp && !pillows[i].isPicked() && pillows[i].numberIs(num)) //Don't see the pickedUp pillows the same way as the others.
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
		CycledPillow returnPillow = new CycledPillow(); //Indicates no pillow could be found
		if (index != -1)
		{
			returnPillow = new CycledPillow(pillows[index], -closestModX, -closestModY); //We use minus because this is representing a modified PILLOW POSITION
												      //However, previously we modified the PLAYER POSITION
												      //So, It's really in the opposite direction
		}
		return returnPillow;
	}
	
	//Show the picked up pillow, presumably above everything else
	public void showPickedUp(Graphics g)
	{
		for (int i = 0; i < pillows.length; i++)
		{
			//Make sure SOMETHING is picked up
			if (pillows[i].isPicked())
			{
				pillows[i].paintPillow(g);
			}
		}
	}

	public void throwPillow(int x, int y) //Mouse coordinates indicate direction: however, they are modified to represent distance from center
	{
		pillows[pickedUp].throwPillow((double)x - 540, (double)y - 440); //Throws the picked up pillow
		pillows[pickedUp].makePlayerThrown(); //The player threw it!
		pickedUp = -1;
		player.setPicked(new Pillow()); //Set it to empty
	}

	//After healing
	public void drop()
	{
		pillows[pickedUp].drop();
		pickedUp = -1;
	}
	
	//The heal
	public int getHeal()
	{
		if (pickedUp != 1)
		{
			return pillows[pickedUp].getHeal();
		}
		return 0;
	}

	public boolean holdingPillow()
	{
		return pickedUp != -1; //Returns false if not holding pillow, true if it is.
	}
	
	public Pillow getPillow(int index)
	{
		return pillows[index];
	}
}
