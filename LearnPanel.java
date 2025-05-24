import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

public class LearnPanel extends JPanel
{
	private JLabel firstLabel; //Just line separators
	private JLabel secondLabel;
	private JButton loseButton;
	private Timer timer;
	private boolean toHighScore; //Where do we go next?

	public LearnPanel(CardLayout cards, GameHolder panel)
	{	
		setLayout(null);

		//Where do we go next?
		toHighScore = false; //By default

		//The two labels
		firstLabel = new JLabel();
		firstLabel.setBounds(10, 0, 990, 100);
		
		secondLabel = new JLabel();
		secondLabel.setBounds(10, 100, 990, 100);
		
		//The button
		loseButton = new JButton("See your score");
		loseButton.setBounds(400, 350, 200, 100);
		loseButton.addActionListener(new LearnButtonListener(cards, panel));

		//The button's timer: only show after 5 seconds
		timer = new Timer(5000, new LearnListener());
		
		//Add them
		add(firstLabel);
		add(secondLabel);
		//Don't add the button quite yet. After 5 seconds, when you've LEARNED
	}
	
	//Set the math problem to teach
	public void setProblem(int solution, int typeIn, int operation) //These values represent the entire operation:
	{
		String type; //It will be more useful as a string
		String symbol; //Same thing here
		
		//Set the fonts
		firstLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		secondLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		
		switch(typeIn)
		{
			case 0:
				//I couldn't figure out how to use \t
				firstLabel.setText(String.format("x + %d = %d        Subtract %d to both sides\n", operation, solution + operation, operation));
				break;
			case 1:
				firstLabel.setText(String.format("x - %d = %d        Add %d to both sides\n", operation, solution - operation, operation));
				break;
			case 2:
				firstLabel.setText(String.format("%dx = %d        Divide by %d on both sides", operation, solution * operation, operation));
				break;
			case 3:
				firstLabel.setText(String.format("x ÷ %d = %d        Multiply %d to both sides", operation, solution / operation, operation));
				break;
		}
		secondLabel.setText(String.format("x = %d", solution)); //So much simpler formatting
		removeButton();
		timer.start(); //Start the timer!
	}

	//Has a high score: go to hall panel next
	public void hasHighScore()
	{
		toHighScore = true;
	}

	public void haveButton()
	{
		add(loseButton); //Enable the button the makes you lose
		repaint();
	}
	
	//For preparation
	public void removeButton()
	{
		remove(loseButton);
		repaint();
	}
	
	//For five seconds
	class LearnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{	
				haveButton();
		}
	}

	class LearnButtonListener implements ActionListener
	{
		//For switching panels
		private CardLayout cards;
		private GameHolder panel;

		public LearnButtonListener(CardLayout cardsIn, GameHolder holder)
		{
			cards = cardsIn;
			panel = holder;
		}

		public void actionPerformed(ActionEvent evt)
		{
			if (!toHighScore)
			{
				cards.show(panel, "Loss");
			}
			else
			{
				toHighScore = false; //Reset the state
				cards.show(panel, "Hall Entry");
			}
		}
	}
}
