import java.awt.Graphics;
//This is the super-class for ALL BOTS
//This is abstract, and must be inherited
abstract class Bot
{
	//Coordinates:
	private double x, y;

	//PillowArray: stores all the pillows
	PillowArray pillows;

	public Bot(PillowArray pA) {
		pillows = pA;
	}
	
	//Decide what you are going to do
	abstract void decide(); //The child class must decide
	
	//Moves: the decide method calls these alongside vision
	public void moveX(double amt)
	{
		x += amt;
	}

	public void moveY(double amt)
	{
		y += amt;
	}

	public void pickUp(Pillow pillow)
	{
		
	}

	//This method is going to be really complicated, due to the cycling
	public void see()
	{
	}

	//The simplest method
	public void paintBot(Graphics g)
	{
		g.fillOval((int)x, (int)y, 100, 100);
	}

	//Collide with bots and the player
	public void collide() {}
}
