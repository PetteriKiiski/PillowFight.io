// GamePanel.java - Actually play the game.

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.io.File; //Images
import javax.swing.JLabel;

public class GamePanel extends JPanel
{
	//Our timer
	Timer timer;
	
	//Array of all the pillows
	PillowArray pillows;
		
	//Array of all the bots
	BotArray bots;
	
	//The player
	PlayerBot player;

	//Immune until click
	public boolean immune;
	
	//Animation Listener
	AnimateListener aL;

	//Math problem Label
	JLabel problemLabel;

	//Buttons!
	JButton homeButton;

	//To change panels
	CardLayout cards;
	JPanel mainPanel;

	//The thing to update the score on
	LosePanel losePanel;
	
	//Score!!! Get this as high as possible
	private int score;

	public void setImmune(boolean immunity)
	{
		immune = immunity;
	}

	public GamePanel(CardLayout cardsIn, JPanel mainPanelIn, LosePanel lossPanel)
	{
		//Things that we need to change panels
		cards = cardsIn;
		mainPanel = mainPanelIn;
		
		//Set the layout
		setLayout(null);
		
		//You are immune until you are not
		immune = true;
		
		//Score!
		score = 0;
		losePanel = lossPanel;

		//The math problem
		problemLabel = new JLabel("", JLabel.CENTER);
		problemLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		problemLabel.setBounds(205, 0, 580, 100);

		//And everything else
		setFocusable(true);
		player = new PlayerBot();
		pillows = new PillowArray(player);
		bots = new BotArray(pillows, (int)(Math.PI / 4), player, this); //miss represents the difficulty. In radians. Player represents the player
		aL = new AnimateListener(this, pillows, bots, cards, mainPanel, losePanel);
		timer = new Timer(AnimateListener.DELAY, aL);
		addKeyListener(new KeyBoardListener(aL)); //Our KeyListener
		
		//Our Home Button
		homeButton = new JButton("Home");
		homeButton.setBounds(0, 0, 200, 100);
		homeButton.addActionListener(new ButtonListener(cards, mainPanel, this));
		
		//Add the button
		add(homeButton);
		
		//Add the math problem
		add(problemLabel);
		
		//Initialize the label
		genLabel(player.getSolution());
		
		//And, finally the mouseListener
		addMouseListener(new ThrowListener());
	}
	
	//Generate the label
	public void genLabel(int solution)
	{
		int type = (int)(Math.random() * 4); //0: addition, 1:subtraction, 2:multiplication, 3:division
		if (solution == 0) //Don't try multiplication: unsolvable
		{
			type = (int)(Math.random() * 3);
			if (type == 2)
			{
				type = 3; //Make it a 3: division
			}
		}
		
		int operation = (int)(Math.random() * 11); //The operation by which the number is modified
		
		switch (type)
		{
			case 0: //Addition
				problemLabel.setText(String.format("x + %d = %d", operation, solution + operation));
				break;
			case 1: //Subtraction
				problemLabel.setText(String.format("x - %d = %d", operation, solution - operation));
				break;
			case 2: //Multiplication
				if (operation == 0) //Don't make it unsolvable
				{
					operation = (int)(Math.random() * 10) + 1;
				}
				problemLabel.setText(String.format("%dx = %d", operation, solution * operation));
				break;
			case 3:	//Division
				if (solution != 0) //For our purposes, 0 has infinitely many factors, but for the algorithm this doesn't work.
				{
					int[] factors = getFactors(solution); //This is to get the possible operations. Painful
					System.out.print("{");
					for (int i = 0; i < factors.length; i++)
					{
						System.out.print(factors[i]);
						if (i != factors.length - 1)
						{
							System.out.print(", ");
						}
					}
					System.out.println("}");
					operation = factors[(int)(Math.random() * factors.length)];
				}
				else
				{
					operation = (int)(Math.random() * 9) + 1;
				}

				problemLabel.setText(String.format("x ÷ %d = %d", operation, solution / operation)); //After this, it's easy though
				break;
		}
	}
	
	public int[] getFactors(int number) //Gets the factors.
	{
		int[] factors = new int[number];
		factors[0] = 1; //1 is ALWAYS a factor
		
		int nextIndex = 1; //The next index to put a factor in
		for (int i = 2; i <= number; i++) //i is the FACTOR, not index.
		{
			if (number % i == 0)
			{
				factors[nextIndex] = i;
				nextIndex++; //Update the index
			}
		}
		
		//To make a minimum array
		int[] justFactors = new int[nextIndex];
		for (int i = 0; i < nextIndex; i++)
		{
			justFactors[i] = factors[i];
		}
		
		return justFactors; //Return the factors
	}
	
	//If you destroy a bot
	public void destroyBot()
	{
		score += 1000; //Just an arbitrary number
	}

	//Get the score
	public int getScore()
	{
		return score;
	}
	
	//Starts the bots, basically
	public void start()
	{
		timer.start();
	}
	
	//Paint component!
	public void paintComponent(Graphics g)
	{
		//Update background
		super.paintComponent(g);
		
		//Paint all the pillows
		pillows.paintPillows(g);
		bots.paintBots(g); //And the bots!
		
		//Paint the player. Who definitely moves... totally
		if (immune)
		{
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("Arial", Font.PLAIN, 100));
			g.drawString("CLICK TO START", 100, 300);
			g.setColor(new Color(0, 255, 0));
		}
		else
		{
			g.setColor(new Color(255, 0, 255));
		}
		g.fillOval(450, 350, 100, 100);

		//Draw the hearts
		//First heart
		setHeartColor(g, 2);
		g.fillRect(790, 0, 70, 100);
		//Second heart
		setHeartColor(g, 4);
		g.fillRect(860, 0, 70, 100);
		//Third heart
		setHeartColor(g, 6);
		g.fillRect(930, 0, 70, 100);

		//Show to score
		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString(String.format("Score: %d", score), 790, 150);

		//Then, over all this, paint the picked up pillow
		pillows.showPickedUp(g);
	}
	
	//Sets the color based on which heart: there's a health ofset, which represents the minimum health for the full heart color
	public void setHeartColor(Graphics g, int ofset)
	{
		if (player.health >= ofset)
		{
			g.setColor(new Color(255, 50, 50));
		}
		else if (player.health >= ofset - 1)
		{
			g.setColor(new Color(0, 255, 0));
		}
		else
		{
			g.setColor(new Color(0, 0, 0));
		}
	}
	
	//Heal!
	public void heal(int amt)
	{
		player.loseHealth(-amt); //I mean, it's the same thing, right.
	}
	
	//Keeps focus in window: and also interact with pillows(throwing)
	class ThrowListener implements MouseListener
	{
		public void mouseExited(MouseEvent evt) {}
		public void mouseEntered(MouseEvent evt) {}
		public void mouseReleased(MouseEvent evt) 
		{

		}
		public void mousePressed(MouseEvent evt)
		{
			//Pick up pillow / Throw / Use
			if (!immune)
			{	
				//Get the button
				int button = evt.getButton();
				if (button == MouseEvent.BUTTON1)
				{
					if (pillows.holdingPillow()) //Only pick up if nothing is picked up, otherwise, throw
					{
						pillows.throwPillow(evt.getX(), evt.getY());
					}
					else
					{
						if (aL.pickUp()) //It was a legal pick-up
						{
							if (pillows.holdingPillow()) //Only if we are actually holding a pillow now
							{
								player.generateSolution(); //Generate the solution
								genLabel(player.getSolution());
								score += 25; //Increase the score for a correct solution
							}
						}
						else //You were wrong on the math
						{
							player.loseHealth(1);
						}
					}
				}
				else if (button == MouseEvent.BUTTON3) //Heal
				{
					if (pillows.holdingPillow())
					{
						heal(pillows.getHeal()); //Gets the heal from the specific pillow
						pillows.drop();
					}
				}
			}
			else //Focus things
			{
				requestFocusInWindow();
				immune = false;
				bots.setImmunity(false);
			}
		}
		public void mouseClicked(MouseEvent evt) {}
	}
}

//To make implementation easier, these are separate classes
class ButtonListener implements ActionListener
{
	CardLayout cards;
	JPanel panel;
	
	//This needs to be reset upon Home Button Clicking
	GamePanel gamePanel;
	
	public ButtonListener(CardLayout cardsIn, JPanel panelIn, GamePanel gamePanelIn)
	{
		cards = cardsIn;
		panel = panelIn;
		gamePanel = gamePanelIn;
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		gamePanel.immune = true;
		cards.show(panel, "Home");
	}
}

//Our updater: moves everything, etc.
class AnimateListener implements ActionListener
{
	//Array of pillows
	PillowArray pillows;
	
	//Array of Bots
	BotArray bots;

	//The Drawing panel
	GamePanel panel;
	
	//The cards
	CardLayout cards;
	
	//The card panel
	JPanel mainPanel;

	//The lose panel
	LosePanel losePanel;
	
	//Move values: will be changed by the KeyListener(KeyBoardListener)
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	//Constants
	public static final int MOVE_SPEED = 500; //The player speed per second
	/*While this is unmodifyable, changes might be able to MODIFY the speed
	 * by adding or subtracting to this value. BUT, this is the base speed,
	 * and can customize the general speed of the player
	*/
	public static final int DELAY = 16; //in ms : this is the timer delay

	//Take in all the info we need. Which is a lot.
	public AnimateListener(GamePanel panelIn, PillowArray pillowsIn, BotArray botsIn, CardLayout cardsIn, JPanel mainPanelIn, LosePanel lossPanel)
	{
		pillows = pillowsIn;
		bots = botsIn;
		panel = panelIn;
		mainPanel = mainPanelIn;
		left = false;
		right = false;
		up = false;
		down = false;
		cards = cardsIn;
		losePanel = lossPanel;
	}
	
	//"while (true) {" loop
	public void actionPerformed(ActionEvent evt)
	{
		/* Calculations for movement AMOUNT: to keep movement speed no matter what DELAY is
		 * MOVE_SPEED px / 1 s  =  ? px / DELAY ms
		 * MOVE_SPEED * DELAY px ms = ? px s
		 * ? = MOVE_SPEED * DELAY ms / s
		 * ? = MOVE_SPEED * DELAY / 1000
		*/
		double movePX = MOVE_SPEED * DELAY / 1000;
		
		//Movement: move everything in the OPPOSITE direction
		if (left)
		{
			pillows.moveX(movePX);
			bots.moveX(movePX);
		}
		if (right)
		{
			pillows.moveX(-movePX);
			bots.moveX(-movePX);
		}
		if (up)
		{
			pillows.moveY(movePX);
			bots.moveY(movePX);
		}
		if (down)
		{
			pillows.moveY(-movePX);
			bots.moveY(-movePX);
		}
		
		pillows.movePillows(); //Move thrown pillows
		
		bots.decide(); //make them move ON THEIR OWN
		if (panel.player.health <= 0)
		{
			panel.immune = true;
			losePanel.setScore(panel.getScore());
			cards.show(mainPanel, "Loss");
		}
		
		//Then, repaint
		panel.repaint();
	} 
	
	//The following will be called by the KeyListener
	public void moveLeft(boolean YoN) //Yes or no, if it was unclear
	{
		left = YoN;
	}
	
	public void moveRight(boolean YoN)
	{
		right = YoN;
	}
	
	public void moveUp(boolean YoN)
	{
		up = YoN;
	}
	
	public void moveDown(boolean YoN)
	{
		down = YoN;
	}

	//Picks up a pillow: only if it's legal
	public boolean pickUp()
	{
		return pillows.pickUp();
	}
}

class KeyBoardListener implements KeyListener
{
	AnimateListener al;
	
	public KeyBoardListener(AnimateListener alIn)
	{
		al = alIn; //Used to update the movement
	}
	
	public void keyReleased(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_LEFT)
		{
			al.moveLeft(false);
		}
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			al.moveRight(false);
		}
		if (evt.getKeyCode() == KeyEvent.VK_UP)
		{
			al.moveUp(false);
		}
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
		{
			al.moveDown(false);
		}
	}
	
	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_LEFT)
		{
			al.moveLeft(true);
		}
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			al.moveRight(true);
		}
		if (evt.getKeyCode() == KeyEvent.VK_UP)
		{
			al.moveUp(true);
		}
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
		{
			al.moveDown(true);
		}
	}

	public void keyTyped(KeyEvent evt) {}
}
