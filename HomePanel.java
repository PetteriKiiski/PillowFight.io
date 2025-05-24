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
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class HomePanel extends JPanel
{
	private CardLayout cards;
	private GameHolder mainCanvas;
	
	private GamePanel gamePanel; //Needs to be reset before starting

	private Color backgroundColor;

	//So that colors may be changed
	JRadioButton color1;
	JRadioButton color2;
	JRadioButton color3;
	JRadioButton color4;
	JCheckBox pillowBox;
	JCheckBox lightBox;
	JCheckBox heavyBox;
	JCheckBox healBox;

	public HomePanel(CardLayout cardsIn, GameHolder mainCanvasIn, GamePanel gamePanelIn)
	{
		//Null layout
		setLayout(null);

		//The game panel
		gamePanel = gamePanelIn;
		
		//Set our field variables, taken from parameters
		cards = cardsIn;
		mainCanvas = mainCanvasIn;
		
		//Initialize our Option Listener
		OptionListener opts = new OptionListener();

		//Label for the background color
		JLabel bgLabel = new JLabel("Background");
		bgLabel.setBounds(310, 150, 100, 20);
		add(bgLabel);

		//For the background color
		backgroundColor = new Color(255, 255, 255);
		ButtonGroup colorGroup = new ButtonGroup();
		color1 = new JRadioButton("RED");
		colorGroup.add(color1);
		color1.addActionListener(opts);
		color1.setBounds(310, 180, 100, 20);
		add(color1);
		color2 = new JRadioButton("BLUE");
		colorGroup.add(color2);
		color2.addActionListener(opts);
		color2.setBounds(310, 210, 100, 20);
		add(color2);
		color3 = new JRadioButton("GREEN");
		colorGroup.add(color3);
		color3.addActionListener(opts);
		color3.setBounds(310, 240, 100, 20);
		add(color3);
		color4 = new JRadioButton("WHITE");
		colorGroup.add(color4);
		color4.doClick(); //Click before the actionListener
		color4.addActionListener(opts);
		color4.setBounds(310, 270, 100, 20);
		add(color4);

		//Another JLabel
		JLabel genLabel = new JLabel("Pillow Generation");
		genLabel.setBounds(420, 150, 100, 20);
		add(genLabel);

		//For the pillow generation: click before listener
		pillowBox = new JCheckBox("Normal");
		pillowBox.setBounds(420, 180, 100, 20);
		pillowBox.doClick();
		pillowBox.addActionListener(opts);
		add(pillowBox);
		lightBox = new JCheckBox("Light");
		lightBox.setBounds(420, 210, 100, 20);
		lightBox.doClick();
		lightBox.addActionListener(opts);
		add(lightBox);
		heavyBox = new JCheckBox("Heavy");
		heavyBox.setBounds(420, 240, 100, 20);
		heavyBox.doClick();
		heavyBox.addActionListener(opts);
		add(heavyBox);
		healBox = new JCheckBox("Heal");
		healBox.setBounds(420, 270, 100, 20);
		healBox.doClick();
		healBox.addActionListener(opts);
		add(healBox);

		//Create all our components for this panel: this is really big	
		
		//Title JLabel
		JLabel titleLabel = new JLabel("Pillow Fight.io", JLabel.CENTER);
		titleLabel.setBounds(0, 0, 1000, 180);
		titleLabel.setFont(new Font("Arial", Font.ITALIC, 50));
		
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
		screenBar.setBounds(200, 150, 100, 20);

		//Add all the components
		add(titleLabel);
		add(screenBar);

		//Change the background of everything
		changeBackground(new Color(255, 255, 255));
	}

	public void changeBackground(Color color) //Public function access
	{
		setBackground(color);
		color1.setBackground(color);
		color2.setBackground(color);
		color3.setBackground(color);
		color4.setBackground(color);
		pillowBox.setBackground(color);
		lightBox.setBackground(color);
		heavyBox.setBackground(color);
		healBox.setBackground(color);

	}

	public Color getBackColor()
	{
		return backgroundColor;
	}

	//This just listens to this page in general. Mostly will listen to options
	class OptionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand();
			switch (cmd)
			{
				case "START GAME":
					gamePanel.regenPillows(); //Re-generate the world every time
					gamePanel.setImmune(true); //Make it immune upon entering.
					gamePanel.start(); //Start the game on the first time playing
					cards.show(mainCanvas, "Game");
					break;
				case "Instructions":
					cards.show(mainCanvas, "Instructions");
					break;
				case "Hall of Fame":
					cards.show(mainCanvas, "Fame");
					break;
				case "RED": //Set the color of the background
					backgroundColor = new Color(255, 0, 0);
					mainCanvas.updateColor();
					break;
				case "GREEN":
					backgroundColor = new Color(0, 255, 0);
					mainCanvas.updateColor();
					break;
				case "BLUE":
					backgroundColor = new Color(0, 0, 255);
					mainCanvas.updateColor();
					break;
				case "WHITE":
					backgroundColor = new Color(255, 255, 255);
					mainCanvas.updateColor();
					break;
				case "Normal": //Should we generate this pillow?
					Pillow.generatePillowToggle();
					break;
				case "Light":
					Pillow.generateLightPillowToggle();
					break;
				case "Heavy":
					Pillow.generateHeavyPillowToggle();
					break;
				case "Heal":
					Pillow.generateHealPillowToggle();
					break;
				//More will be added
			}
		}
	}
}
