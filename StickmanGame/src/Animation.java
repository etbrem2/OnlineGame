import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Animation {
	ArrayList<BufferedImage> frames;
	int time;
	int currentFrame = 0;
	long startTime;
	public boolean playedOnce = false;

	public Animation(int time) {
		// time is how long a frame will display in ms
		
		this.time = time;
		frames = new ArrayList<BufferedImage>();
	}

	public void addFrame(String loc) {
		// add an image to this animation
		try {
			BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(
					loc));
			frames.add(temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		startTime = System.currentTimeMillis();
		currentFrame = 0;
		playedOnce = false;
	}
	
	public void ok(){
		
	}

	public void update() {
		if (System.currentTimeMillis() - startTime > time) {
			currentFrame++;
			startTime = System.currentTimeMillis();
			
			if (currentFrame == frames.size()) {
				currentFrame = 0;
				playedOnce = true;
			}
		}
	}

	public BufferedImage getImage() {
		return frames.get(currentFrame);
	}
}
