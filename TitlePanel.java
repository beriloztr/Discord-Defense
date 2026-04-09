package ui_items;

import javax.swing.*;

import GameSystem.Main;

import java.awt.*;

public class TitlePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public int titleWidth; // 700
	public int titleHeight; // 400
	//
	private float titleStartWidth;
	private float titleEndWidth;
	private float titleStartHeight;
	private float titleEndHeight;

	private Timer timer;
	private double time = 0;

	ImageIcon titleRaw = new ImageIcon(getClass().getResource("/img/title.png"));

	public TitlePanel() {

		//
		titleWidth = (int) (1050 * Main.scalerX);
		titleHeight = (int) (600 * Main.scalerY);
		titleStartWidth = titleWidth;
		titleEndWidth = titleWidth + 70;

		titleStartHeight = titleHeight;
		titleEndHeight = titleHeight + 40;

		setOpaque(false);
		System.out.println("Size: " + titleEndWidth);

		setBounds(0, -30, (int) titleEndWidth + 500, (int) titleEndHeight);

		timer = new javax.swing.Timer(25, e -> {
			updateAnimation();
			repaint();
		});
		timer.start();
	}

	public void updateAnimation() {
		time += 0.07;

		double sineCurve = (Math.sin(time) + 1) / 2;

		titleWidth = (int) lerp(titleStartWidth, titleEndWidth, findT(sineCurve));
		titleHeight = (int) lerp(titleStartHeight, titleEndHeight, findT(sineCurve));
		if (titleWidth % 2 != 0)
			titleWidth++;
		if (titleHeight % 2 != 0)
			titleHeight++;
	}

	private double lerp(float n1, float n2, double t) {
		return n1 + t * (n2 - n1);
	}

	private double findT(double t) {
		return 4 * t * (1 - t);
		// return 1 - Math.abs(2*t-1);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int x = (int) ((Main.WIDTH - titleWidth) / 2);
		int y = 0;
		if (titleRaw == null)
			System.out.print("error");
		g2.drawImage(titleRaw.getImage(), x, y, titleWidth, titleHeight, null);

	}

	@Override
	public void removeNotify() {
		super.removeNotify();
		if (timer != null && timer.isRunning()) {
			timer.stop(); // Kills the "Zombie" timer
		}
	}

}