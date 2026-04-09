package ui_items;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.JPanel;
import GameSystem.GamePanel;
import GameSystem.KillCounter;
import GameSystem.Main;

public class GameOver extends JPanel implements ImageResizer {
	private static final long serialVersionUID = 1L;

	// 1. Declare labels here so they can be accessed in setupGameOver()
	JLabel scoreLabel;
	JLabel valTypeA;
	JLabel valTypeB;
	JLabel valTypeC;

	public int buttonWidth, buttonHeight, buttonX, buttonY, transitionAlpha;

	public GameOver() {
		setVisible(true);
		setLayout(null);

		buttonWidth = (int) (456 * Main.scalerX);
		buttonHeight = (int) (140 * Main.scalerY);
		buttonY = (int) (Main.HEIGHT / 1.5);
		buttonX = (int) (Main.WIDTH);

		// Background Image
		JLabel gameOver = new JLabel(ImageResizer.imageResize("/img/gameOver.png", Main.WIDTH, Main.HEIGHT));
		gameOver.setBounds(0, 0, Main.WIDTH, Main.HEIGHT);

		scoreLabel = new JLabel("0");
		valTypeA = new JLabel("0");
		valTypeB = new JLabel("0");
		valTypeC = new JLabel("0");

		Font retroFont = new Font("Monospaced", Font.BOLD, 30);
		scoreLabel.setFont(retroFont);
		scoreLabel.setForeground(Color.WHITE);

		valTypeA.setFont(retroFont);
		valTypeA.setForeground(Color.WHITE);
		valTypeB.setFont(retroFont);
		valTypeB.setForeground(Color.WHITE);
		valTypeC.setFont(retroFont);
		valTypeC.setForeground(Color.WHITE);

		scoreLabel.setBounds(Main.WIDTH / 2 + (int) (100 * Main.scalerX), Main.HEIGHT / 3 + (int) (65 * Main.scalerY),
				400, 50);
		int difference = (int) (105 * Main.scalerY);
		int tableX = Main.WIDTH / 2 + (int) (100 * Main.scalerX);
		int tableY = Main.HEIGHT / 2 + difference;

		valTypeA.setBounds(tableX, tableY, 100, 50);
		valTypeB.setBounds(tableX, tableY + difference, 100, 50);
		valTypeC.setBounds(tableX, tableY + 2 * difference, 100, 50);

		Button menuButton = new Button(0, buttonY, buttonWidth, buttonHeight, "buttons/MenuButton", "Menu", false);
		Button restartButton = new Button(buttonX - buttonWidth, buttonY, buttonWidth, buttonHeight,
				"buttons/RestartButton", "Restart", false);

		add(scoreLabel);
		add(valTypeA);
		add(valTypeB);
		add(valTypeC);
		add(restartButton);
		add(menuButton);
		add(gameOver);
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

	public void setupGameOver() {
		scoreLabel.setText("" + GamePanel.time);
		valTypeA.setText(KillCounter.getSpecificTypeKillCount("TypeA") + "");
		valTypeB.setText(KillCounter.getSpecificTypeKillCount("TypeB") + "");
		valTypeC.setText(KillCounter.getSpecificTypeKillCount("TypeC") + "");

		this.setVisible(true);
	}
}