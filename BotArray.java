//BotArray.java : BotArray class
//Note, none of these are directly BOTS, but they are from subclasses
//This is why it's not in Bot.java

public class BotArray
{
	private Bot[] bots;
	private PillowArray pillows;

	public BotArray(int numBots, PillowArray pillowsIn)
	{
		pillows = pillowsIn;

		for (int i = 0; i < numBots; i++)
		{
			bots[i] = new DumbBot(pillows); //I would make it a loop, but it's a bit too complicated for my liking.
		}
	}
}
