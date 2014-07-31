package Client;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


import Server.type;

public class Images {
	static BufferedImage redSquare;
	
	public Images(){
		try {
			redSquare = ImageIO.read(getClass().getResourceAsStream("/Square/redSquare.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage imageFromID(int num){
		if(num == type.player)
			return redSquare;
		
		return redSquare;
	}
}
