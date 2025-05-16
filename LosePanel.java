import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class LosePanel extends JPanel
{
	//Will be shown once lost
	public LosePanel()
	{
		setBackground(new Color(0, 0, 0));
	}
	
	//Paint component!
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Times new roman", Font.PLAIN, 50));
		g.drawString("YOU LOSE", 450, 400);
	}
}
