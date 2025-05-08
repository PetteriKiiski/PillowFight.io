public class DumbBot extends Bot
{
	public DumbBot(PillowArray pA)
	{
		super(pA); //Just call super constructer
	}
	
	public void decide() throws NotAPillowException
	{
		CycledPillow closest = pillows.getClosestTo((int)x, (int)y, num);
		if (closest.exists() & !pickedUp.exists()) //Search and pick up a pillow
		{
			moveToward(closest.getX() - x, closest.getY() - y);
			pickUp(closest.getPillow());
		}
		else if (!closest.exists())
		{
			System.out.println("Nothing close enough :(");
		}
	}
}

