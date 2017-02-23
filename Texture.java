package ser.main;

import java.awt.image.BufferedImage;

public class Texture {

	public BufferedImage player, missile, enemy, asteroid, boom, fire;

	private SpriteSheet ss;
	Game game;
	public Texture(Game game) {
		ss = new SpriteSheet(game.getSpriteSheet());
		this.game = game;

		getTextures();
	}

	private void getTextures() {
		missile = ss.grabImage(2, 1, 32, 32);
		asteroid = ss.grabImage(4, 1, 32, 32);
		enemy = ss.grabImage(1, 1, 32, 32);
		player = game.getPlayer();
		

	}
}
