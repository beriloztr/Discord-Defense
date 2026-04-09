package Entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import GameSystem.Main;
import ui_items.Lanes;

public abstract class Enemy extends Character {

	// Declarations
	protected double speed;
	protected String laneChangeRate; // Low, Medium, High
	protected int curLane;
	protected BufferedImage[] animation;
	public static ArrayList<Enemy> enemyList = new ArrayList<>();
	protected int aniTick;
	protected int aniIndex; // Frame index for each element in animation array.
	protected int aniSpeed = 15;
	public static double enemySpeedMultiplayer = 1;
	protected int laneChangeCooldown = 240;
	protected int laneChangeTick = 0;

	public static BufferedImage imgA, imgB, imgC;

	// Constructor
	public Enemy(double x, double y, double speed, String laneChangeRate, int curLane, BufferedImage sheet,
			int frameCount) {
		super(x, y, 50, 50); // default 50x50 but it will be overwritten
		this.speed = speed;
		this.laneChangeRate = laneChangeRate;
		this.curLane = curLane;
		this.hp = 3; //enemies have 3 hp default some will change to balance
		loadAnimation(sheet, frameCount);
		enemyList.add(this);

	}

	// Methods
	private void loadAnimation(BufferedImage sheet, int frameCount) {
		animation = new BufferedImage[frameCount];
		int frameWidth = sheet.getWidth() / frameCount;
		int frameHeight = sheet.getHeight();
		/*
		 * System.out.println("frameWidth: " + frameWidth);
		 * System.out.println("frameHeight: " + frameHeight);
		 */
		this.width = frameWidth;
		this.height = frameHeight;

		for (int i = 0; i < frameCount; i++) { // ask !!!
			animation[i] = sheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight); // crop the img using subimage
			// i * frameWidth => starting point for each iteration (x-axis)
			// 0 => starting point for each iteration (y-axis)
		}
	}

	public static void spawnEnemy() {
		int laneIndex = (int) (Math.random() * Lanes.laneCount); // random lane
		double startY = laneIndex * Lanes.laneHeight; // find starting y coordinate
		double startX = Main.WIDTH + 50; // making sure the spawn is beyond screen by + 50

		// Find Enemy Type
		int type = (int)(Math.random() * 3);

		switch (type) {
		case 0:
			new TypeA(startX, startY, 1.0, "Low", laneIndex, imgA); // Enemy obj creation here
			break;
		case 1:
			new TypeB(startX, startY, 0.5, "Medium", laneIndex, imgB);
			break;
		case 2:
			new TypeC(startX, startY, 0.3, "High", laneIndex, imgC);
			break;
		}
	}

	public void cycleSubSheets() {
		aniTick++;

		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;

			if (aniIndex >= animation.length)
				aniIndex = 0;
		}
	}

	public void changeLane() {
		double chance = 0;

		if (laneChangeRate.equalsIgnoreCase("low"))
			chance = 0.003;
		else if (laneChangeRate.equalsIgnoreCase("medium"))
			chance = 0.005;
		else if (laneChangeRate.equalsIgnoreCase("high"))
			chance = 0.007;
		else
			System.out.println("invalid laneChangeRate.");
		laneChangeTick++;
		if (laneChangeTick > laneChangeCooldown) // 4s cooldown
			if (Math.random() < chance) {
				move();
				laneChangeTick = 0;
			}
	}

	public void move() {
		boolean up = false;
		if (Math.random() > 0.5)
			up = true;

		if (up) {
			if (curLane >= 0 && curLane < (Lanes.laneCount - 1)) {
				this.y += Lanes.laneHeight; // go up
				++curLane;
//				System.out.println("moving 1 lane up, new lane: " + curLane);
			} else if (curLane == (Lanes.laneCount - 1)) {
				this.y -= Lanes.laneHeight; // go down
				--curLane;
//				System.out.println("moving 1 lane down due to top, new lane: "+curLane);

			}
		} else {
			if (curLane <= (Lanes.laneCount - 1) && curLane != 0) {
				this.y -= Lanes.laneHeight; // go down
				--curLane;
//				System.out.println("moving 1 lane down, new lane: "+curLane );
			} else if (curLane == 0) {
				this.y += Lanes.laneHeight; // go up
				++curLane;
//				System.out.println("moving 1 lane up due to bottom, new lane: " + curLane);

			}
		}
	}

	// Getters & Setters
	public BufferedImage getCurrentFrame() { // Getter to draw current frame
		return animation[aniIndex];
	}

	public int getEnemyHp() {
		return this.hp;
	}

	public void setEnemyHp(int value) {
		this.hp = value;
	}
}
