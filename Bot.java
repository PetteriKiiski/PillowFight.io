//This is the super-class for ALL BOTS
//This is abstract, and must be inherited
import java.awt.Graphics;
import java.awt.Color;

abstract class Bot
{
	//Coordinates:
	private double x, y;
	
	//PillowArray: stores all the pillows
	PillowArray pillows;

	public Bot(PillowArray pA) {
		pillows = pA;
		
		x = 100;
		y = 100;
	}
	
	//Decide what you are going to do
	abstract void decide(); //The child class must decide
	
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
		g.fillOval((int)x, (int)y, 100, 100);
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
			y -= Pillow.MAX_Y + 50; //See, without this, the pillows appear to "pop" out of nowhere
		}
		else if (y <= -100)
		{
			y += Pillow.MAX_Y;
		}

		if (x >= Pillow.MAX_X)
		{
			x -= Pillow.MAX_X + 100;
		}
		else if (x <= -100)
		{
			x += Pillow.MAX_X;
		}
	}
}
