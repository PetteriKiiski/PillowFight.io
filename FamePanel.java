import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.awt.Font;
import java.io.PrintWriter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.Graphics;

//Hall of fame!
public class FamePanel extends JPanel
{
	private JLabel[] entries; //These are the labels
	private String[] names; //These are the names
	private int[] scores; //These are the score
	private JLabel titleLabel;
	private GameHolder holder;
	private CardLayout cards;
	private int newEntryIndex;

	public static final String SCORE_FILENAME = "hallOfFame.txt";

	public FamePanel(GameHolder holderIn, CardLayout cardsIn)
	{
		//To change cards
		holder = holderIn;
		cards = cardsIn;

		//The background, regardless of the customization
		setBackground(Color.YELLOW);
		setLayout(new GridLayout(12, 1)); //For title, 10 entries, and buttons
		
		//The title label
		titleLabel = new JLabel("THE HALL OF FAME", JLabel.CENTER);
		titleLabel.setFont(new Font("Times new roman", Font.PLAIN, 50));

		//Initialize the scores
		scores = new int[10]; //Defaults to 0

		//Initialize the names
		names = new String[10];

		//The score entries
		entries = new JLabel[10];
		
		//Initialize the entries
		for (int i = 0; i < entries.length; i++)
		{
			entries[i] = new JLabel("", JLabel.CENTER);
			entries[i].setFont(new Font("Times new roman", Font.PLAIN, 20));
		}
		
		//There is no new entry
		newEntryIndex = -1;

		loadScores();

		addEverything(); //Add EVERYTHING
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(0, 255, 0));
		//Paint the green background thing around the new score
		if (newEntryIndex >= 1)
		{
			//The shift: amount of height per score
			g.fillRect(0, entries[newEntryIndex].getY(), 1000, 67);
		}
	}

	public void addEverything()
	{
		removeAll(); //removes everything... duh
		add(titleLabel); //Add the label

		//Then everything else

		//Initialize and add all the score labels
		for (int i = 0; i < entries.length; i++)
		{
			add(entries[i]);
		}

		//The home button
		JButton homeButton = new JButton("Home");
		homeButton.addActionListener(new HomeButtonListener());
		add(homeButton); //Then add it
	}

	public void loadScores()
	{
		try
		{
			File file = new File("hallOfFame.txt");
			Scanner reader = new Scanner(file);
			String data;
			int index = 0; //The entry index
			while (reader.hasNextLine())
			{
				data = reader.nextLine(); //Is in the format: Name: score
				entries[index].setText(String.format("%d. %s", index + 1, data));
				if (data.strip() != "") //Make sure it's not empty
				{
					try
					{
						scores[index] = Integer.parseInt(data.split(":")[1].strip());
						names[index] = data.split(":")[0].strip();
						index++;
					}
					catch (NumberFormatException err) //This seems to be the error that is thrown
					{
						scores[index] = 0;
					}
				}
			}
			for (; index < entries.length; index++) //Index is already defined, a ';' is enough
			{
				entries[index].setText(String.format("%d.", index + 1));
			}
			repaint();
		}
		catch (FileNotFoundException err)
		{
			System.err.println("ERROR: COULD NOT FIND HIGH SCORE FILE");
		}
	}

	//Tells you if the score is a new high score
	public boolean isHighScore(int score)
	{
		//If the scores aren't full, just fill it in
		if (scores.length < 10)
		{
			return true;
		}

		for (int i = 0; i < scores.length; i++)
		{
			if (score > scores[i])
			{
				return true;
			}
		}
		return false;
	}

	//Enter a score
	public void addEntry(String name, int score)
	{
		//Change the label of the position!
		for (int i = 9; i >= 0; i--) //Go backwards through array
		{
			if (score > scores[i])
			{
				if (i != 9) //If you were 10th, someone beat you
				{
					scores[i + 1] = scores[i]; //Otherwise, move one down
					names[i + 1] = names[i];
					entries[i + 1].setText(String.format("%d. %s: %d", i + 2, names[i], scores[i]));//The number needs to be changed sadly(to i + 2, because index is i + 1)
				}
				//The scores(and the entries) will be changed later.
			}
			//Note that scores[i] has not been changed yet
			if (score <= scores[i] || i == 0) //The best score needs to be entered too!
			{
				if (i == 0 && score > scores[i]) //If it's the best score, we will cheat and make i + 1 equal to 0
				{
					i = -1;
				}
				if (i != 9) //If it was a high score(which it should)
				{
					entries[i + 1] = new JLabel(String.format("%d. %s: %d", i + 2, name, score), JLabel.CENTER);//i + 1 actually represents a worse score in the hall of fame
					entries[i + 1].setFont(new Font("Times new roman", Font.PLAIN, 20));
					newEntryIndex = i + 1;
					scores[i + 1] = score;
					names[i + 1] = name;
				}
				break; //It is finished
			}
		}

		//Then, write the updated data
		try
		{
			PrintWriter writer = new PrintWriter(new File(SCORE_FILENAME));
			for (int i = 0; i < entries.length; i++)
			{
				writer.println(String.format("%s: %d", names[i], scores[i])); //Write the text of the label
			}
			writer.close();
		}
		catch (FileNotFoundException err)
		{
			System.err.println("ERROR: COULD NOT FINE HIGH SCORE FILE");
		}

		addEverything();//Reset the panel
		repaint(); //Then, repaint everything
	}
	
	//I've done this same thing SO MUCH
	class HomeButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			newEntryIndex = -1;
			cards.show(holder, "Home");
		}
	}
}
