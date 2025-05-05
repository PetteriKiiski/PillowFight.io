//BotArray.java : BotArray class
//Note, none of these are directly BOTS, but they are from subclasses
//This is why it's not in Bot.java
import java.awt.Graphics;

public class BotArray
{
	private Bot[] bots;
	private PillowArray pillows;

	public BotArray(int numBots, PillowArray pillowsIn)
	{
		pillows = pillowsIn;
		bots = new Bot[numBots];

		for (int i = 0; i < numBots; i++)
		{
			bots[i] = new DumbBot(pillows); //I would make it a loop, but it's a bit too complicated for my liking.
		}
	}
	
	public void paintBots(Graphics g)
	{
		for (int i = 0; i < bots.length; i++)
		{
			bots[i].paintBot(g);
		}
	}
	
	public void moveX(double amt)
	{
		for (int i = 0; i < bots.length; i++)
		{
			bots[i].moveX(amt);
		}
	}
	
	public void moveY(double amt)
	{
		for (int i = 0; i < bots.length; i++)
		{
			bots[i].moveY(amt);
		}
	}
	
	public void decide()
	{
		for (int i = 0; i < bots.length; i++)
		{
			bots[i].decide(); //They will move... or not, their choice.
		}
	}
}
