//BotArray.java : BotArray class
//Note, none of these are directly BOTS, but they are from subclasses
//This is why it's not in Bot.java

public class BotArray
{
	private Bot[] bots;
	public static final Class[] botTypes = {TestBot}; //Array of botTypes
	private PillowArray pillows;

	public BotArray(int numBots, PillowArray pillowsIn)
	{
		pillows = pillowsIn;

		for (int i = 0; i < numbots; i++)
		{
			bots[i] = new botTypes[i % botTypes.length]()
		}
	}
}
