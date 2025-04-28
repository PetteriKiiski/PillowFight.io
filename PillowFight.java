//PillowFight.java: this is the "main" program, the file to be executed.
//It doesn't REALLY do much, just sets up the cards and Frame, but otherwise
//it doesn't it doesn't actually add any components.
//
//Important notice:
//Many classes are currently in separate files, though for organisational purposes
//may be moved to this class, or even another one of the files, depending on where
//it seems fit, just because it may be pointless to have a 30 line program in its
//own file.

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.BorderLayout;

public class PillowFight
{
	public static void main (String[] args)
	{
		PillowFight ph = new PillowFight();
		ph.run();
	}
	
	public void run()
	{
		JFrame frame = new JFrame("Pillow Fight.io");
		frame.setSize(1000, 800);
		frame.setLocation(200, 30);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		GameHolder holder = new GameHolder();
		frame.getContentPane().add(holder);
		frame.setVisible(true);
	}
}

class GameHolder extends JPanel
{
	//CONSTANT: whether or not our PositionListener is added:
	//Do we print the mouse position when you click?
	private final boolean PRINT_POSITION = true;

	//Cards are placed on this.
	private JPanel cardPanel;
	
	public GameHolder()
	{
		
		CardLayout cards = new CardLayout();
		
		//Set the layout
		setLayout(cards);
		
		//Create and add all our panels
		HomePanel hp = new HomePanel(cards, this);
		GamePanel gp = new GamePanel();
		InstructionsPanel ip = new InstructionsPanel();
		FamePanel fp = new FamePanel();
		
		//Load the home button
		
		/* Currently not working:
		homeButtonImg = null;
		try
		{
			homeButtonImg = ImageIO.read(new File("images/homeBtn.png"));
		}
		catch (IOException e)
		{
			System.err.println("There was an error loading the home button's image");
		}
		*/

		//Actually add all these panels
		add("Home", hp);
		add("Game", gp);
		add("Instructions", ip);
		add("Fame", fp);
		
		//For ease of null layout component placement
		if (PRINT_POSITION)
		{
			addMouseListener(new PositionListener());
		}
	}
}
