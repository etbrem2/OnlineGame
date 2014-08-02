package Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Entity {

	int x, y;
	int type = -1;
	String user;
	BufferedImage image;

	public Entity(String playerData) {

		String[] info = playerData.split("/");

		user = info[0];
		type = Integer.parseInt(info[1]);
		x = Integer.parseInt(info[2]);
		y = Integer.parseInt(info[3]);

		try {
			image = Game.images.imageFromID(type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawEntity(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	public void drawName(Graphics g) {
		g.setColor(Color.black);
		if (user != null)
			g.drawString(user, x, y - 5);
	}
}
