import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

public class LosePanel extends JPanel
{
	//The score: to show the score at the end
	private int score;

	//Home button
	private JButton homeButton;

	//To change panels
	GameHolder holder;
	CardLayout cards;

	//Will be shown once lost
	public LosePanel(GameHolder holderIn, CardLayout cardsIn) //To show the score
	{
		//To change panels
		holder = holderIn;
		cards = cardsIn;

		//Set to null
		setLayout(null);
		setBackground(new Color(0, 0, 0));
		homeButton = new JButton("Home");
		homeButton.setBounds(400, 450, 200, 100);
		homeButton.addActionListener(new ToHomeListener());
		add(homeButton);
	}
	
	public void setScore(int scoreIn)
	{
		score = scoreIn;
	}

	//Paint component!
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Times new roman", Font.PLAIN, 50));
		g.drawString("YOU LOSE", 350, 360);
		g.setFont(new Font("Times new roman", Font.PLAIN, 30));
		g.drawString(String.format("Score: %d", score), 350, 400);
	}

	class ToHomeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			cards.show(holder, "Home");
		}
	}
}
