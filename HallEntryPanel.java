import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

public class HallEntryPanel extends JPanel
{
	private int score; //To give to the FamePanel
	private FamePanel famePanel; //To give the score
	private CardLayout cards;
	private GameHolder holder;

	public HallEntryPanel(FamePanel famePanelIn, GameHolder holderIn, CardLayout cardsIn)
	{
		setBackground(new Color(255, 255, 255));
		setLayout(null); //I just need the textField to be centered.

		//The hall of fame
		famePanel = famePanelIn;

		//To switch to the hall of fame
		holder = holderIn;
		cards = cardsIn;

		//The textfield
		JTextField nameField = new JTextField(""); //For the name
		nameField.setBounds(450, 350, 100, 20);
		nameField.addActionListener(new NameListener());

		//Directions on what to do
		//I'd do the <html><br></html> syntax, but it doesn't give me exactly what I want
		JLabel directionsLabel1 = new JLabel("Congratulations on entering the hall of fame!", JLabel.CENTER);
		JLabel directionsLabel2 = new JLabel("Enter your name here", JLabel.CENTER);
		directionsLabel1.setBounds(300, 250, 400, 50);
		directionsLabel2.setBounds(300, 300, 400, 50);
		directionsLabel1.setFont(new Font("Times new roman", Font.PLAIN, 20));
		directionsLabel2.setFont(new Font("Times new roman", Font.PLAIN, 20));

		//Then just add them
		add(nameField);
		add(directionsLabel1);
		add(directionsLabel2);
	}

	//Protected -> public access
	public void changeBackground(Color color)
	{
		setBackground(color);
	}

	//To set the score, since this can't be done on construction
	public void setScore(int scoreIn)
	{
		score = scoreIn;
	}

	class NameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			famePanel.addEntry(evt.getActionCommand(), score); //Give the name and the score
			cards.show(holder, "Fame");
		}
	}
}
