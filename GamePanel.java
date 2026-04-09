package GameSystem;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ui_items.EnergyBar;
import ui_items.Hotbar;
import ui_items.ImageResizer;
import ui_items.Lanes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import Entity.*;

public class GamePanel extends JPanel implements Runnable, ImageResizer {

	private static final long serialVersionUID = 1L;
	// --- 1. SCREEN SETTINGS ---
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 1;
	public final int tileSize = originalTileSize * scale; // 48x48 tile

	// --- 2. SYSTEM ---
	int FPS = 60;
	KeyInput keyH = new KeyInput();

	Hotbar hotbar;
	EnergyBar energyBar;
	Lanes lanes;
	JLabel background;
	Thread gameThread = null;
	public static boolean isPaused = false;
	public static boolean isOver = false;
	public static int time = 0;
	public static boolean oneSecondPassed = false;
	private Piano piano;
	// to have delay when spawning enemies:
	private static int spawnTick = 0;
	private static int spawnRate = 240;

	private KillCounter killCounter;
	double delta = 0;
	public static boolean isTransitioning = false;
	int transitionAlpha = 0;

	Image bulletImg;

	// --- CONSTRUCTOR ---
	public GamePanel() {
		resetLists();
		this.gameThread = null;
		hotbar = new Hotbar();
		energyBar = new EnergyBar();
		lanes = new Lanes();
		background = setUpBackground();
		piano = new Piano(0, 0);
		JLabel pianoImg = new JLabel(ImageResizer.imageResize("/img/piano.png", Lanes.x, Lanes.height));
		pianoImg.setBounds(0, 0, Lanes.x, Lanes.height);
		bulletImg = (ImageResizer.imageResize("/img/bullet.png", 96, 96).getImage());
		loadEnemyImages();
		killCounter = new KillCounter();

		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
		this.setDoubleBuffered(true); // Improves rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.setLayout(null);

		hotbar.setBounds((Main.WIDTH - hotbar.WIDTH), Main.HEIGHT - hotbar.HEIGHT, hotbar.WIDTH, hotbar.HEIGHT);
		add(hotbar);
		add(energyBar);
		add(lanes);
		add(pianoImg);
		add(background);
		this.setVisible(true);
	}

	// --- START METHOD ---
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void stopGameThread() {
		if (gameThread != null)
			gameThread = null;
	}

	private JLabel setUpBackground() {
		JLabel background = new JLabel();
		ImageIcon backgroundImgRaw = new ImageIcon(getClass().getResource("/img/background.png"));
		Image backgroundImg = backgroundImgRaw.getImage().getScaledInstance(Main.WIDTH, Main.HEIGHT,
				Image.SCALE_REPLICATE);
		background.setIcon(new ImageIcon(backgroundImg));
		background.setBounds(0, 0, Main.WIDTH, Main.HEIGHT);
		return background;
	}

	private void loadEnemyImages() {
		try {
			Enemy.imgA = ImageIO.read(getClass().getResourceAsStream("/img/enemies/drum.png"));
			Enemy.imgB = ImageIO.read(getClass().getResourceAsStream("/img/enemies/metronome.png"));
			Enemy.imgC = ImageIO.read(getClass().getResourceAsStream("/img/enemies/greenSlime.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error loading enemy images!");
		}
	}

	// --- GAME LOOP (The "Heart") ---
	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {
			pauseKey();
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			if (delta >= 1) {
				drawCount++;
				if (!isPaused)
					update();
				repaint();
				delta--;
			}
			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				timer = 0;
				drawCount = 0;
				if (!isPaused) {
					time++;
					oneSecondPassed = true;
				}
			}
		}
	}

	// --- UPDATE (Logic) ---
	public void update() {
		if (piano.isDead()) {
			isOver = true;
			Main.gameOver();
		}
//		piano.setPianoHp(0);
		if (time % 10 == 0) {
			if (spawnRate >= 110)
				spawnRate -= 10;
		}

		hotbar.update();
		energyBar.updateBars();
		keyBinds();
		if (oneSecondPassed) {
			EnergyBar.curEnergy++;
			oneSecondPassed = false;
		}

		// UPDATE ENTITIES:
		piano.update();
		// Update Enemy
		for (int i = 0; i < Enemy.enemyList.size(); i++) {
			Enemy e = Enemy.enemyList.get(i);
			if (e.isDead()) {
				killCounter.addKill(e.getClass().getSimpleName());
				killCounter.printAllStats();// debug purpose
				if (Math.random() < 0.3f)
					hotbar.addItem((int) (Math.random() * 3));
				Enemy.enemyList.remove(i);
				i--;
			} else {
				e.update();
			}

			// COLLISON HANDLING:
			// Piano hit by enemy
			if (piano.checkCollision(e) && !piano.isDead()) {
				Enemy.enemyList.remove(e);
				System.out.println("Piano hit by enemy remaining HP: " + piano.getPianoHp());
				piano.setPianoHp(piano.getPianoHp() - 10); // reduce piano hp by 10 (10 hits later death)
			}

		}

		// ENEMY SPAWN:
		spawnTick++; // continues until spawnTick is 180 (3s passed)
		if (spawnTick >= spawnRate) { // spawns enemy after 2s
			Enemy.spawnEnemy();
			spawnTick = 0; // resets so that it can respawn enemy
		}

	}// end of update()

	// --- DRAW (Rendering) ---
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void paintChildren(Graphics g) { // This draws AFTER the labels dont forget
		super.paintChildren(g);

		Graphics2D g2 = (Graphics2D) g;

		// DRAW ENEMIES:
		for (Enemy e : Enemy.enemyList) {
			if (e != null) {
				g2.drawImage(e.getCurrentFrame(), (int) e.getX(), (int) e.getY(), null);
				// null is observer updates for loads we already have it here so no need, it is
				// null
			}
		}
		// DRAW BULLETS
		g2.setColor(Color.RED);
		for (int i = 0; i < Piano.shot.size(); i++) {
			Bullet b = Piano.shot.get(i);
			if (b != null) {
				g2.drawImage(bulletImg, (int) b.getX(), (int) b.getY() + (int) (96 / 6 * Main.scalerY),
						(int) (96 * Main.scalerX), (int) (96 * Main.scalerY), null);

			}
		}
		if (isTransitioning) {
			g2.setColor(new Color(0, 0, 0, transitionAlpha));
			g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
			transitionAlpha = transitionAlpha + 2;
			if (transitionAlpha >= 255) {
				transitionAlpha = 255;
				isOver = true;
			}
		}
	}

	private void resetLists() {
		Enemy.enemyList.removeAll(Enemy.enemyList);
		Piano.shot.removeAll(Piano.shot);
	}

	private void pauseKey() {
		if (keyH.isPressedOnce(KeyEvent.VK_ESCAPE)) {
			Main.openPauseMenu();
		}
	}

	private void keyBinds() {
		if (!piano.isDead()) {
			if (keyH.isPressedOnce(KeyEvent.VK_C))
				hotbar.useItem(0);

			if (keyH.isPressedOnce(KeyEvent.VK_V))
				hotbar.useItem(1);

			if (keyH.isPressedOnce(KeyEvent.VK_B))
				hotbar.useItem(2);

			if (keyH.isPressedOnce(KeyEvent.VK_N))
				hotbar.useItem(3);

			if (keyH.isPressedOnce(KeyEvent.VK_M))
				hotbar.useItem(4);
		}
		if (keyH.isPressedOnce(KeyEvent.VK_SPACE)) {
			hotbar.addItem((int) (Math.random() * 3));
		}
		if (keyH.isPressedOnce(KeyEvent.VK_Q))
			piano.fire(0);
		if (keyH.isPressedOnce(KeyEvent.VK_W))
			piano.fire(1);
		if (keyH.isPressedOnce(KeyEvent.VK_E))
			piano.fire(2);
		if (keyH.isPressedOnce(KeyEvent.VK_U))
			piano.fire(3);
		if (keyH.isPressedOnce(KeyEvent.VK_I))
			piano.fire(4);
		if (keyH.isPressedOnce(KeyEvent.VK_O))
			piano.fire(5);
		if (keyH.isPressedOnce(KeyEvent.VK_P))
			piano.fire(6);

	}
}
