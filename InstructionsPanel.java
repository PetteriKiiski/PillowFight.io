import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.CardLayout;

public class InstructionsPanel extends JPanel
{
	private JLabel topLabel;
	private int labelIndex;
	private String[] labelStrings;
	private ImagePanel imagePanel;
	
	public InstructionsPanel(CardLayout cards, GameHolder showPanel)
	{
		
		//We use border layout here. Especially since it didn't work in the game panel
		setLayout(new BorderLayout(0, 0));
	
		//Label strings
		labelStrings = new String[]{"Welcome to your pillow fight!", "Overview", "To fight, first pick up a pillow.",
										"It must be a solution to the problem.", "There is one there! You Left-click to pick up.",
										"You throw in the direction of the mouse.", "This is done with left click also.",
										"Hopefully, you hit. The bot lost health.", "If you destroy a bot, you get 1000 score.",
										"If not, keep trying.", "Sometimes, you'll get hit!", "To heal, find a correct pink pillow.",
										"Once you find it, pick it up.", "Then right click to heal from it.", "Now, an overview of pillow stats",
										"Pillow.", "Speed: 1x, Damage: 1", "Light Pillow", "Speed: 1x, Damage: 0.5", "Heavy Pillow.", "Speed: 0.8x, Damage: 2",
										"Heal Pillow.", "Speed 1x, Damge 0.5", "Special: Heals 0.5 hearts"};
	
		//The index of the label
		labelIndex = 0;
		
		//The label at the top
		topLabel = new JLabel(labelStrings[labelIndex], JLabel.CENTER);
		topLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		
		//Add it
		add(BorderLayout.NORTH, topLabel);
		
		//Everything else is on a panel, on which everything else is placed
		imagePanel = new ImagePanel(this, cards, showPanel);
		add(BorderLayout.CENTER, imagePanel);
	}
	
	//Label turning mechanisms
	public void next()
	{
		if (labelIndex < labelStrings.length)
		{
			labelIndex++;
			topLabel.setText(labelStrings[labelIndex]);
			imagePanel.next();
			repaint();
		}
	}

	public void prev()
	{
		if (labelIndex > 0)
		{
			labelIndex--;
			topLabel.setText(labelStrings[labelIndex]);
			imagePanel.prev();
			repaint();
		}
	}
	
	//Reset everything
	public void reset()
	{
		labelIndex = 0;
		imagePanel.reset();
	}
}

class ImagePanel extends JPanel
{
	private Image image;
	private Image[] imageArray;
	private int imageIndex;
	private JButton nextButton;
	private JButton prevButton;
	private JButton homeButton;
	private InstructionsPanel mainPanel;
	private CardLayout cards;
	private GameHolder holder;
	
	public ImagePanel(InstructionsPanel mainPanelIn, CardLayout cardsIn, GameHolder showPanel)
	{
		//Null layout
		setLayout(null);
		
		//The main panel
		mainPanel = mainPanelIn;
		
		//Array of all the images
		imageArray = new Image[]{LoadedImages.welcome, LoadedImages.overview, LoadedImages.learnHold, LoadedImages.learnHold, LoadedImages.learnHold, 
			LoadedImages.holding, LoadedImages.holding, LoadedImages.hit, LoadedImages.hit, LoadedImages.hit, LoadedImages.lowHealth, LoadedImages.lowHealth,
			LoadedImages.findHeal, LoadedImages.healed, LoadedImages.healed, LoadedImages.pillow, LoadedImages.pillow, LoadedImages.lightPillow, 
			LoadedImages.lightPillow, LoadedImages.heavyPillow, LoadedImages.heavyPillow, LoadedImages.healPillow, LoadedImages.healPillow, 
			LoadedImages.healPillow};
		
		//the image index
		imageIndex = 0;
		
		//Initialize the image
		image = imageArray[imageIndex];
		
		//All the buttons
		nextButton = new JButton("Next");
		nextButton.setBounds(880, 600, 100, 50);
		prevButton = new JButton("Prev.");
		prevButton.setBounds(20, 600, 100, 50);
		homeButton = new JButton("Home");
		homeButton.setBounds(0, 0, 200, 100);
	
		//The listener
		NavigationButtons listener = new NavigationButtons();
		
		//Add this listener
		nextButton.addActionListener(listener);
		prevButton.addActionListener(listener);
		homeButton.addActionListener(listener);
	
		//To switch the panel
		cards = cardsIn;
		holder = showPanel;
		
		//Add 'em all
		add(nextButton);
		add(prevButton);
		add(homeButton);
	}
	
	//Page turning mechanisms
	public void next()
	{
		imageIndex++;
		image = imageArray[imageIndex];
	}
	
	public void prev()
	{
		imageIndex--;
		image = imageArray[imageIndex];
	}
	
	//Reset this
	public void reset()
	{
		imageIndex = 0;
	}
	
	//Paint image
	public void paintComponent(Graphics g)
	{
		g.drawImage(image, 200, 100, 800, 600, this);
	}
	
	//Button listener
	class NavigationButtons implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String action = evt.getActionCommand();
			if (action == "Next")
			{
				if (imageIndex < imageArray.length - 1)
				{
					mainPanel.next();
				}
				else
				{
					cards.show(holder, "Home");
					mainPanel.reset();
				}
			}
			else if (action == "Prev.")
			{
				if (imageIndex > 0)
				{
					mainPanel.prev();
				}
				else
				{
					cards.show(holder, "Home");
					mainPanel.reset();
				}
			}
			else if (action == "Home")
			{
				cards.show(holder, "Home");
				mainPanel.reset();
			}
		}
	}
}
