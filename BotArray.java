//BotArray.java : stores all the bots
import java.awt.Graphics;

public class BotArray
{
	private Bot[] bots;
	private PillowArray pillows;
	private PlayerBot player; //Represents the player

	public BotArray(int numBots, PillowArray pillowsIn, int miss, PlayerBot playerIn)
	{
		pillows = pillowsIn;
		bots = new Bot[numBots + 1]; //+1 because of the player
		player = playerIn;
		bots[0] = player;

		for (int i = 1; i <= numBots; i++)
		{
			bots[i] = new DumbBot(pillows, this, miss); //I would make it a loop, but it's a bit too complicated for my liking.
		}
	}
	
	//FOR ALL OF THESE LOOPS, we start at 1, because 0 is the player
	
	//Set the immunity of the player
	public void setImmunity(boolean value)
	{
		player.setImmune(value);
	}
	
	public void paintBots(Graphics g)
	{
		for (int i = 1; i < bots.length; i++)
		{
			bots[i].paintBot(g);
		}
	}
	
	public void moveX(double amt)
	{
		for (int i = 1; i < bots.length; i++)
		{
			bots[i].moveX(amt);
		}
	}
	
	public void moveY(double amt)
	{
		for (int i = 1; i < bots.length; i++)
		{
			bots[i].moveY(amt);
		}
	}
	
	public void decide()
	{
		for (int i = 1; i < bots.length; i++)
		{
			try
			{
				bots[i].decide(); //They will move... or not, their choice.
			}
			catch (Exception err) //Too many kinds of exceptions
			{
				System.err.println("ERROR: There was an error deciding what the bot is to do");
			}
		}
	}
	
	//Helpful for the bot's implementation. Modified-copypasted from Pillow.java
	public CycledBot getClosestTo(int at_x, int at_y, int num, Bot isBot)
	{
		double closest = -1; //The distance of the closest(so far)
		int index = -1; //The closest's index
		double tempDist = -1; //This is used to check each possible distance
		double currentDist = -1; //This is the "working" distance of the bot from the bot
		int closestModX = -2; //The modification of the coordinates based on the cycling
		int closestModY = -2;
		int currentModX = -2;
		int currentModY = -2;
		//That is, the distance of the bot that is being currently checked
		for (int i = 0; i < bots.length; i++)
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
			if (bots[i] != isBot && !bots[i].isImmune())
			{
				for (int xmod = -1; xmod <= 1; xmod++)
				{
					for (int ymod = -1; ymod <= 1; ymod++)
					{
						//MAX_X is the amount everything loops by (same with MAX_Y on the y-axis)
						tempDist = bots[i].getDist(at_x + (xmod * Pillow.MAX_X), at_y + (ymod * Pillow.MAX_Y));
						//Only really consider how close the "looped" bot is if we have looped yet
						if ((xmod == -1 && ymod == -1) || (tempDist <= currentDist)) //If it's closer than a looped version, then that's the "real distance"
						{
							currentDist = tempDist;
							currentModX = xmod; //Remember how much cycling and the direction occured, so that the bot knows which way to go
							currentModY = ymod;
						}
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
		CycledBot returnBot = new CycledBot(); //Indicates no pillow could be found
		if (index != -1)
		{
			returnBot = new CycledBot(bots[index], -closestModX, -closestModY); //We use minus because this is representing a modified PILLOW POSITION
												      //However, previously we modified the PLAYER POSITION
												      //So, It's really in the opposite direction
		}
		return returnBot;
	}
}
