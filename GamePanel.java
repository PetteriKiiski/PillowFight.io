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

public class GamePanel extends JPanel
{
	//Our timer
	Timer timer;
	
	//Array of all the pillows
	PillowArray pillows;
		
	//Array of all the bots
	BotArray bots;

	//Immune until click
	public boolean immune;
	
	//Animation Listener
	AnimateListener aL;

	//Buttons!
	JButton homeButton;

	//To change panels
	CardLayout cards;
	JPanel mainPanel;

	public void setImmune(boolean immunity)
	{
		immune = immunity;
	}

	public GamePanel(CardLayout cardsIn, JPanel mainPanelIn)
	{
		//Things that we need to change panels
		cards = cardsIn;
		mainPanel = mainPanelIn;
		
		//Set the layout
		setLayout(null);
		
		//You are immune until you are not
		immune = true;
		
		setFocusable(true);
		pillows = new PillowArray(90);
		bots = new BotArray(1, pillows);
		aL = new AnimateListener(this, pillows, bots);
		timer = new Timer(AnimateListener.DELAY, aL);
		addKeyListener(new KeyBoardListener(aL)); //Our KeyListener
		timer.start();
		
		//Our Home Button
		homeButton = new JButton("Home");
		homeButton.setBounds(0, 0, 200, 100);
		homeButton.addActionListener(new ButtonListener(cards, mainPanel, this));
		
		//Add the button
		add(homeButton);
		
		//And, finally the mouseListener
		addMouseListener(new ThrowListener());
	}
	
	//Paint component!
	public void paintComponent(Graphics g)
	{
		//Update background
		super.paintComponent(g);
		
		//Paint all the pillows
		pillows.paintPillows(g);
		bots.paintBots(g);
		
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

		//Then, over all this, paint the picked up pillow
		pillows.showPickedUp(g);
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
					else {
						aL.pickUp();
					}
				}
			}
			else //Focus things
			{
				requestFocusInWindow();
				immune = false;
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
	public AnimateListener(GamePanel panelIn, PillowArray pillowsIn, BotArray botsIn)
	{
		pillows = pillowsIn;
		bots = botsIn;
		panel = panelIn;
		left = false;
		right = false;
		up = false;
		down = false;
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
		}
		if (right)
		{
			pillows.moveX(-movePX);
		}
		if (up)
		{
			pillows.moveY(movePX);
		}
		if (down)
		{
			pillows.moveY(-movePX);
		}
		
		pillows.movePillows(); //Move thrown pillows
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

	//Picks up a pillow
	public void pickUp()
	{
		pillows.pickUp();
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
