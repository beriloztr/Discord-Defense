package Entity;

import java.awt.Rectangle;

public interface CollisionChecker {
	public boolean checkCollision(Character other);

	public Rectangle getBound();
}
