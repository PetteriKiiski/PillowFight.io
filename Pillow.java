import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

//Contains all the pillows
public class Pillow
{
	//Stores the position: while it's a double, it'll be rounded.
	//This will smoother motion when it's really slow for whatever reason
	private double x, y;

	//The velocity when thrown
	private double xvel, yvel;

	//Our time tracker, while throwing
	private int throwTick;

	//Are we throwing it?
	private boolean throwing;

	//The speed of the pillows
	public static final int PILLOW_SPEED = 1500; //PX per second

	//Throw time
	public static final double THROW_TIME = 0.4; //In seconds

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
	
		//Initialize velocity
		xvel = 0;
		yvel = 0;

		//We are not being thrown
		throwing = false;

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
		cycle();
	}
	
	public void moveY(double amt)
	{
		y += amt;
		//Cycle the pillows
		cycle();
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

	//Sets a velocity: takes in distance from center, uses to calculate velocity
	public void throwPillow(int to_x, int to_y)
	{
		/*
		 * Here are my calculations:
		 * Draw a right triangle, and label the legs x, and y.
		 * The "speed" is going to be sqrt(x^2 + y^2)
		 * Now, we make our velocity these x and y values,
		 * multiplied by our desired speed, divided by this
		 * hypotneuse, which would be the speed without this
		 * calculation.
		 */
		if (!throwing) //Just a safety check
		{
			double hypot = Math.sqrt(Math.pow(to_x, 2) + Math.pow(to_y, 2));
			xvel = to_x * PILLOW_SPEED / hypot;
			yvel = to_y * PILLOW_SPEED / hypot;
			throwing = true;
		}
	}

	//Moves, according to our velocity
	public void move()
	{
		if (throwing)
		{
			//Move it
			
			/*
			 * Calculations:
			 * xvel px / s = ? px / DELAY ms
			 * xvel DELAY px ms = ? px s
			 * ? = xvel DELAY ms / s
			 * ? = xvel DELAY / 1000
			 */
			x += (int)xvel * AnimateListener.DELAY / 1000;
			y += (int)yvel * AnimateListener.DELAY / 1000;
			//Keep track of time
			throwTick++;
			
			//Amount of times will be TIME * 1000 / DELAY
			if (throwTick >= THROW_TIME * 1000 / AnimateListener.DELAY)
			{
				throwTick = 0;
				xvel = 0;
				yvel = 0;
				throwing = false;
			}
			//And again, cycle
			cycle();
		}
	}

	//Cycles the position
	public void cycle()
	{
		y %= 2400;
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

	//Certain pillows will have velocity: this moves them accordingly.
	public void movePillows()
	{
		for (int i = 0; i < pillows.length; i++)
		{
			pillows[i].move();
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

	public void throwPillow(int x, int y) //Mouse coordinates indicate direction: however, they are modified to represent distance from center
	{
		pillows[pickedUp].throwPillow(x - 540, y - 440); //Throws the picked up pillow
		pickedUp = -1;
	}

	public boolean holdingPillow()
	{
		return pickedUp != -1; //Returns false if not holding pillow, true if it is.
	}
}
