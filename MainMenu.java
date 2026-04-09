package ui_items;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import GameSystem.Main;
import javax.swing.JLabel;

public class MainMenu extends JPanel implements ImageResizer {
	private static final long serialVersionUID = 1L;

	public static SettingsMenu settingsMenu;
	static public boolean isSettingsMenuActive = false;
	TitlePanel title;

	public int buttonWidth;
	public int buttonHeight;

	private int difference;
	private int buttonX;
	private int buttonY;

	public int transitionAlpha;

	public MainMenu() {
		transitionAlpha = 0;
		//
		buttonWidth = (int) (456 * Main.scalerX);
		buttonHeight = (int) (140 * Main.scalerY);
		difference = (int) (-150 * Main.scalerY);
		buttonY = (int) (Main.HEIGHT);
		buttonX = (int) ((float) (Main.WIDTH - buttonWidth) / 2);
		setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
		//
		settingsMenu = new SettingsMenu();
		//
		JLabel splashScreen = new JLabel(ImageResizer.imageResize("/img/splashscreen.png", Main.WIDTH, Main.HEIGHT));
		splashScreen.setBounds(0, 0, Main.WIDTH, Main.HEIGHT);
		//
		// ImageIcon titleImgIcon = new ImageIcon(titleImg);
		// JLabel title = new JLabel(titleImgIcon);
		// title.setBounds((Main.WIDTH-titleWidth)/2, -20, titleWidth, titleHeight);
		//
		setDoubleBuffered(true);
		title = new TitlePanel();
		// Button creation
		int bw = buttonWidth;
		int bh = buttonHeight;
		Button startButton = new Button(buttonX, buttonY + 3 * difference, bw, bh, "buttons/StartButton", "Start",
				false);
		Button settingsButton = new Button(buttonX, buttonY + 2 * difference, bw, bh, "buttons/SettingsButton",
				"Settings", false);
		Button quitButton = new Button(buttonX, buttonY + difference, bw, bh, "buttons/QuitButton", "Quit", false);
		Button externalButton = new Button(0, 0, 25, 25, "buttons/StartButton", "External", false);

		/////////////////////////////////////
		setLayout(null);
		add(externalButton);
		add(startButton);
		add(settingsButton);
		add(quitButton);
		add(title);
		add(splashScreen);
	}

	public void settingsMenu() {
		isSettingsMenuActive = true;
		add(settingsMenu);
		setComponentZOrder(settingsMenu, 0);

	}

	public void removeSettingsMenu() {
		Main.window.removeMouseListener(Main.mouseListener);
		isSettingsMenuActive = false;
		remove(settingsMenu);
		Main.window.revalidate();
		Main.window.repaint();
		Main.window.requestFocus();
	}

	@Override
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		Graphics2D g2 = (Graphics2D) g;
		if (Main.isTransitioning) {
			g2.setColor(new Color(0, 0, 0, transitionAlpha));
			g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
			transitionAlpha = transitionAlpha + 3;
			if (transitionAlpha >= 255)
				transitionAlpha = 255;
		}
	}
}
