package GameSystem;

import javax.swing.ImageIcon;

import Entity.Bullet;
import Entity.Enemy;
import ui_items.EnergyBar;
import ui_items.Hotbar;

public class Items {
	int id;
	String name;
	static public int count = 0;
	private int width;
	private int height;
	ImageIcon img;

	public Items(int id, int order) {
		if (order == -9)
			count--;
		count++;
		this.id = id;
		switch (id) {
		case 0: {
			this.width = (int) (70 * Main.scalerX);
			this.height = (int) (70 * Main.scalerY);
			img = new ImageIcon(getClass().getResource("/img/items/ice.png"));
			break;
		}
		case 1: {
			img = new ImageIcon(getClass().getResource("/img/items/damage.png"));
			this.width = (int) (128 * Main.scalerX);
			this.height = (int) (128 * Main.scalerY);
			break;
		}
		case 2: {
			img = new ImageIcon(getClass().getResource("/img/items/energy.png"));
			this.width = (int) (113 * 0.6 * Main.scalerX);
			this.height = (int) (174 * 0.6 * Main.scalerY);
			break;
		}
		default:
			System.out.println("Invalid item id!");
			break;
		}
	}

	public void use() {
		switch (id) {
		case 0: {
			Enemy.enemySpeedMultiplayer = 0.1;
			Hotbar.slowTimer = 5.0f;
			break;
		}
		case 1: {
			Bullet.bulletDamage = 3;
			Hotbar.damageTimer = 8.0f;
			break;
		}
		case 2: {
			EnergyBar.increaseEnergy(4);

			break;
		}
		default:
			System.out.println("Invalid item id!");
			break;
		}
		count--;
	}

	public ImageIcon getImage() {
		return img;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
