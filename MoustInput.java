package ser.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MoustInput implements MouseListener {

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

		int mx = e.getX();
		int my = e.getY();

		// public Rectangle playButton = new Rectangle(Game.WIDTH/2+120,
		// 150,100,50);
		// public Rectangle helpButton = new Rectangle(Game.WIDTH/2+120,
		// 250,100,50);
		// public Rectangle quiButton = new Rectangle(Game.WIDTH/2+120,
		// 350,100,50);

		// Play Button
		if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
			if (my >= 150 && my <= 200) {
				// Play button
				Game.State = Game.STATE.GAME;
			}
		}

		// Quit Button
		if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
			if (my >= 350 && my <= 400) {
				// Play button
				System.exit(1);
			}
		}

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

}
