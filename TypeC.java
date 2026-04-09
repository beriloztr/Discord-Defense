package Entity;

import java.awt.image.BufferedImage;

public class TypeC extends Enemy {

	// TYPE C: slow speed, high lane change
	// Slime Enemy
	public TypeC(double x, double y, double speed, String laneChangeRate, int curLane, BufferedImage sprite) {
		super(x, y, speed, laneChangeRate, curLane, sprite, 5);
	}

	@Override
	public void update() {
		this.x -= speed * enemySpeedMultiplayer; // move left by each frame
		cycleSubSheets(); // update aniTick (timer)
		changeLane(); // randomly change lanes
	}
}
