package ui_items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GameSystem.Main;

public class PauseMenu extends JPanel implements ImageResizer {

	private static final long serialVersionUID = 1L;

	public int buttonWidth;
	public int buttonHeight;

	private int difference;
	private int buttonX;
	private int buttonY;

	public int transitionAlpha;

	public PauseMenu() {
		setLayout(null);
		//
		buttonWidth = (int) (456 * Main.scalerX);
		buttonHeight = (int) (140 * Main.scalerY);
		difference = (int) (-150 * Main.scalerY);
		buttonY = (int) (Main.HEIGHT / 1.5);
		buttonX = (int) ((float) (Main.WIDTH - buttonWidth) / 2);
		int bw = buttonWidth;
		int bh = buttonHeight;
		//
		JLabel pauseScreen = new JLabel(ImageResizer.imageResize("/img/pauseScreen.png", Main.WIDTH, Main.HEIGHT));
		pauseScreen.setBounds(0, 0, Main.WIDTH, Main.HEIGHT);
		//
		Button resumeButton = new Button(buttonX, buttonY + 3 * difference, bw, bh, "buttons/ResumeButton", "Resume",
				false);
		Button restartButton = new Button(buttonX, buttonY + 2 * difference, bw, bh, "buttons/RestartButton", "Restart",
				false);
		Button menuButton = new Button(buttonX, buttonY + difference, bw, bh, "buttons/MenuButton", "Menu", false);
		//
		add(resumeButton);
		add(restartButton);
		add(menuButton);
		add(pauseScreen);
	}

	@Override
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		Graphics2D g2 = (Graphics2D) g;
		if (Main.isTransitioning) {
			g2.setColor(new Color(0, 0, 0, transitionAlpha));
			g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
			transitionAlpha = transitionAlpha + 2;
			if (transitionAlpha >= 255)
				transitionAlpha = 255;
		}
	}

}
