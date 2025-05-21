import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.io.IOException;

public class LoadedImages
{
	public static Image pillow;
	public static Image lightPillow;
	public static Image heavyPillow;
	public static Image healPillow;
	public static Image emptyHeart;
	public static Image halfHeart;
	public static Image fullHeart;
	
	public static void loadImages()
	{
		try
		{
			pillow = ImageIO.read(new File("images/pillow.png"));
			lightPillow = ImageIO.read(new File("images/lightPillow.png"));
			heavyPillow = ImageIO.read(new File("images/heavyPillow.png"));
			healPillow = ImageIO.read(new File("images/healPillow.png"));
			emptyHeart = ImageIO.read(new File("images/emptyHeart.png"));
			halfHeart = ImageIO.read(new File("images/halfHeart.png"));
			fullHeart = ImageIO.read(new File("images/fullHeart.png"));
		}
		catch (IOException err)
		{
			System.err.println("ERROR LOADING IMAGES");
		}
	}
}
