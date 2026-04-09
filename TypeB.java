package Entity;

import java.awt.image.BufferedImage;

public class TypeB extends Enemy {

	// TYPE B: Medium speed, Medium lane change
	// Metronome Enemy
	public TypeB(double x, double y, double speed, String laneChangeRate, int curLane, BufferedImage sprite) {
		super(x, y, speed, laneChangeRate, curLane, sprite, 4);
		this.hp = 2;
	}

	@Override
	public void update() {
		this.x -= speed * enemySpeedMultiplayer; // move left by each frame
		cycleSubSheets(); // update aniTick (timer)
		changeLane(); // randomly change lanes
	}
}
