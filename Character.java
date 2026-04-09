package Entity;

import java.awt.Rectangle;

public abstract class Character implements CollisionChecker {

	// declarations
	protected double x;
	protected double y;
	protected int hp;
	protected int width;
	protected int height;

	// Constructor
	public Character(double x, double y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	// Abstract methods
	public abstract void update();

	// Methods
	public Rectangle getBound() {
		return new Rectangle((int) x, (int) y, width, height); // Rectangle holds int
	}

	public boolean checkCollision(Character other) {
		// getBound() returns => Rectangle for 'this' character
		// other.getBound() returns => Rectangle for the 'other' character
		// .intersects() returns => true if the two rectangles overlap
		return this.getBound().intersects(other.getBound());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getW() {
		return width;
	}

	public double getH() {
		return height;
	}

	public boolean isDead() {
		if (hp <= 0)
			return true;

		return false;
	}
}
