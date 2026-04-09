package ui_items;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.SwingConstants;

import Entity.Bullet;
import Entity.Enemy;
import GameSystem.GamePanel;
import GameSystem.Items;
import GameSystem.Main;

public class Hotbar extends JPanel implements ImageResizer {
	private static final long serialVersionUID = 1L;
	private float scaler = 1.33f;
	public final int WIDTH = (int) Math.ceil(scaler * 480 * (Main.WIDTH / 1920.0));
	public final int HEIGHT = (int) Math.ceil(scaler * 96 * (Main.HEIGHT / 1080.0));
	public final int BOXSIZE = 96;

	public Items[] inventory = new Items[5];
	public JLabel[] myLabels = new JLabel[5];
	public static float slowTimer = 0;
	public static float damageTimer = 0;

	public void addItem(int id) {
		if (Items.count < 5) {
			System.out.println("Added item: " + id);
			for (int i = 0; i < myLabels.length; i++) {
				if (myLabels[i].getComponentCount() == 0) {
					inventory[i] = new Items(id, i);
					int itemW = inventory[i].getWidth();
					int itemH = inventory[i].getHeight();
					//
					JLabel itemLabel = new JLabel(ImageResizer.imageResize(inventory[i].getImage(), itemW, itemH));
					//
					itemLabel.setBounds(((WIDTH / 5 - itemW)) / 2, (HEIGHT - itemH) / 2, itemW, itemH);
					itemLabel.setOpaque(false);
					itemLabel.setBackground(Color.GRAY);
					itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
					myLabels[i].add(itemLabel);
					break;
				}
			}
		}
	}

	public void useItem(int slot) {
		if (inventory[slot] == null)
			return;
		inventory[slot].use();
		inventory[slot] = null;
		myLabels[slot].removeAll();
	}

	public void reset() {
		for (int i = 0; i < inventory.length; i++)
			useItem(i);
		Items.count = 0;
	}

	public Hotbar() {
		try {
			reset();
			this.setOpaque(false);
			BufferedImage backgroundImg = ImageIO.read(getClass().getResourceAsStream("/img/hotbar.png"));
			setBounds(0, 0, WIDTH, HEIGHT);
			setLayout(new GridLayout(1, 5, 0, 0));
			for (int i = 0; i < myLabels.length; i++) {
				BufferedImage subImage = backgroundImg.getSubimage(i * BOXSIZE, 0, BOXSIZE, BOXSIZE);
				Image scaledSub = subImage.getScaledInstance(WIDTH / myLabels.length, HEIGHT, Image.SCALE_REPLICATE);
				myLabels[i] = new JLabel();
				myLabels[i].setIcon(new ImageIcon(scaledSub));
				add(myLabels[i]);
				myLabels[i].setLayout(null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void applySlow() {
		if (slowTimer > 0) {
			if (GamePanel.oneSecondPassed)
				slowTimer -= 1;
			if (slowTimer <= 0) {
				slowTimer = 0;
				Enemy.enemySpeedMultiplayer = 1;
			}
		}
	}

	private void increaseDamage() {
		if (damageTimer > 0) {
			if (GamePanel.oneSecondPassed)
				damageTimer -= 1;
			if (damageTimer <= 0) {
				damageTimer = 0;
				Bullet.bulletDamage = 1;
			}
		}
	}

	public void update() {
		applySlow();
		increaseDamage();

	}
}
