import java.awt.Graphics;
import java.awt.Color;

//Contains all the pillows
public class Pillow
{
	//Stores the position: while it's a double, it'll be rounded.
	//This will smoother motion when it's really slow for whatever reason
	double x, y;
	
	//Constructor: takes in the range of the generation
	public Pillow (double minx, double maxx, double miny, double maxy)
	{
		//Generate the pillow's position
		x = (maxx - minx) * Math.random() + minx;
		y = (maxy - miny) * Math.random() + miny;
	}
	
	//Paints the pillow
	public void paintPillow(Graphics g)
	{
		//Simply draws a square, which represents the pillow
		g.setColor(new Color(255, 255, 0));
		g.drawRect((int)x, (int)y, 50, 50);
	}
}

//Contains all the pillows that exist
class PillowArray
{
	Pillow[] pillows;
	
	//Create a customizable pillow number
	public PillowArray(int numPillows)
	{
		pillows = new Pillow[numPillows];
	}
	
	public void paintPillows(Graphics g)
	{
		for (int i = 0; i < pillows.length; i++)
		{
			pillows[i].paintPillow(g);
		}
	}
}
