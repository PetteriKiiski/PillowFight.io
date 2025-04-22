import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class HomePanel extends JPanel
{
	CardLayout cards;
	GameHolder mainCanvas;
	
	public HomePanel(CardLayout cardsIn, GameHolder mainCanvasIn)
	{
		//Set our field variables, taken from parameters
		cards = cardsIn;
		mainCanvas = mainCanvasIn;
		
		//Initialize our Option Listener
		OptionListener opts = new OptionListener();
		
		//Set Background, then do other stuff
		setBackground(Color.GREEN);
		
		//We will use null layout for this one
		setLayout(null);
		
			//Create all our components for this panel: this is really big	
		//Title JLabel
		JLabel titleLabel = new JLabel("Pillow Fight.io", JLabel.CENTER);
		titleLabel.setBounds(0, 0, 1000, 180);
		titleLabel.setFont(new Font("Arial", Font.ITALIC, 50));
		titleLabel.setBackground(Color.BLUE);
		
		//Screen Selection: Uses a JMenu
		JMenuBar screenBar = new JMenuBar();
		JMenu screenMenu = new JMenu("Switch Tab");
		JMenuItem fameItem = new JMenuItem("Hall of Fame");
		JMenuItem instItem = new JMenuItem("Instructions"); //Shortened, "Instructions" item
		JMenuItem startItem = new JMenuItem("START GAME");
		fameItem.addActionListener(opts);
		instItem.addActionListener(opts);
		startItem.addActionListener(opts);
		screenMenu.add(fameItem);
		screenMenu.add(instItem);
		screenMenu.add(startItem);
		screenBar.add(screenMenu);
		
		//Then, finally set the boundaries of our JMenu
		screenBar.setBounds(200, 150, 300, 20);
		
		//Add all the components
		add(titleLabel);
		add(screenBar);
	}
	
	//This just listens to this page in general. Mostly will listen to options
	class OptionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand();
			if (cmd == "START GAME")
			{
				cards.show(mainCanvas, "Game");
			}
		}
	}
}
