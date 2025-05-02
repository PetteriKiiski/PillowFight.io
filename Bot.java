//This is the super-class for ALL BOTS
//This is abstract, and must be inherited
public abstract class Bot
{
	//Coordinates:
	private double x, y;

	//PillowArray: stores all the pillows
	PillowArray pillows;

	public static final BOT_SPEED = 1000;

	public Bot(PillowArray pA) {
		pillows = pA;
	}
	
	//Decide what you are going to do
	abstract void decide();
	
	//Moves: the decide method calls these alongside vision
	public void moveLeft(double amt)
	{
		
	}

	public void moveRight(double amt)
	{
	}

	public void moveUp(double amt)
	{
	}

	public void moveDown(double amt)
	{
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
		g.fillOval(x, y, 100, 100);
	}

	//Collide with bots and the player
	public void collide() {}
}
