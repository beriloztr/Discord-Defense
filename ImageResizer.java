package ui_items;

import java.awt.Image;

import javax.swing.ImageIcon;

public interface ImageResizer {
	public static ImageIcon imageResize(String path, int w, int h) {
		ImageIcon mainImgRaw = new ImageIcon(ImageResizer.class.getResource(path));
		Image mainImg = mainImgRaw.getImage().getScaledInstance(w, h, Image.SCALE_REPLICATE);
		return new ImageIcon(mainImg);
	}

	public static ImageIcon imageResize(ImageIcon path, int w, int h) {
		Image mainImg = path.getImage().getScaledInstance(w, h, Image.SCALE_REPLICATE);
		return new ImageIcon(mainImg);
	}
}
