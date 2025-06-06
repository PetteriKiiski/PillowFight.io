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
import javax.swing.JSlider;
import java.awt.Graphics;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;

public class HomePanel extends JPanel
{
	private CardLayout cards;
	private GameHolder mainCanvas;
	
	private GamePanel gamePanel; //Needs to be reset before starting

	private Color backgroundColor;

	//So that colors may be changed
	private JRadioButton color1;
	private JRadioButton color2;
	private JRadioButton color3;
	private JRadioButton color4;
	private JCheckBox pillowBox;
	private JCheckBox lightBox;
	private JCheckBox heavyBox;
	private JCheckBox healBox;
	private JSlider accuracySlider;
	private JTextField nameField;

	//Have you entered your name?
	private boolean showNameMsg;
	private boolean nameSelected;

	//Elligibility: are you aloud to run for hall of fame?
	private boolean eligibility;

	public HomePanel(CardLayout cardsIn, GameHolder mainCanvasIn, GamePanel gamePanelIn)
	{
		//Null layout
		setLayout(null);

		//Elligibility: yes, you start off with elligibility
		eligibility = true;

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
		JLabel genLabel = new JLabel("Pillow Gen."); //If you can't tell, this stands for pillow generation
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

		//Title for our slider
		JLabel sliderTitle = new JLabel("Bot Accuracy");
		sliderTitle.setBounds(530, 150, 100, 20);
		add(sliderTitle);

		//And the slider itself
		accuracySlider = new JSlider(0, 100, 50);
		accuracySlider.setBounds(530, 180, 200, 50);
		accuracySlider.setMajorTickSpacing(20);
		accuracySlider.setPaintTicks(true);
		accuracySlider.setLabelTable(accuracySlider.createStandardLabels(20));
		accuracySlider.setPaintLabels(true);
		accuracySlider.addChangeListener(new SliderChanged()); //Check for eligibility
		add(accuracySlider);
		
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
		screenBar.setBounds(200, 180, 100, 20);

		//The name field: no reset as it's assumed to be the same person
		nameField = new JTextField("Nickname");
		nameField.setBounds(200, 150, 100, 20);
		nameField.addActionListener(new NameListener());
	
		//Don't show the name message by default
		showNameMsg = false;
		nameSelected = false;

		//Add the name field
		add(nameField);

		//Add components
		add(titleLabel);
		add(screenBar);

		//Change the background of everything
		changeBackground(new Color(255, 255, 255));
	}

	//Paint the elligibility box
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (eligibility)
		{
			g.setColor(new Color(0, 255, 0));
			g.fillRect(540, 240, 100, 50);
			g.setColor(new Color(0, 0, 0));
			g.drawRect(540, 240, 100, 50); //Border the rect
			g.setFont(new Font("Times new roman", Font.PLAIN, 20));
			g.drawString("Eligible", 550, 280);
		}
		else
		{
			g.setColor(new Color(255, 0, 0));
			g.fillRect(540, 240, 100, 50);
			g.setColor(new Color(0, 0, 0));
			g.drawRect(540, 240, 100, 50);
			g.setFont(new Font("Times new roman", Font.PLAIN, 20));
			g.drawString("Ineligible", 550, 280);
		}
		//If you tried to play without a name change
		if (showNameMsg)
		{
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			if (backgroundColor.getRed() == 255 && backgroundColor.getBlue() == 0) //There is only only one color like this
			{
				g.setColor(new Color(0, 0, 0));
			}
			else
			{
				g.setColor(new Color(255, 0, 0));
			}
			g.drawString("Please enter your name", 200, 130);
		}
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
		accuracySlider.setBackground(color);
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
					if (nameSelected)
					{
						gamePanel.regenPillows(); //Re-generate the world every time
						gamePanel.regenBots((Math.PI / 2) * ((100 - accuracySlider.getValue()) / 100)); //0% accuracy represents Math.PI / 2.
									  //100% accuracy represents 0.
									  //So, I just use (Math.PI / 2) * ((100 - accuracy) / 100)

						//Start immune
						gamePanel.setEligibility((eligibility));
						gamePanel.setImmune(true);
						gamePanel.start(); //Start the game on the first time playing
						cards.show(mainCanvas, "Game");
					}
					else
					{
						showNameMsg = true;
					}
					break;
				case "Instructions":
					cards.show(mainCanvas, "Instructions");
					showNameMsg = false; //The user switched panels, now we don't show the message
					break;
				case "Hall of Fame":
					cards.show(mainCanvas, "Fame");
					showNameMsg = false;
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
			//Set the elligbility: something may have changed
			//Essentially: is the slider correct AND are all pillows enabled
			eligibility = (accuracySlider.getValue() == 50 && Pillow.pillowGen[0] && Pillow.pillowGen[1] && Pillow.pillowGen[2] && Pillow.pillowGen[3]);
			repaint(); //Repaint for elligibility
		}
	}

	//Completely separate listener for the namefield
	class NameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			gamePanel.setName(evt.getActionCommand());
			showNameMsg = false;
			nameSelected = true;
			repaint();
		}
	}

	//This is kind of stupid: it's JUST to check if we're still eligible
	class SliderChanged implements ChangeListener
	{
		public void stateChanged(ChangeEvent evt)
		{
			eligibility = (accuracySlider.getValue() >= 50 && Pillow.pillowGen[0] && Pillow.pillowGen[1] && Pillow.pillowGen[2] && Pillow.pillowGen[3]);
			repaint(); //Repaint for elligibility
		}
	}
}
