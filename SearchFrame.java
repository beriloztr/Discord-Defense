package ExternalGUI;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import GameSystem.Items;
import GameSystem.Main;
import ui_items.ImageResizer;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class SearchFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private static Items[] items;

	public SearchFrame(JFrame frame) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 637, 354);
		System.out.println(Main.scalerX);
		items = new Items[3];
		for (int i = 0; i < 3; i++)
			items[i] = new Items(i, -9);
		JLabel photoLabel = new JLabel();
		photoLabel.setBounds(57, 109, 141, 127);
		getContentPane().add(photoLabel);

		getContentPane().setLayout(null);

		JLabel itemPhotoLabel = new JLabel("Item Photo:");
		itemPhotoLabel.setBounds(57, 86, 141, 13);
		getContentPane().add(itemPhotoLabel);

		JLabel descLabel = new JLabel("Description:");
		descLabel.setBounds(271, 86, 76, 13);
		getContentPane().add(descLabel);

		JTextArea descTF = new JTextArea();
		descTF.setLineWrap(true);
		descTF.setBounds(264, 109, 284, 152);
		getContentPane().add(descTF);

		Integer[] ids = { 0, 1, 2 };
		JComboBox<Integer> ItemCB = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>(ids));
		ItemCB.setBounds(68, 25, 112, 21);
		getContentPane().add(ItemCB);
		ItemCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = ItemCB.getSelectedIndex();
				if (items[index] == null)
					return;
				switch (index) {
				case 0:
					textField.setText("Ice Cube");
					descTF.setText("Slows down enemies dramatically for a short duration");
					break;
				case 1:
					textField.setText("Angry Notes");
					descTF.setText("Increases notes damage for a short duration");
					break;
				case 2:
					textField.setText("Energy Capsule");
					descTF.setText("Adds energy to the energy bar");
					break;
				default:
					break;
				}

				Image img = ImageResizer.imageResize(items[index].getImage(), 128, 128).getImage();
				if (img != null) {
					photoLabel.setIcon(new javax.swing.ImageIcon(img));
				}
			}
		});

		textField = new JTextField();
		textField.setBounds(334, 25, 214, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		JLabel idLabel = new JLabel("ID:");
		idLabel.setBounds(32, 29, 23, 13);
		getContentPane().add(idLabel);

		JLabel itemNameLabel = new JLabel("Item Name:");
		itemNameLabel.setBounds(262, 29, 146, 13);
		getContentPane().add(itemNameLabel);

		JButton closeB = new JButton("Close");
		closeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				frame.setVisible(true);
			}
		});
		textField.setText("Ice Cube");
		descTF.setText("Slows down enemies dramatically for a short duration");
		Image img = ImageResizer.imageResize(items[0].getImage(), 128, 128).getImage();
		if (img != null) {
			photoLabel.setIcon(new javax.swing.ImageIcon(img));
		}
		closeB.setBounds(91, 262, 89, 23);
		getContentPane().add(closeB);

	}
}
