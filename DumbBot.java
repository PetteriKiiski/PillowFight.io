public class DumbBot extends Bot
{	
	public DumbBot(PillowArray arr)
	{
		super(arr);
	}

	public void decide()
	{
		moveX(AnimateListener.DELAY * AnimateListener.MOVE_SPEED / 1000);
	}
}
