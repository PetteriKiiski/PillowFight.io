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
		pickedUp.throwPillow(Math.cos(Math.atan(((int)(toX - x))) + randomX), Math.sin(Math.atan(((int)(toY - y))) + randomY));
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
}
