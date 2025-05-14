//This is the super-class for ALL BOTS
//This is abstract, and must be inherited
//This is the super class of all bots
//The really cool thing is that you can
//write your own "decisions" if you know how to 
//inherit this class and use it's variables
//and methods.

//It's not abstact because we sometimes need some empty bots
import java.awt.Graphics;
import java.awt.Color;

public class Bot
{
	//Coordinates:
	protected double x, y;
	
	//Required solution to "Math problem"
	protected int num;
	
	//Accuracy of the bot: this modifies the difficulty of the game
	protected int miss;
	
	//PillowArray: stores all the pillows
	protected PillowArray pillows;

	//BotArray: stores all the bots AND a kind of player bot
	protected BotArray bots;

	public Pillow pickedUp; //This is public: you can see it

	//For the PlayerBot
	protected boolean isPlayer;
	protected boolean immune;

	//For reality checking
    protected boolean existence; //Does it exist?

	protected int health;

	public Bot() 
	{
		existence = false;
	}

	public Bot(PillowArray pA, BotArray bA, int missIn) {
		//PillowArray
		pillows = pA;
		
		//BotArray
		bots = bA;
		
		//Exists
		existence = true;
		
		//The picked Up  pillow
		pickedUp = new Pillow(); 

		//The location
		x = Math.random() * Pillow.MAX_X;
		y = Math.random() * Pillow.MAX_Y;
		
		miss = missIn; //The amount the bot misses by
		
		//The random number
		num = (int)(Math.random() * 10); //The range is 0-9, not 0-10'
		
		//For the player: this is default
		isPlayer = false;
		immune = false;
		
		//Health
		health = 6; //Six half hearts
	}
	
	//returns the player's immune state: does not apply to bots
	public boolean isImmune()
	{
		if (existence)
		{
			return immune;
		}
		else
		{
			return false;
		}
	}
	
	//Coordinates
	public double getX()
	{
		if (existence)
		{
			return x;
		}
		else
		{
			System.out.println("ERROR: Not a bot");
			return -1.0; //I know this is not impossible, but it's the best I can do
		}
	}
	
	public double getY()
	{
		if (existence)
		{
			return y;
		}
		else
		{
			System.out.println("ERROR: this is not a bot");
			return -1.0;
		}
	}
	
	//Exists?
	public boolean exists()
	{
		return existence;
	}
	
	//Decide what you are going to do
	public void decide() throws NotAPillowException, NotABotException {} //The child class must decide: this is just the default
	
	//Moves: the decide method calls these alongside vision
	public void moveX(double amt)
	{
		if (!existence)
		{
			System.out.println("ERROR: this is not a bot");
		}
		else
		{
			x += amt;
			cycle(); //Cycle the bot!
		}
	}

	public void moveY(double amt)
	{
		if (existence)
		{
			y += amt;
			cycle();
		}
		else
		{
			System.out.println("ERROR: This is not a bot");
		}
	}

	//This differs from moveX() : This is since when the bot moves, the pillow needs to move with it, not when the player moves.
	//This should only be used by the bot's decisions, so it is protected
	protected void changeX(double amt)
	{
		if (existence)
		{
			moveX(amt);
			if (pickedUp.exists())
			{
				pickedUp.moveToPicked(x, y);
			}
		}
		else
		{
			System.out.println("ERROR: This is not a bot");
		}	
	}

	protected void changeY(double amt)
	{
		if (existence)
		{
			moveY(amt);
			if (pickedUp.exists())
			{
				pickedUp.moveToPicked(x, y);
			}
		}
		else
		{
			System.out.println("ERROR: This is not a bot");
		}
	}

	//Pick up the pillow! Use CycledPillow to get an accurate distance
	public void pickUp(CycledPillow pillow)
	{
		if (!pickedUp.exists() && pillow.exists() && existence)
		{
			if (pillow.setPicked((int)x, (int)y))
			{
				pickedUp = pillow.getPillow();
				num = (int)(Math.random() * 10); //Change the number
			}	
		}
		else if (!existence)
		{
			System.out.println("ERROR: This is not a bot");
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
		//Currently disabled
		//int randomX = (int)(Math.random() * 2 * miss) - miss;
		//int randomY = (int)(Math.random() * 2 * miss) - miss;

		//Use the math
		//if (toX - x != 0) //No zero division!
		//{
		//	pickedUp.throwPillow(Math.cos(Math.atan(((int)(toY - y)/(int)(toX - x))) + randomX), Math.sin(Math.atan(((int)(toY - y)/(int)(toX - x))) + randomY));
		pickedUp.throwPillow((int)(toX - x), (int)(toY - y));
		//}
		//else //The angle is pi/2 radians or 3pi/2 radians in this case, depending on the y
		//{
		//	if (toY - y > 0)
		//	{
		//		pickedUp.throwPillow(Math.cos(Math.PI / 2 + randomX), Math.sin(Math.PI / 2 + randomY));
		//	}
		//	else if (toY - y < 0)
		//	{
		//		pickedUp.throwPillow(Math.cos(Math.PI / 2 + randomX), Math.sin(Math.PI / 2 + randomY));
		//	}
		//	else //We want to stay still, choose a random direction
		//	{
		//		double randomAngle = Math.random() * 2 * Math.PI;
		//		pickedUp.throwPillow(Math.cos(randomAngle), Math.sin(randomAngle));
		//	}
		//}
		pickedUp = new Pillow(); //We are no longer holding a pillow
	}

	//The simplest method
	public void paintBot(Graphics g)
	{
		if (existence)
		{
			g.setColor(new Color(255, 0, 0));
			g.fillOval((int)x - 50, (int)y - 50, 100, 100); //You are at the center of the bot...
		}
		else
		{
			System.out.println("ERROR: Not a bot");
		}
	}

	//Collide with bots and the player
	public void collide() //This is gonna be nice. Collisions will decrease health
	{
		health--;
	}
	
	//Literally copy-pasted from Pillow.java
	//With minor edits
	public void cycle()
	{
		if (existence)
		{
			//really simple
			if (y > Pillow.MAX_Y)
			{
				y -= Pillow.MAX_Y + 150;
			}	
			else if (y < -150)
			{
				y += Pillow.MAX_Y;
			}

			if (x > Pillow.MAX_X)
			{
				x -= Pillow.MAX_X + 150;
			}
			else if (x < -150)
			{
				x += Pillow.MAX_X;
			}
		}
		else
		{
			System.out.println("ERROR: This is not a bot");
		}
	}
	
	//Very helpful method: just moves in the direction.
	public void moveToward(double to_x, double to_y)
	{
		if (existence)
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
		else
		{
			System.out.println("ERROR: This is not a bot");
		}	
	}
	
	//Returns the distance from the location
	//This is basically copy-pasted from the Pillow class
	public double getDist(double locx, double locy)
	{
		if (existence)
		{
			return Math.sqrt(Math.pow(locx - x, 2) + Math.pow(locy - y, 2));
		}
		else
		{
			System.out.println("This is not a bot");
		}
		return -1.0;
	}
	
	//Returns whether the bot was successfully destroyed
	public boolean loseHealth(int amt) //Amount may be customizable in the future by different pillows
	{
		health -= amt;
		if (health <= 0)
		{
			return true; //Successfully destroyed
		}
		return false;
	}
}

//A cycling bot, used for distance detection
class CycledBot
{
	Bot bot;
	private int modx;
	private int mody;
	private boolean existence;

	public CycledBot(Bot botIn, int modxIn, int modyIn)
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
			return (int)bot.getX() + (modx * Pillow.MAX_X); //Cycle the bot appropriately
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
			return (int)bot.getY() + (mody * Pillow.MAX_Y); //Cycle it
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
	public Bot getBot() throws NotABotException
	{
		if (existence)
		{
			return bot;
		}
		else
		{
			throw new NotABotException();
		}
	}

	//Returns the distance from the location
	//This is basically copy-pasted from the Pillow class
	public double getDist(double locx, double locy)
	{
		try
		{
			return Math.sqrt(Math.pow(locx - getX() + 50, 2) + Math.pow(locy - getY() + 50, 2));
		}
		catch (NotAPillowException err)
		{
			System.out.println("Error: NOT A PILLOW");
			return -1.0;
		}
	}
	
	//Check collisions
	public void checkCollisions()
	{
		
	}
}

//It's not a bot: error
class NotABotException extends Exception
{
	public NotABotException()
	{
		super("This is not really a bot");
	}
	
	public NotABotException(String msg)
	{
		super(msg);
	}
}
