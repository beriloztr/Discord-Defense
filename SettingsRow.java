package ui_items;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import GameSystem.Main;

public class SettingsRow extends JPanel implements ImageResizer {
	private static final long serialVersionUID = 1L;
	//
	private int y;
	private int height;
	//
	private int textBarX;
	private int textBarW;
	private int textBarH;
	private int spacing;
	JLabel resolutionText;
	ImageIcon resTextImgIcon /* = new ImageIcon("img/settings/resoulutionText.png") */;

//
	public int optionWidth;
	public int optionHeight;
	Button option1B;
	Button option2B;

//
	public SettingsRow(ImageIcon textBar, String option1, String option2, String type1, String type2, int order) {
		this.spacing = (int) (10 * Main.scalerY);
		this.y = (int) (200 * Main.scalerY);
		this.height = (int) (500 * Main.scalerY);
		//
		this.textBarW = (int) (400 * Main.scalerX);
		this.textBarH = (int) (132 * Main.scalerY);
		this.textBarX = (int) (150 * Main.scalerX);
		//
		this.resolutionText = new JLabel(ImageResizer.imageResize(textBar, textBarW, textBarH));
		//
		this.optionWidth = (int) (300 * Main.scalerX);
		this.optionHeight = (int) (100 * Main.scalerY);
		this.option1B = new Button(2 * spacing + textBarW + textBarX, (textBarH - optionHeight) / 2, optionWidth,
				optionHeight, option1, type1, true);
		this.option2B = new Button(3 * spacing + textBarW + textBarX + optionWidth, (textBarH - optionHeight) / 2,
				optionWidth, optionHeight, option2, type2, true);
		// this.setLayout(new GridLayout(1,3,0,0));
		this.setLayout(null);
		this.setOpaque(false);
		this.setBounds(0, y * order, SettingsMenu.width, height);
		//
		resolutionText.setBounds(textBarX, 0, textBarW, textBarH);
		//
		this.add(resolutionText);
		this.add(option1B);
		this.add(option2B);
	}

	public static void changeResolution(int w, int h) {
		if (Main.isFullScreen == true)
			return;
		Main.WIDTH = w;
		Main.HEIGHT = h;
		Main.updateScreen();
	}

	public static void setFullscreen(boolean bool) {
		if (bool && Main.isFullScreen != bool) {
			Main.isFullScreen = bool;
			Main.window.dispose();
			Main.window.setUndecorated(true);
			Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
			Main.updateToFullscreen();
		} else {
			Main.isFullScreen = bool;
			Main.window.dispose();
			Main.window.setUndecorated(false);
			Main.window.setExtendedState(JFrame.NORMAL);
			Main.window.setSize(Main.WIDTH, Main.HEIGHT);
			Main.updateScreen();
		}
	}
}
