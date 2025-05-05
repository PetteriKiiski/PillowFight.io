public class DumbBot extends Bot
{
	public int direction;
	public DumbBot(PillowArray pA)
	{
		super(pA); //Just call super constructer
		direction = 1;
	}
	
	//Make the "min" and "max" change
	@Override
	public void moveX(double amt)
	{
		super.moveX(amt);
	}
	
	public void decide()
	{
	}
}
