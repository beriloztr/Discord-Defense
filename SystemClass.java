package ExternalGUI;

import java.util.ArrayList;

import GameSystem.Items;

public class SystemClass {
	public static ArrayList<String> items = new ArrayList<String>();

	public static boolean add(int id) {
		if (items.size() == 5)
			return false;
		items.add(id + "");
		return true;
	}

	public static boolean delete(int id) {
		if (search(id)) {
			items.remove(id + "");
			return true;
		}
		return false;
	}

	public static boolean search(int id) {
		for (String i : items)
			if (id == Integer.parseInt(i))
				return true;
		return false;
	}

	public static Items display(int id) {
		for (String i : items)
			if (Integer.parseInt(i) == id && search(id)) {
				return new Items(id, -9);
			}
		return null;
	}

	public static Items[] display() {
		Items[] temp = new Items[items.size()];
		int count = 0;
		for (String i : items)
			temp[count++] = new Items(Integer.parseInt(i), -9);
		return temp;
	}

}
