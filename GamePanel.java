// GamePanel.java - Actually play the game.

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;

public class GamePanel extends JPanel
{
	//Our timer
	Timer timer;
	
	//Array of all the pillows
	PillowArray pillows;
	
	public GamePanel()
	{
		pillows = new PillowArray(10);
		timer = new Timer(16, new AnimateListener(this, pillows));
		timer.start();
	}
	
	public void paintComponent(Graphics g)
	{
		pillows.paintPillows(g);
	}
}

class AnimateListener implements ActionListener
{
	//Array of pillows
	PillowArray pillows;
	
	//The Drawing panel
	GamePanel panel;
	
	//Take in all the info we need. Which is a lot.
	public AnimateListener(GamePanel panelIn, PillowArray pillowsIn)
	{
		pillows = pillowsIn;
		panel = panelIn;
	}
	
	//"while (true) {" loop
	public void actionPerformed(ActionEvent evt)
	{
		panel.repaint();
	} 
}
