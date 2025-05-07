public class DumbBot extends Bot
{
	public DumbBot(PillowArray pA)
	{
		super(pA); //Just call super constructer
	}
	
	public void decide() throws NotAPillowException
	{
		CycledPillow closest = pillows.getClosestTo((int)x, (int)y, num);
		if (closest.exists())
		{
			moveToward(closest.getX() - x, closest.getY() - y);
		}
	}
}
