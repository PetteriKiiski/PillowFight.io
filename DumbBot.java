public class DumbBot extends Bot
{
	public DumbBot(PillowArray pA)
	{
		super(pA); //Just call super constructer
	}
	
	public void decide()
	{
		CycledPillow closest = pillows.getClosestTo((int)x, (int)y, num);
		moveToward(closest.getX() - x, closest.getY() - y);
	}
}
