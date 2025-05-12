public class DumbBot extends Bot
{
	private int directionX;
	private int directionY;
	private int timeCounter;
	
	public DumbBot(PillowArray pA, int miss)
	{
		super(pA, miss); //Just call super constructer
		directionX = (int)(Math.random() * 3) - 1;
		directionY = (int)(Math.random() * 3) - 1;
		timeCounter = 0;
	}
	
	public void decide() throws NotAPillowException
	{
		CycledPillow closest = pillows.getClosestTo((int)x, (int)y, num);
		if (closest.exists() & !pickedUp.exists()) //Search and pick up a pillow
		{
			moveToward(closest.getX() - x, closest.getY() - y);
			pickUp(closest);
		}
		else
		{
			changeX(directionX * AnimateListener.MOVE_SPEED * AnimateListener.DELAY / 1000);
			changeY(directionY * AnimateListener.MOVE_SPEED * AnimateListener.DELAY / 1000);
			timeCounter++;
		}
		
		//Throw decision
		if (pickedUp.exists() && timeCounter >= 3000 / AnimateListener.DELAY)
		{
			throwPillow(x - directionX, y - directionY);
			timeCounter = 0;
		}
	}
}
