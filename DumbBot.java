public class DumbBot extends Bot
{
	private int timeCounter;
	
	public DumbBot(PillowArray pA, BotArray bA, double miss)
	{
		super(pA, bA, miss); //Just call super constructer
		timeCounter = 0;
	}
	
	public void decide() throws NotAPillowException, NotABotException
	{
		CycledPillow closestPillow = pillows.getClosestTo((int)x, (int)y, num);
		CycledBot closestBot = bots.getClosestTo((int)x, (int)y, num, this);
		timeCounter++; //Keep track of throwing time
		
		if (pickedUp.exists() && timeCounter > 1000 / AnimateListener.DELAY) //Throw in a reasonable interval
		{
			throwPillow(closestBot.getX(), closestBot.getY());	
			timeCounter = 0;
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
			//Only try if it's the closest
			if (closestPillow.getPillow() == pillows.getClosestTo((int)x, (int)y, -1).getPillow())
			{
				pickUp(closestPillow);
			}
		}
	}
}
