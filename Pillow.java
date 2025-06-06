//Pillow class which has the data for the pillow and the PillowArray
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

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
	
	//Is the player throwing it(for score-keeping)?
	private boolean playerThrown;

	//The speed of the pillows
	public static final int PILLOW_SPEED = 1000; //PX per second

	//Throw time
	public static final double THROW_TIME = 0.4; //In seconds

	//Generation Range Constants: no need for min, as it's cyclical
	public static final int MAX_X = 9000;
	public static final int MAX_Y = 7200;
		
	//The number on the pillow: will be the solution
	private int num;
	
	//The index in the PillowArray
	private int index;
	
	//Can be used to represent the lack of a real pillow
	private boolean existence;

	//Is the pillow picked up?
	private boolean picked;

	//The properties
	private double speedFactor; //% of speed
	private int damage; //self-explanatory
	private int heal; //how much it heals
	private Image image; //The image indicator
	private Color numColor; //The color of the number

	//The game panel to draw the image on
	private GamePanel gamePanel;

	//The different kinds of pillows: these are public static
	public static boolean[] pillowGen = new boolean[]{true, true, true, true}; //Default
	public static int numTypes = 4; //Keep track of this

	//Constructor: takes in the number
	public Pillow (int numIn, int indexIn, GamePanel gamePanelIn)
	{
		//Set its picked state
		picked = false;

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
		
		//Index
		index = indexIn;

		//Set the pillows properties
		generateProperties();
		
		//GamePanel
		gamePanel = gamePanelIn;
		
		//This pillow exists
		existence = true;
		
		playerThrown = false;
	}

	//These are static: they impact all instances
	public static void generatePillowToggle()
	{
		pillowGen[1] = !pillowGen[1];
		if (pillowGen[1])
		{
			numTypes++;
		}
		else
		{
			numTypes--;
		}
	}

	public static void generateLightPillowToggle()
	{
		pillowGen[0] = !pillowGen[0];
		if (pillowGen[0])
		{
			numTypes++;
		}
		else
		{
			numTypes--;
		}
	}

	public static void generateHeavyPillowToggle()
	{
		pillowGen[2] = !pillowGen[2];
		if (pillowGen[2])
		{
			numTypes++;
		}
		else
		{
			numTypes--;
		}
	}

	public static void generateHealPillowToggle()
	{
		pillowGen[3] = !pillowGen[3];
		if (pillowGen[3])
		{
			numTypes++;
		}
		else
		{
			numTypes--;
		}	
	}

	//These are all the possibilities for generation
	public void generateProperties()
	{
		//Limit the generation types
		int[] types = new int[numTypes];
		int index = 0; //The next index to add to types array
		for (int i = 0; i < pillowGen.length; i++)
		{
			if (pillowGen[i]) //If this type exists
			{
				types[index] = i; //Add to the array this type
				index++; //Next time we edit, go to the next one
			}
		}
		//Override the no types possibility
		int type;
		if (numTypes != 0)
		{
			type = types[(int)(Math.random() * numTypes)]; //Get the type out of the possible types
		}
		else
		{
			type = 1; //The normal pillow
		}
		numColor = new Color(0, 0, 0); //In most cases it's just black
		switch (type)
		{
			case 0: //Light
				heal = 0;
				speedFactor = 2; //Really fast
				damage = 1; //But kinda weak. However, it's hard to miss.
				image = LoadedImages.lightPillow; //Very bright yellow
				break;
			case 1: //Normal
				heal = 0;
				speedFactor = 1; //Normal
				damage = 2;
				image = LoadedImages.pillow;
				break;
			case 2: //Heavy
				heal = 0;
				speedFactor = 0.7; //Slow
				damage = 4; //But REALLY harmful. However, this usually won't happen unless you're (its) really stuck
				image = LoadedImages.heavyPillow;
				numColor = new Color(200, 200, 200); //In this case it's light grey
				break;
			case 3: //Heal
				heal = 1; //Good for heal (not amazing)
				speedFactor = 0.5; //bad for everything else
				damage = 1;
				image = LoadedImages.healPillow; //Pink!
				break;
			default:
				System.out.println("ERROR: could not give a pillow type");
		}
	}
	
	public Pillow()
	{
		//Indicates this is the lack of a pillow
		existence = false;	
	}
	
	//The solution to the math problem
	public int getSolution()
	{
		return num;
	}

	//Do you really need me to explain this?
	public int getIndex()
	{
		return index;
	}

	//Has it been picked up by something?
	public boolean isPicked()
	{
		if (existence)
		{
			return picked;
		}
		else
		{
			return false;
		}
	}

	public boolean exists()
	{
		return existence;
	}
	
	//Paints the pillow
	public void paintPillow(Graphics g)
	{
		if (existence)
		{
			//Simply draws a square, which represents the pillow
			g.drawImage(image, (int)x, (int)y, gamePanel);
			
			//Draw the number on the pillow
			g.setColor(numColor);
			g.setFont(new Font("Arial", Font.PLAIN, 35));
			g.drawString(Integer.toString(num), (int)x+15, (int)y+38);
		}
		else
		{
			System.err.println("ERROR: This is not a pillow");
		}
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

	//Returns the distance from the location, or the center of the screen
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

	public boolean setPicked() //For the player.
	{
		return setPicked(500, 400, new CycledPillow(this, 0, 0)); //Same thing, just the default function. No cycling for default
	}

	//Returns if the pillow was successfully picked up
	public boolean setPicked(int atX, int atY, CycledPillow pillow) //For bots. This method is called by the CycledPillow class
	{
		try
		{
			if (existence && !picked && pillow.getDist(atX, atY) <= 200 & !throwing) //make sure it's reasonably close
			{
				picked = true;
				moveToPicked(atX, atY);
				return true;
			}
			else if (!existence)
			{
				System.err.println("ERROR: This is not a pillow");
				return false;
			}
			else if (getDist(pillow.getX(), pillow.getY()) > 200 || throwing) //Not successfully picked in this case
			{
				return false;
			}
		}
		catch (NotAPillowException err)
		{
			System.err.println("ERROR: This is not a pillow");
			return false;
		}
		return false; //At this point, nothing was returned
	}

	//Moves it to the spot where it is picked
	public void moveToPicked(double atX, double atY)
	{
		x = atX + 15;
		y = atY + 15;
	}

	//Also just changes location
	//Probably won't be used anymore, but I will keep this here, incase something needs or wants it
	public void drop()
	{
		if (existence)
		{
			picked = false;
			x = 530;
			y = 480;
		}
		else
		{
			System.err.println("ERROR: This is not a pillow");
		}
	}

	//Sets a velocity: takes in distance from center, uses to calculate velocity
	public void throwPillow(double to_x, double to_y)
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
			picked = false;
			double hypot = Math.sqrt(Math.pow(to_x, 2) + Math.pow(to_y, 2));
			xvel = to_x * PILLOW_SPEED * speedFactor / hypot;
			yvel = to_y * PILLOW_SPEED * speedFactor / hypot;
			throwing = true;
			
			//Move it, so that the pillow doesn't damage the thrower
			x += xvel * 150 / (PILLOW_SPEED * speedFactor); //Move by 150 instead of PILLOW_SPEED * speedFactor
			y += yvel * 150 / (PILLOW_SPEED * speedFactor);
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
				playerThrown = false;
			}
			//And again, cycle
			cycle();
		}
		else if(!existence)
		{
			System.err.println("ERROR: This is not a pillow");
		}
	}

	//Returns whether it is currently doing damage to whatever hits it.
	public boolean doesDamage()
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

	//Stop the pillow if it hit
	public int hit()
	{
		throwing = false;
		playerThrown = false;
		return damage;
	}
	
	//Healing!
	public int getHeal()
	{
		return heal;
	}

	//Returns if it's player thrown
	public boolean isPlayerThrown()
	{
		return playerThrown;
	}
	
	//Make it playerThrown
	public void makePlayerThrown()
	{
		playerThrown = true;
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

	//If the bot needs to interact with the pillow itself for some reason.
	public Pillow getPillow()
	{
		if (existence)
		{
			return pillow;
		}
		else
		{
			System.out.println("ERROR: Not a pillow");
		}
		return new Pillow();
	}

	//Just calls the pillows method and returns it's output
	public boolean setPicked(int atX, int atY)
	{
		if (existence)
		{
			return pillow.setPicked(atX, atY, this);
		}
		else
		{
			return false;
		}
	}

	//Returns the distance from the location
	//This is basically copy-pasted from the Pillow class
	public double getDist(double locx, double locy) throws NotAPillowException
	{
		if (existence)
		{
			return Math.sqrt(Math.pow(locx - getX() + 25, 2) + Math.pow(locy - getY() + 25, 2));
		}
		else
		{
			throw new NotAPillowException();
		}
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
