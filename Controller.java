package ser.main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.text.html.parser.Entity;

import ser.main.classes.EntityA;
import ser.main.classes.EntityB;

@SuppressWarnings("unused")
public class Controller {

	private LinkedList<EntityA> ea = new LinkedList<EntityA>();
	private LinkedList<EntityB> eb = new LinkedList<EntityB>();

	EntityA enta;
	EntityB entb;

	private Texture tex;
	Random r = new Random();
	private Game game;
	private Sound sound;

	
	public Controller(Texture tex, Game game) {
		this.tex = tex;
		this.game = game;
	}

	
	public void createEnemy(int enemy_count) { //removed the int enemy_count arg
		for (int i = 0; i < enemy_count/2; i++) { // changed the enemy_count variable for a constant 5
			addEntity(new Enemy(r.nextInt(640), -10, tex, this, game));
			addEntity(new Asteroid(r.nextInt(640), -10, tex, this, game));
		}
	}

	public void tick() {

		// A Class
		for (int i = 0; i < ea.size(); i++) {
			enta = ea.get(i);
			enta.tick();
		}

		// B Class
		for (int i = 0; i < eb.size(); i++) {
			entb = eb.get(i);
			entb.tick();
		}

	}

	public void render(Graphics g) {

		// A Class
		for (int i = 0; i < ea.size(); i++) {
			enta = ea.get(i);

			enta.render(g);
		}
		// B Class
		for (int i = 0; i < eb.size(); i++) {
			entb = eb.get(i);

			entb.render(g);
		}

	}

	public void addEntity(EntityA block) {
		ea.add(block);
	}

	public void removeEntity(EntityA block) {
		ea.remove(block);
		sound = new Sound();
		
	}

	public void addEntity(EntityB block) {
		eb.add(block);
	}

	public void removeEntity(EntityB block) {
		eb.remove(block);
	}

	public LinkedList<EntityA> getEntityA() {
		return ea;
	}

	public LinkedList<EntityB> getEntityB() {
		return eb;
	}
}
