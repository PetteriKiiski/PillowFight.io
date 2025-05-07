//Pillow class which has the data for the pillow and the PillowArray
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
	public static final int PILLOW_SPEED = 1000; //PX per second

	//Throw time
	public static final double THROW_TIME = 0.4; //In seconds

	//Generation Range Constants: no need for min, as it's cyclical
	public static final int MAX_X = 3000;
	public static final int MAX_Y = 2400;
		
	//The number on the pillow: will be the solution
	int num;
	
	//Can be used to represent the lack of a real pillow
	private boolean existence;

	//Constructor: takes in the number
	public Pillow (int numIn)
	{
		//Generate the pillow's position
		x = MAX_X * Math.random();
		y = MAX_Y * Math.random();
	
		//Initialize velocity
		xvel = 0;
		yvel = 0;

		//We are not being thrown
		throwing = false;

		//The number on it
		num = numIn;

		existence = true;
	}
	
	public Pillow()
	{
		existence = false;	
	}

	public boolean exists()
	{
		return existence;
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
		if (existence)
		{
			x += amt;
			//Cycle the pillows
			cycle();
		}
		else
		{
			System.err.println("ERROR: This is not a pillow");
		}
	}
	
	public void moveY(double amt)
	{
		if (existence)
		{
			y += amt;
			//Cycle the pillows
			cycle();
		}
		else
		{
			System.err.println("ERROR: This is not a pillow");
		}
	}

	//Returns the distance from the "player", or the center of the screen
	//Also, we use the center of the square
	public double getDist(double locx, double locy) throws NotAPillowException
	{
		if (existence)
		{
			return Math.sqrt(Math.pow(locx - (x + 25), 2) + Math.pow(locy - (y + 25), 2));
		}
		else
		{
			throw new NotAPillowException();
		}
	}

	//Doesn't actually set a boolean: changes location
	public void setPicked() //For the player.
	{
		if (existence)
		{
			x = 515;
			y = 415;
		}
		else
		{
			System.err.println("ERROR: This is not a pillow");
		}
	}

	public void setPicked(int atX, int atY) //For bots
	{
		if (existence)
		{
			x = atX + 15;
			y = atY + 15;
		}
		else
		{
			System.err.println("ERROR: This is not a pillow");
		}
	}

	//Also just changes location
	public void drop()
	{
		if (existence)
		{
			x = 530;
			y = 480;
		}
		else
		{
			System.err.println("ERROR: This is not a pillow");
		}
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
		if (!throwing & existence) //Just a safety check
		{
			double hypot = Math.sqrt(Math.pow(to_x, 2) + Math.pow(to_y, 2));
			xvel = to_x * PILLOW_SPEED / hypot;
			yvel = to_y * PILLOW_SPEED / hypot;
			throwing = true;
		}
		else if(!existence)
		{
			System.err.println("ERROR: This is not a pillow");
		}
	}

	//Moves, according to our velocity
	public void move()
	{
		if (throwing & existence)
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
		else if(!existence)
		{
			System.err.println("ERROR: This is not a pillow");
		}
	}

	//Cycles the position
	public void cycle()
	{
		//really simple
		if (existence)
		{
			if (y >= MAX_Y)
			{
				y -= MAX_Y + 50; //See, without this, the pillows appear to "pop" out of nowhere
			}
			else if (y <= -50)
			{
				y += MAX_Y;
			}
	
			if (x >= MAX_X)
			{
				x -= MAX_X + 50;
			}
			else if (x <= -50)
			{
				x += MAX_X;
			}
		}
		else
		{
			System.err.println("ERROR: This is not a pillow");
		}
	}

	public boolean beingThrown()
	{
		if (existence)
		{
			return throwing;
		}
		else
		{
			return false;
		}
	}
	
	public int getX() throws NotAPillowException
	{
		if (existence)
		{
			return (int)x;
		}
		else
		{
			throw new NotAPillowException();
		}

	}
	
	public int getY() throws NotAPillowException
	{
		if (existence)
		{
			return (int)y;
		}
		else
		{
			throw new NotAPillowException();
		}
	}
	
	public boolean numberIs(int i)
	{
		if (existence)
		{
			return num == i;
		}
		else
		{
			return false;
		}
	}
}

//This contains the coordinates of a kind of cycled pillow. Used by bots for directional, cycled pillow motion
class CycledPillow
{
	Pillow pillow;
	private int modx;
	private int mody;
	private boolean existence;

	public CycledPillow(Pillow pillowIn, int modxIn, int modyIn)
	{
		pillow = pillowIn;
		modx = modxIn;
		mody = modyIn;
		existence = true;
	}
	
	//
	public CycledPillow()
	{
		existence = false;
	}

	public int getX() throws NotAPillowException
	{
		if (existence)
		{
			return pillow.getX() + (modx * Pillow.MAX_X); //Cycle the pillow appropriately
		}
		else
		{
			throw new NotAPillowException();
		}
	}
	
	public int getY() throws NotAPillowException
	{
		if (existence)
		{
			return pillow.getY() + (mody * Pillow.MAX_Y); //Cycle it
		}
		else
		{
			throw new NotAPillowException();
		}
	}

	public boolean exists()
	{
		return existence;
	}
}

//Exception is thrown when the pillow does not exist
class NotAPillowException extends Exception
{
	NotAPillowException()
	{
		super("CycledPillow object represents no available pillow");
	}

	NotAPillowException(String message)
	{
		super(message);
	}
}
