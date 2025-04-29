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
	
	//Move the pillow
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
}

//Contains all the pillows that exist
class PillowArray
{
	private Pillow[] pillows;
	
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
			pillows[i].moveX(amt);
		}
	}
	
	public void moveY(double amt)
	{
		for (int i = 0; i < pillows.length; i++)
		{
			pillows[i].moveY(amt);
		}
	}
}
