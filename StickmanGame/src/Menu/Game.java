package Menu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import Entity.Player;

public class Game extends JFrame {
	public static Game window;

	Player player;
	static final int height = 700;
	static final int width = 800;

	boolean running, cleared = false;
	BufferedImage clear;

	public Game() {
		super("Samuel's Game");

		window = this;
		player = new Player(100, 300);

		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					player.up = true;
					break;
				case KeyEvent.VK_LEFT:
					player.left = true;
					break;
				case KeyEvent.VK_RIGHT:
					player.right = true;
					break;
				case KeyEvent.VK_SHIFT:
					player.shift = true;
					break;
				}
			}

			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					player.up = false;
					break;
				case KeyEvent.VK_LEFT:
					player.left = false;
					break;
				case KeyEvent.VK_RIGHT:
					player.right = false;
					break;
				case KeyEvent.VK_SHIFT:
					player.shift = false;
					break;
				}
			}
		});

		setVisible(true);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Thread gameLoop = new Thread(new Runnable() {
			public void run() {
				running = true;

				clear = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);

				Graphics g = clear.getGraphics();

				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);

				while (running) {
					update();
					repaint();

					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		gameLoop.start();
	}

	public void update() {
		player.update();
	}

	public void paint(Graphics g) {
		// clear is a white BufferedImage
		// g.drawImage(image,x,y,width,height,null) draws the image to fill the
		//  rectangle (x,y,width,height)

		g.drawImage(clear, 0, 0, window.getWidth(), window.getHeight(), null);

		player.draw(g);
	}

	public static void main(String[] args) {
		new Game();
	}

}
