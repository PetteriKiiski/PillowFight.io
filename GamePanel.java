import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel
{
	Timer timer;
	public GamePanel()
	{
		PillowArray pillows = new PillowArray(1);
		timer = new Timer(16, new AnimateListener(pillows));
	}
}

class AnimateListener implements ActionListener
{
	//Take in all the info we need. Which is a lot.
	public AnimateListener(PillowArray pillows)
	{
		
	}
	
	public void actionPerformed(ActionEvent evt)
	{
	} 
}
