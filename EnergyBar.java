package ui_items;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameSystem.Main;

public class EnergyBar extends JPanel implements ImageResizer {
	private static final long serialVersionUID = 1L;
	public static int maxEnergy = 22;
	public static int curEnergy = -1;
	//
	private float imageRatioW = 1280 / 1900f; // 1900 Original Width of img
	private float imageRatioH = 120 / 320f; // 320 Original Height of img

	//
	public static int width;
	public static int height;
	//
	private JLabel[] bars;
	private int barW;
	private int barH;
	private int barX;
	private int barY;
	private int barDifValue;
	//

	public EnergyBar() {
		curEnergy = -1;
		width = (int) (1280 * Main.scalerX);
		height = (int) (120 * Main.scalerY);
		//
		bars = new JLabel[maxEnergy];
		barW = (int) (60 * imageRatioW * Main.scalerX);
		barH = (int) (189 * imageRatioH * Main.scalerY);
		barX = (int) (75 * imageRatioW * Main.scalerX);
		barY = (int) (70 * imageRatioH * Main.scalerY);
		barDifValue = (int) (24 * imageRatioW * Main.scalerX);
		//
		this.setLayout(null);
		this.setOpaque(false);
		this.setBounds(0, Main.HEIGHT - height, width, height); // 2175,365
		//
		JLabel energyBarLabel = new JLabel(ImageResizer.imageResize("/img/energyBar.png", width, height));
		//
		energyBarLabel.setBounds(0, 0, width, height);
		for (int i = 0; i < maxEnergy; i++) {
			bars[i] = new JLabel();
			bars[i].setVisible(false);
			bars[i].setBackground(getColor(i));
			bars[i].setOpaque(true);
			bars[i].setBounds(barX + (i * (width - 2 * barX + barDifValue) / maxEnergy), barY, barW, barH);
			add(bars[i]);
		}
		add(energyBarLabel);
	}

	public void updateBars() {
		if (curEnergy >= maxEnergy - 1)
			curEnergy = maxEnergy - 1;
		else if (curEnergy < -1)
			curEnergy = -1;
		for (int i = 0; i <= curEnergy; i++) {
			bars[i].setVisible(true);
		}
		for (int i = curEnergy + 1; i < maxEnergy; i++) {
			bars[i].setVisible(false);
		}
	}

	public static void increaseEnergy(int num) {
		curEnergy += num;
	}

	private Color getColor(int i) {
		switch (i) {
		case 0:
			return new Color(200, 31, 56);
		case 1:
			return new Color(222, 33, 58);
		case 2:
			return new Color(250, 38, 57);
		case 3:
			return new Color(253, 42, 48);
		case 4:
			return new Color(253, 87, 48);
		case 5:
			return new Color(252, 113, 53);
		case 6:
			return new Color(252, 153, 52);
		case 7:
			return new Color(250, 178, 48);
		case 8:
			return new Color(251, 216, 50);
		case 9:
			return new Color(253, 242, 62);
		case 10:
			return new Color(251, 251, 58);
		case 11:
			return new Color(178, 251, 76);
		case 12:
			return new Color(158, 250, 79);
		case 13:
			return new Color(91, 239, 84);
		case 14:
			return new Color(61, 235, 83);
		case 15:
			return new Color(56, 241, 112);
		case 16:
			return new Color(60, 245, 177);
		case 17:
			return new Color(59, 250, 251);
		case 18:
			return new Color(47, 215, 248);
		case 19:
			return new Color(46, 181, 247);
		case 20:
			return new Color(52, 153, 250);
		case 21:
			return new Color(62, 130, 253);
		default:
			System.out.println("\nError: Invalid Energy Bar color index!");
			return new Color(0, 0, 0);
		}
	}

}
