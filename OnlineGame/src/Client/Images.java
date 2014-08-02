package Client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import Server.type;

public class Images {

	static BufferedImage redSquare;
	static BufferedImage greenSquare;

	public Images() {
		try {
			redSquare = ImageIO.read(getClass().getResourceAsStream(
			        "/Square/redSquare.png"));
			greenSquare = ImageIO.read(getClass().getResourceAsStream(
			        "/Square/greenSquare.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage imageFromID(int num) {
		if (num == type.player)
			return redSquare;

		if (num == type.enemy)
			return greenSquare;

		return redSquare;
	}
}
