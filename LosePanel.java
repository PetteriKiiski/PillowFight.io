import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class LosePanel extends JPanel
{
	//The score: to show the score at the end
	private int score;

	//Will be shown once lost
	public LosePanel() //To show the score
	{
		setBackground(new Color(0, 0, 0));
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
}
