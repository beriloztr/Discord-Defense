package ui_items;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import GameSystem.Main;

public class SettingsMenu extends JPanel implements ImageResizer {
	private static final long serialVersionUID = 1L;
	public static int width = (int) (Main.WIDTH * 0.8);
	public int height = (int) (Main.HEIGHT * 0.8);

	SettingsRow resSettings;
	SettingsRow displaySettings;
	//
	ImageIcon settingsUIRaw = new ImageIcon(getClass().getResource("/img/settingsUI.png"));
	Image settingsUIImg;
	JLabel settingsUILabel;

	//
	public SettingsMenu() {
		width = (int) (Main.WIDTH * 0.8);
		this.height = (int) (Main.HEIGHT * 0.8);
		this.setLayout(null);
		this.setBounds((Main.WIDTH - width) / 2, (Main.HEIGHT - height) / 2, width, height);
		this.setOpaque(false);

		// Resolution row start ->
		ImageIcon resTextBar = new ImageIcon(getClass().getResource("/img/settings/resoulutionText.png"));
		String resOption1Path = "settings/1920";
		String resOption2Path = "settings/1280";
		String resType1 = "1920";
		String resType2 = "1280";
		int resOrder = 1;
		this.resSettings = new SettingsRow(resTextBar, resOption1Path, resOption2Path, resType1, resType2, resOrder);
		// Resolution row end <-

		// Display row start ->
		ImageIcon displayTextBar = new ImageIcon(getClass().getResource("/img/settings/displayText.png"));
		String displayOption1Path = "settings/windowed";
		String displayOption2Path = "settings/fullscreen";
		String displayType1 = "windowed";
		String displayType2 = "fullscreen";
		int displayOrder = 2;
		this.displaySettings = new SettingsRow(displayTextBar, displayOption1Path, displayOption2Path, displayType1,
				displayType2, displayOrder);
		// Display row end <-

		// this handles the backgorudn img
		this.settingsUILabel = new JLabel(ImageResizer.imageResize(settingsUIRaw, width, height));
		this.settingsUILabel.setOpaque(false);
		this.settingsUILabel.setBounds(0, 0, width, height);
		this.settingsUILabel.setVisible(true);
		//
		this.add(settingsUILabel);
		this.add(resSettings);
		this.add(displaySettings);
		this.setComponentZOrder(settingsUILabel, 1);
		this.setComponentZOrder(resSettings, 0);
		this.setComponentZOrder(displaySettings, 0);
		this.setVisible(true);
	}

}
