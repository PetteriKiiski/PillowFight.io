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
	
	//PillowArray: stores all the pillows
	PillowArray pillows;

	public Bot(PillowArray pA) {
		pillows = pA;
		
		x = Math.random() * Pillow.MAX_X;
		y = Math.random() * Pillow.MAX_Y;
		
		num = (int)(Math.random() * 10); //The range is 0-9, not 0-10
	}
	
	//Decide what you are going to do
	abstract void decide() throws NotAPillowException; //The child class must decide
	
	//Moves: the decide method calls these alongside vision
	public void moveX(double amt)
	{
		x += amt;
		cycle();
	}

	public void moveY(double amt)
	{
		y += amt;
		cycle();
	}

	public void pickUp(Pillow pillow)
	{
		
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
			x += to_x;
			dontMoveX = true;
		}
		if (to_y <= amt && to_y >= -amt)
		{
			y += to_y;
			dontMoveY = true;
		}
		
		
		//Move, if we haven't already
		if (to_x > 0 && !dontMoveX)
		{
			x += amt;
		}	
		else if (to_x < 0 && !dontMoveX)
		{	
			x -= amt;
		}
		if (to_y > 0 && !dontMoveY)
		{
			y += amt;
		}
		else if(to_y < 0 && !dontMoveY)
		{
			y -= amt;
		}
	}
}
