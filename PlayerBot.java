//The player "bot": just so that the bots can see the player
public class PlayerBot extends Bot
{
	public PlayerBot()
	{
		//The constant position
		x = 500;
		y = 400;
		
		//The random number
		num = (int)(Math.random() * 10); //The range is 0-9, not 0-10
		
		//other
		existence = true;
		isPlayer = true;
		immune = true;
		pickedUp = new Pillow();
		health = 6; //Six half hearts.
	}
	
	public void setImmune(boolean value)
	{
		immune = value;
	}
	
	public void setPicked(Pillow picked)
	{
		pickedUp = picked;
	}
	
	public int getSolution()
	{
		return num;
	}
	
	public void generateSolution()
	{
		num = (int)(Math.random() * 10);
	}
}
