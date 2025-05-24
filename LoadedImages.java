import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.io.IOException;

//All images are created here
public class LoadedImages
{
	public static Image pillow;
	public static Image lightPillow;
	public static Image heavyPillow;
	public static Image healPillow;
	public static Image emptyHeart;
	public static Image halfHeart;
	public static Image fullHeart;
	public static Image welcome;
	public static Image overview;
	public static Image learnHold;
	public static Image holding;
	public static Image hit;
	public static Image lowHealth;
	public static Image findHeal;
	public static Image healed;
	
	public static void loadImages()
	{
		try
		{
			pillow = ImageIO.read(new File("images/pillow.png"));
			lightPillow = ImageIO.read(new File("images/lightPillow.png"));
			heavyPillow = ImageIO.read(new File("images/heavyPillow.png"));
			healPillow = ImageIO.read(new File("images/healPillow.png"));
			emptyHeart = ImageIO.read(new File("images/EmptyHeart.png"));
			halfHeart = ImageIO.read(new File("images/HalfHeart.png"));
			fullHeart = ImageIO.read(new File("images/FullHeart.png"));
			welcome = ImageIO.read(new File("images/Welcome.png"));
			overview = ImageIO.read(new File("images/Overview.png"));
			learnHold = ImageIO.read(new File("images/LearnHold.png"));
			holding = ImageIO.read(new File("images/Holding.png"));
			hit = ImageIO.read(new File("images/Hit.png"));
			lowHealth = ImageIO.read(new File("images/LowHealth.png"));
			findHeal = ImageIO.read(new File("images/FindHeal.png"));
			healed = ImageIO.read(new File("images/Healed.png"));
		}
		catch (IOException err)
		{
			System.err.println("ERROR LOADING IMAGES");
			err.printStackTrace();
		}
	}
}
