package utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GetResource {

	private static HashMap<String, ImageIcon> icons = new HashMap<>();

	public static ImageIcon getIcon(String name) {
		if (icons.containsKey(name))
			return icons.get(name);

		try {
			BufferedImage img = ImageIO.read(GetResource.class.getResource("/resources/" + name));
			ImageIcon res = new ImageIcon(img);
			icons.put(name, res);
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}