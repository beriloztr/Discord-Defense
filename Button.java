package ui_items;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import GameSystem.Main;

public class Button extends JButton implements ImageResizer {

	private static final long serialVersionUID = 1L;
	private int posScale;
	private int sizeScale;
	private int width; // 456 for main menu
	private int height;// 140 for main menu
	private int scaledW;
	private int scaledH;
	//
	private boolean on = false;

	ImageIcon hoverImg;
	ImageIcon mainOnImageIcon;

	Button(int x, int y, int w, int h, String img, String type, boolean settingsItem) {
		this.width = w;
		this.height = h;
		this.sizeScale = 4;
		this.posScale = 2;

		int scaledX = x - posScale;
		int scaledY = y - posScale;
		this.scaledW = width + sizeScale;
		this.scaledH = height + sizeScale;
		//
		ImageIcon mainImageIcon = ImageResizer.imageResize("/img/" + img + ".png", width, height);
		//
		//
		this.setIcon(mainImageIcon);
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBounds(x, y, width, height);
		this.setBorder(null); // this removes the border around button
		//
		if (settingsItem) {
			mainOnImageIcon = ImageResizer.imageResize("/img/" + img + "on" + ".png", width, height);
			if (Main.WIDTH == 1920 && type.equals("1920")) {
				setIcon(mainOnImageIcon);
				on = true;
				repaint();
			} else if (Main.WIDTH == 1280 && type.equals("1280")) {
				setIcon(mainOnImageIcon);
				on = true;
				repaint();
			} else if (Main.isFullScreen == false && type.equals("windowed")) {
				setIcon(mainOnImageIcon);
				on = true;
				repaint();
			} else if (Main.isFullScreen == true && type.equals("fullscreen")) {
				setIcon(mainOnImageIcon);
				on = true;
				repaint();
			}
		}
		//
		if (on) {
			hoverImg = ImageResizer.imageResize("/img/" + img + "on" + ".png", scaledW, scaledH);

		} else {
			hoverImg = ImageResizer.imageResize("/img/" + img + ".png", scaledW, scaledH);
		}
		// event handlers //
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if (MainMenu.isSettingsMenuActive && !settingsItem)
					return;
				setIcon(hoverImg);
				setBounds(scaledX, scaledY, scaledW, scaledH);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				if (MainMenu.isSettingsMenuActive && !settingsItem)
					return;
				if (on)
					setIcon(mainOnImageIcon);
				else
					setIcon(mainImageIcon);
				setBounds(x, y, width, height);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				setBorder(null); // this removes the border around button
			}
		});
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Main.isTransitioning)
					return;
				if (MainMenu.isSettingsMenuActive && !settingsItem)
					return;
				if (type.equalsIgnoreCase("Start"))
					Main.startGame();
				else if (type.equalsIgnoreCase("Quit"))
					Main.quitGame();
				else if (type.equalsIgnoreCase("Settings"))
					Main.settings();
				else if (type.equalsIgnoreCase("1920"))
					SettingsRow.changeResolution(1920, 1080);
				else if (type.equalsIgnoreCase("1280"))
					SettingsRow.changeResolution(1280, 720);
				else if (type.equalsIgnoreCase("windowed"))
					SettingsRow.setFullscreen(false);
				else if (type.equalsIgnoreCase("fullscreen"))
					SettingsRow.setFullscreen(true);
				else if (type.equalsIgnoreCase("Resume"))
					Main.openPauseMenu();
				else if (type.equalsIgnoreCase("Menu"))
					Main.goBackToMainMenu();
				else if (type.equalsIgnoreCase("Restart"))
					Main.reStartGame();
				else if (type.equalsIgnoreCase("External"))
					Main.externalFrame.setVisible(true);

			}
		});
	}

}
