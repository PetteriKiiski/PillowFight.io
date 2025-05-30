//Main game file

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.Color;

public class PillowFight
{
	public static void main (String[] args)
	{
		PillowFight ph = new PillowFight();
		ph.run();
	}
	
	public void run()
	{
		LoadedImages.loadImages(); //Load all the images
		JFrame frame = new JFrame("Pillow Fight.io");
		frame.setSize(1000, 800);
		frame.setLocation(200, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		GameHolder holder = new GameHolder();
		frame.getContentPane().add(holder);
		frame.setVisible(true);
	}
}

//Holds the game
class GameHolder extends JPanel
{
	private final boolean PRINT_POSITION = false; //Unless I work on this later, this will now be turned off

	//These panels are for updating background color
	private LearnPanel learnPanel;
	private HallEntryPanel hep;
	private GamePanel gp;
	private HomePanel hp;
	private InstructionsPanel ip;
	private LosePanel lp;
	private FamePanel fp;

	//Cards are placed on this.
	private JPanel cardPanel;
	
	//For the game reset
	private CardLayout cards;

	public GameHolder()
	{
		cards = new CardLayout();
		
		//Set the layout
		setLayout(cards);
		
		//Create and add all our panels
		learnPanel = new LearnPanel(cards, this);
		lp = new LosePanel(this, cards);
		fp = new FamePanel(this, cards);
		hep = new HallEntryPanel(fp, this, cards);
		gp = new GamePanel(cards, this, lp, learnPanel, fp, hep);
		hp = new HomePanel(cards, this, gp);
		ip = new InstructionsPanel(cards, this);		

		//Actually add all these panels
		add("Home", hp);
		add("Game", gp);
		add("Instructions", ip);
		add("Fame", fp);
		add("Loss", lp);
		add("Learn", learnPanel); //Aww man, repeating lp is not aloud
		add("Hall Entry", hep);

		//For ease of null layout component placement
		if (PRINT_POSITION)
		{
			addMouseListener(new PositionListener());
		}
	}

	public void updateColor()
	{
		Color updatedColor = hp.getBackColor();
		learnPanel.changeBackground(updatedColor);
		hep.changeBackground(updatedColor);
		gp.changeBackground(updatedColor);
		hp.changeBackground(updatedColor);
		ip.changeBackground(updatedColor);
	}
}
