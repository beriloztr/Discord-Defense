package ui_items;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JPanel;
import GameSystem.Main;

public class Lanes extends JPanel implements ImageResizer {
	private static final long serialVersionUID = 1L;

	public static int width = (int) (1536 * Main.scalerX);
	public static int height = (int) (896 * Main.scalerY);
	final public static int laneCount = 7;
	public static int laneHeight = (int) ((height / laneCount));
	public static int x = (int) (400 * Main.scalerX);

	public Lanes() {
		width = (int) (1536 * Main.scalerX);
		height = (int) (896 * Main.scalerY);
		laneHeight = (int) ((height / laneCount));
		x = (int) (400 * Main.scalerX);
		//
		setLayout(new GridLayout(7, 1, 0, 0));
		setOpaque(false);
		setBounds(x, 0, width, height);
		//
		Integer[] randLanes = { 0, 1, 2, 3, 4, 5, 6 };
		Collections.shuffle(Arrays.asList(randLanes));
		for (int i = 0; i < 7; i++) {
			JLabel laneLabel = new JLabel(
					ImageResizer.imageResize("/img/lanes/laneNo" + randLanes[i] + ".png", width, laneHeight));
			add(laneLabel);
		}

	}
}
