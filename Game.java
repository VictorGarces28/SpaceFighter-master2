package ser.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import ser.main.classes.EntityA;
import ser.main.classes.EntityB;

public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "Space Game";

	private boolean running = false;
	private Thread thread;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	private BufferedImage background1 = null;
	private BufferedImage player = null;

	private boolean is_shooting = false;

	private int score = 0; // new variable to set score
	private int enemy_count = 8; // changed from 5 to 0 // changed it from 0 to nothing
	private int enemy_killed = 0; //changed it from 0  to nothing
	private Sound sound;
	
	/*
	 * This part of the code is to determine the score of the game
	 * 
	 */
	
	public void setScore(int enemy_count) {
		this.score += enemy_killed;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getEnemt_killed() {
		return enemy_killed;
	}

	public void setEnemt_killed(int enemy_killed) {
		this.enemy_killed += enemy_killed; //Changed = to +=
		this.enemy_killed += score;
	}

	private Player p;
	private Controller c;
	private Texture tex;
	private Menu menu;

	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;

	public static int HEALTH = 100 * 2;

	public static enum STATE {
		MENU, GAME
	};

	public static STATE State = STATE.MENU;

	public void init() {
		
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/sheet.png");
			background = loader.loadImage("/starbg.png");
			background1 = loader.loadImage("/starbg.png");
			player = loader.loadImage("/space.gif");

		} catch (IOException e) {
			e.printStackTrace();
		}

		addKeyListener(new KeyInput(this));
		this.addMouseListener(new MoustInput());

		tex = new Texture(this);

		c = new Controller(tex, this);
		p = new Player(WIDTH, HEIGHT * 2, tex, this, c);
		menu = new Menu();

		ea = c.getEntityA();
		eb = c.getEntityB();

		sound = new Sound();
		c.createEnemy(enemy_count);// removed the enemy_count argument
		sound.stopGame(); // vhgm
		sound.playMenu(); // vhgm
	}

	private synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!running)
			return;

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	public void run() {
		init();
		sound.playGame();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0; // was 60.0 . higher means faster game-play
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}

		}
		stop();

	}

	int ybg = 0;
	int ybg2 = -((HEIGHT*2));
	// everything that updates
	private void tick() {
		
		if(ybg2<= 0){
			ybg2 += 1 ;
		}else{
			ybg2 = -((HEIGHT*2));
		}
		
		if(ybg<=(HEIGHT*2)){
			ybg += 1 ;
		}else{
			ybg = 0;
		}
		
		if (State == STATE.GAME) {
			p.tick();
			c.tick();
			
			
		}
		if (enemy_killed >= enemy_count) { //Changed from >= to >
			enemy_count += 2; //changed +=2 to +=1 // changed 1 to 10
			//enemy_killed = 0;  // Commented and then uncommented this line to see what effect would have for the amount enemies on screen
			c.createEnemy(enemy_count);// removed the enemy_count argument
			setScore(enemy_count); // added this line to keep score // canged to 5
			enemy_killed = 0; //Moved the line to the bottom to keep score
		}



	}

	
	// everything that renders
	@SuppressWarnings("static-access")
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		//

		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(background, 30, ybg, null);
		g.drawImage(background, 30, ybg2, null);
		
		if (State == STATE.GAME) {
			//g.drawImage(background1, 0, 0, null);
			g.drawImage(background1, 30, ybg, null);
			g.drawImage(background1, 30, ybg2, null);
			p.render(g);
			c.render(g);

			int alpha = 150;
			Color grey = new Color(224, 224, 224, alpha);
			Color green = new Color(0, 255, 0, alpha);
			Color white = new Color(255, 225, 225, alpha);

			g.setColor(grey);
			g.fillRect(5, 5, 200, 20);

			g.setColor(green);
			g.fillRect(5, 5, HEALTH, 20);

			g.setColor(white);
			g.drawRect(5, 5, 200, 20);

			@SuppressWarnings("unused")
			Graphics2D g2d = (Graphics2D) g;

			g.setColor(grey);
			g.fillRect(WIDTH * 15 / 10, 5, 80, 20);

			Font fnt0 = new Font("arial", Font.BOLD, 13);
			g.setFont(fnt0);
			g.setColor(Color.white);
			g.drawString("Score : " + String.valueOf(getScore()), WIDTH * 15 / 10, 20); // Change the variable from enemy_killed to getScore

		} else if (State == STATE.MENU) {
			menu.render(g);
		}
		if (HEALTH <= 0) {
			State = STATE.MENU;
			//sound.playGame();
			sound.stopGame();
			this.HEALTH = 200;
			this.score = 0; //to reset score after game over
			this.enemy_count = 8; // to reset enemy count after game over
			this.enemy_killed = 0; // to reset enemy killed after game over
			this.ea.removeAll(ea); // see if this works
			this.eb.removeAll(eb); // see if this works
			init(); //to start a new game after game over
			sound.stopGame();
		}
		
		g.dispose();
		bs.show();
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (State == STATE.GAME) {
			if (key == KeyEvent.VK_RIGHT) {
				p.setVelX(5);
			} else if (key == KeyEvent.VK_LEFT) {
				p.setVelX(-5);

			} else if (key == KeyEvent.VK_DOWN) {
				p.setVelY(5);

			} else if (key == KeyEvent.VK_UP) {
				p.setVelY(-5);
			} else if (key == KeyEvent.VK_SPACE && !is_shooting) {
				c.addEntity(new Bullet(p.getX(), p.getY(), tex, this));
				sound.playGunSound();
				is_shooting = true;
			}
			
			 
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (State == STATE.MENU) {
			if (key == KeyEvent.VK_ESCAPE) {
				Game.State = Game.STATE.GAME;// pause the game . click play to resume.
			}
		}
		else if (State == STATE.GAME) {
		
		if (key == KeyEvent.VK_RIGHT) {
			p.setVelX(0);
		} else if (key == KeyEvent.VK_LEFT) {
			p.setVelX(0);

		} else if (key == KeyEvent.VK_DOWN) {
			p.setVelY(0);

		} else if (key == KeyEvent.VK_UP) {
			p.setVelY(0);
		} else if (key == KeyEvent.VK_SPACE) {
			sound.stopGunSound();
			is_shooting = false;
		}
		else if (key == KeyEvent.VK_ESCAPE) {
			 State = STATE.MENU;// pause the game . click play to resume.

		}
		
		}
		
	}

	public static void main(String[] args) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();

	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public BufferedImage getPlayer() {
		return player;
	}

	public int getEnemy_count() {
		return enemy_count;
	}

	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}

	public int getEnemy_killed() {
		return enemy_killed;
	}

	public void setEnemy_killed(int enemy_killed) {
		this.enemy_killed = enemy_killed;
	}
	

}
