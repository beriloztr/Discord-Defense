package Entity;

import ui_items.Lanes;

public class Bullet extends Character {
	private final int bulletspeed = 10;
	public static double bulletSpeedMultiplayer = 1;
	public static int bulletDamage = 1;

	public Bullet(double x, double y) {
		super(x, y, (int) 50, Lanes.laneHeight);
		this.hp = 1; // dies if touches sth immediately
	}

	@Override
	public void update() {
		this.x += bulletspeed * bulletSpeedMultiplayer;
		// Enemy hit by bullet
		for (Enemy e : Enemy.enemyList) {
			if (this.checkCollision(e)) {
				System.out.println("Enemy hit by bullet! took " + Bullet.bulletDamage + " damage");
				e.setEnemyHp(e.getEnemyHp() - Bullet.bulletDamage); // Enemy takes damage (3/2/1 hits till death)
				this.hp -= 1; // Bullet lost its only hp (destroyed)
			}
		}
	}

}
