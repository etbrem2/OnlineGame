package Server;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Enemy {

	BufferedImage pic;
	int speed = 2, x = 400, y = 400;
	int health = 100;

	public Enemy() throws IOException {
		Thread move = new Thread(new Runnable() {

			public void run() {
				while (health > 0) {
					Connection player = closest(Server.connections);

					if (player != null) {
						if (player.x > x)
							x += speed;
						if (player.x < x)
							x -= speed;
						if (player.y > y)
							y += speed;
						if (player.y < y)
							y -= speed;
					}
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		move.start();
	}

	public Connection closest(ArrayList<Connection> players) {
		if (players.size() == 0)
			return null;

		Connection closest = players.get(0);

		if (players.size() > 1) {
			int min = Math.abs(closest.x - x) + Math.abs(closest.y - y);
			
			for (int i = 1; i < players.size(); i++) {
				Connection temp = players.get(i);
				
				int closeness = Math.abs(temp.x - x) + Math.abs(temp.y - y);

				if (closeness < min) {
					min = closeness;
					closest = temp;
				}
			}
		}
		return closest;
	}
}
