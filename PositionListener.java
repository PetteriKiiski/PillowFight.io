import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class PositionListener implements MouseListener
{
	public void mouseClicked(MouseEvent evt) {
		System.out.printf("Clicked at: (%d, %d)\n", evt.getX(), evt.getY());
	}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mousePressed(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {}
}
