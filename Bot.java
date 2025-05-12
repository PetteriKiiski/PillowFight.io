//This is the super-class for ALL BOTS
//This is abstract, and must be inherited
//This is the super class of all bots
//The really cool thing is that you can
//write your own "decisions" if you know how to 
//inherit this class and use it's variables
//and methods.
import java.awt.Graphics;
import java.awt.Color;

abstract class Bot
{
	//Coordinates:
	protected double x, y;
	
	//Required solution to "Math problem"
	protected int num;
	
	//Accuracy of the bot: this modifies the difficulty of the game
	int miss;
	
	//PillowArray: stores all the pillows
	PillowArray pillows;

	//BotArray: stores all the bots AND a kind of player bot
	BotArray bots;

	Pillow pickedUp;

	public Bot(PillowArray pA, int missIn) {
		//PillowArray
		pillows = pA;
		
		//The picked Up  pillow
		pickedUp = new Pillow(); 

		//The location
		x = Math.random() * Pillow.MAX_X;
		y = Math.random() * Pillow.MAX_Y;
		
		miss = missIn; //The amount the bot misses by
		
		//The random number
		num = (int)(Math.random() * 10); //The range is 0-9, not 0-10
	}
	
	//Decide what you are going to do
	abstract void decide() throws NotAPillowException; //The child class must decide
	
	//Moves: the decide method calls these alongside vision
	public void moveX(double amt)
	{
		x += amt;
		cycle(); //Cycle the bot!
	}

	public void moveY(double amt)
	{
		y += amt;
		cycle();
	}

	//This differs from moveX() : This is since when the bot moves, the pillow needs to move with it, not when the player moves.
	//This should only be used by the bot's decisions, so it is protected
	protected void changeX(double amt)
	{
		moveX(amt);
		if (pickedUp.exists())
		{
			pickedUp.moveToPicked(x, y);
		}
	}

	protected void changeY(double amt)
	{
		moveY(amt);
		if (pickedUp.exists())
		{
			pickedUp.moveToPicked(x, y);
		}
	}

	//Pick up the pillow! Use CycledPillow to get an accurate distance
	public void pickUp(CycledPillow pillow)
	{
		if (!pickedUp.exists() && pillow.exists());
		{
			if (pillow.setPicked((int)x, (int)y))
			{
				pickedUp = pillow.getPillow();
				num = (int)(Math.random() * 10); //Change the number
			}	
		}
	}
	
	//Throw the pillow. This is quite simple actually, since it was already implemented
	public void throwPillow(double toX, double toY)
	{
		//Randomness in accuracy of throw: this is in DEGREES
		//Some trig needed in this!
		//Here's the math
		//angle = arctan(toY/toX)
		//then, add the random degree to this angle
		//arctan(toY/toX) + randomness
		//then, convert them both back, using cos or sin
		//X = cos(arctan(toY / toX) + randomness)
		//Y = sin(arctan(toY / toX) + randomness)
		//
		// By this, we randomly change the angle!
		
		//Randomize the angle
		int randomX = (int)(Math.random() * 2 * miss) - miss;
		int randomY = (int)(Math.random() * 2 * miss) - miss;

		//Use the math
		if (toX - x != 0) //No zero division!
		{
			pickedUp.throwPillow(Math.cos(Math.atan(((int)(toY - y)/(int)(toX - x))) + randomX), Math.sin(Math.atan(((int)(toY - y)/(int)(toX - x))) + randomY));
		}
		else //The angle is pi/2 radians or 3pi/2 radians in this case, depending on the y
		{
			if (toY - y > 0)
			{
				pickedUp.throwPillow(Math.cos(Math.PI / 2 + randomX), Math.sin(Math.PI / 2 + randomY));
			}
			else if (toY - y < 0)
			{
				pickedUp.throwPillow(Math.cos(Math.PI / 2 + randomX), Math.sin(Math.PI / 2 + randomY));
			}
			else //We want to stay still, choose a random direction
			{
				double randomAngle = Math.random() * 2 * Math.PI;
				pickedUp.throwPillow(Math.cos(randomAngle), Math.sin(randomAngle));
			}
		}
		pickedUp = new Pillow(); //We are no longer holding a pillow
	}

	//The simplest method
	public void paintBot(Graphics g)
	{
		g.setColor(new Color(255, 0, 0));
		g.fillOval((int)x - 50, (int)y - 50, 100, 100); //You are at the center of the bot...
	}

	//Collide with bots and the player
	public void collide() {}
	
	//Literally copy-pasted from Pillow.java
	//With minor edits
	public void cycle()
	{
		//really simple
		if (y >= Pillow.MAX_Y)
		{
			y -= Pillow.MAX_Y + 50;
		}
		else if (y <= -50)
		{
			y += Pillow.MAX_Y;
		}

		if (x >= Pillow.MAX_X)
		{
			x -= Pillow.MAX_X + 50;
		}
		else if (x <= -50)
		{
			x += Pillow.MAX_X;
		}
	}
	
	//Very helpful method: just moves in the direction.
	public void moveToward(double to_x, double to_y)
	{
		double amt = AnimateListener.MOVE_SPEED * AnimateListener.DELAY / 1000;
	
		//To ensure everything is only moved once
		boolean dontMoveX = false;
		boolean dontMoveY = false;
		
		if (to_x <= amt && to_x >= -amt) //Don't overshoot if you can reach
		{
			changeX(to_x);
			dontMoveX = true;
		}
		if (to_y <= amt && to_y >= -amt)
		{
			changeY(to_y);
			dontMoveY = true;
		}
		
		
		//Move, if we haven't already
		if (to_x > 0 && !dontMoveX)
		{
			changeX(amt);
		}	
		else if (to_x < 0 && !dontMoveX)
		{	
			changeX(-amt);
		}
		if (to_y > 0 && !dontMoveY)
		{
			changeY(amt);
		}
		else if(to_y < 0 && !dontMoveY)
		{
			changeY(-amt);
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
			if ( && bots[i].numberIs(num))
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

}

//A cycling bot
class CycledBot
{
	Bot bot;
	private int modx;
	private int mody;
	private boolean existence;

	public CycledBot(bot botIn, int modxIn, int modyIn)
	{
		bot = botIn;
		modx = modxIn;
		mody = modyIn;
		existence = true;
	}
	
	//
	public CycledBot()
	{
		existence = false;
	}

	public int getX()
	{
		if (existence)
		{
			return bot.getX() + (modx * Pillow.MAX_X); //Cycle the bot appropriately
		}
		else
		{
			System.out.println("ERROR: This bot does not exist");
		}
		return -1;
	}
	
	public int getY() throws NotAPillowException
	{
		if (existence)
		{
			return bot.getY() + (mody * Pillow.MAX_Y); //Cycle it
		}
		else
		{
			System.out.println("ERROR: This bot does not exist");
		}
		return -1;
	}

	public boolean exists()
	{
		return existence;
	}

	//If the bot needs to interact with the bot itself for some reason.
	public Pillow getPillow()
	{
		if (existence)
		{
			return bot;
		}
		else
		{
			System.out.println("ERROR: Not a bot");
		}
		return new Bot();
	}

	//Returns the distance from the location
	//This is basically copy-pasted from the Pillow class
	public double getDist(double locx, double locy)
	{5
		if (existence)
		{
			return Math.sqrt(Math.pow(locx - getX() + 25, 2) + Math.pow(locy - getY() + 25, 2));
		}
		else
		{
			System.out.println("This is not a bot!"):
		}
		return -1.0;
	}
}

