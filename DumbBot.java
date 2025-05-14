public class DumbBot extends Bot
{
	private int directionX;
	private int directionY;
	private int timeCounter;
	private boolean close;
	
	public DumbBot(PillowArray pA, BotArray bA, int miss)
	{
		super(pA, bA, miss); //Just call super constructer
		directionX = (int)(Math.random() * 3) - 1;
		directionY = (int)(Math.random() * 3) - 1;
		timeCounter = 0;
		close = false;
	}
	/*
	public void decide() throws NotAPillowException, NotABotException
	{
		CycledPillow closestPillow = pillows.getClosestTo((int)x, (int)y, num);
		CycledBot closestBot = bots.getClosestTo((int)x, (int)y, num, this);
		
		try
		{
			if (closestBot.getBot().isPlayer)
			{
				close = true;
			}
			else if (close)
			{
				close = false;
			}
		}
		catch (NotABotException err)
		{
			System.out.println("ERROR: Not a Bot");
		}	
		if (pickedUp.exists())
		{	
				throwPillow(closestBot.getX(), closestBot.getY());	
		}
	
		//Move
		
		if ((closestBot.getDist(x, y) <= 400 && closestBot.getBot().pickedUp.exists()) || closestBot.getDist(x, y) <= 300) //Move away if they are holding a pillow
		{
			moveToward(x - closestBot.getX(), y - closestBot.getY()); //Actually, move away
		}
		else if (pickedUp.exists())
		{
			moveToward(closestBot.getX() - x, closestBot.getY() - y);
		}	
		else if (closestPillow.exists())//Search for pillow
		{
			moveToward(closestPillow.getX() - x, closestPillow.getY() - y);
			pickUp(closestPillow);
		}
	}
	*/
	public void decide() {}
}
