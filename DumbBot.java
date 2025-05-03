public class DumbBot extends Bot
{
	public DumbBot(PillowArray pA)
	{
		super(pA); //Just call super constructer
	}
	public void decide()
	{
		moveX(AnimateListener.MOVE_SPEED * AnimateListener.DELAY / 1000);
	}
}
