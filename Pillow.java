import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

//Contains all the pillows
public class Pillow
{
	//Stores the position: while it's a double, it'll be rounded.
	//This will smoother motion when it's really slow for whatever reason
	private double x, y;
	
	//Generation Range Constants
	public static final int MIN_X = -1000;
	public static final int MIN_Y = -800;
	public static final int MAX_X = 2000;
	public static final int MAX_Y = 1600;
		
	//The number on the pillow: will be the solution
	int num;
	
	//Constructor: takes in the number
	public Pillow (int numIn)
	{
		//Generate the pillow's position
		x = (MAX_X - MIN_X) * Math.random() + MIN_X;
		y = (MAX_Y - MIN_Y) * Math.random() + MIN_Y;
	
		//The number on it
		num = numIn;
	}
	
	//Paints the pillow
	public void paintPillow(Graphics g)
	{
		//Simply draws a square, which represents the pillow
		g.setColor(new Color(255, 255, 0));
		g.fillRect((int)x, (int)y, 50, 50);
		
		//Draw the number on the pillow
		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font("Arial", Font.PLAIN, 25));
		g.drawString(Integer.toString(num), (int)x+15, (int)y+38);
	}
	
	//Move the pillow: both of these
	public void moveX(double amt)
	{
		x += amt;
		//Cycle the pillows
		if (x >= 2000)
		{
			x -= 3000;
		}
		if (x <= -1000)
		{
			x += 3000;
		}
	}
	
	public void moveY(double amt)
	{
		y += amt;
		//Cycle the pillows
		if (y >= 1600)
		{
			y -= 2400;
		}
		if (y <= -800)
		{
			y += 2400;
		}
	}

	//Returns the distance from the "player", or the center of the screen
	//Also, we use the center of the square
	public double getDist()
	{
		return Math.sqrt(Math.pow(500 - (x + 25), 2) + Math.pow(400 - (y + 25), 2));
	}

	//Doesn't actually set a boolean: changes location
	public void setPicked()
	{
		x = 515;
		y = 415;
	}

	//Also just changes location
	public void drop()
	{
		x = 530;
		y = 480;
	}
}

//Contains all the pillows that exist
class PillowArray
{
	private Pillow[] pillows;
	
	int pickedUp = -1;

	//Create a customizable pillow number
	public PillowArray(int numPillows)
	{
		pillows = new Pillow[numPillows];
		for (int i = 0; i < numPillows; i++)
		{
			//i % 10 ensures all digits exist in the "world"
			pillows[i] = new Pillow(i % 10);
		}
	}
	
	public void paintPillows(Graphics g)
	{
		for (int i = 0; i < pillows.length; i++)
		{
			pillows[i].paintPillow(g);
		}
	}
	
	//Move ALL the pillows
	public void moveX(double amt)
	{
		for (int i = 0; i < pillows.length; i++)
		{
			if (i != pickedUp)
			{
				pillows[i].moveX(amt);
			}
		}
	}
	
	public void moveY(double amt)
	{
		for (int i = 0; i < pillows.length; i++)
		{
			if (i != pickedUp)
			{
				pillows[i].moveY(amt);
			}
		}
	}

	//Pick up the closest, reasonably close pillow
	public void pickUp()
	{
		double closest = 200; //The maximum legal distance for picking up
		int index = -1;

		double currentDist;
		for (int i = 0; i < pillows.length; i++)
		{
			currentDist = pillows[i].getDist();
			//Make sure it's an appropriate distance(ensured by the closest's initial value)
			//AND it the closest pillow
			//AND it isn't just the same pillow we are already holding
			if (currentDist <= closest && i != pickedUp)
			{
				index = i;
				closest = currentDist;
			}
		}

		//If there is a close pillow
		if (index != -1)
		{
			//Drop the current pillow, if there is one
			if (pickedUp != -1)
			{
				pillows[pickedUp].drop();
			}

			//Then pick it up
			pickedUp = index;
			pillows[pickedUp].setPicked();
		}
	}

	//Show the picked up pillow, presumably above everything else
	public void showPickedUp(Graphics g)
	{
		//Make sure SOMETHING is picked up
		if (pickedUp != -1)
		{
			pillows[pickedUp].paintPillow(g);
		}
	}
}
