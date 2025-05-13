//The player "bot": just so that the bots can see the player
public class PlayerBot extends Bot
{
	public PlayerBot()
	{
		//The constant position
		x = 450;
		y = 350;
		existence = true;
		isPlayer = true;
		immune = true;
		pickedUp = new Pillow();
	}
	
	public void setImmune(boolean value)
	{
		immune = value;
	}
	
	public void setPicked(Pillow picked)
	{
		pickedUp = picked;
	}
}
