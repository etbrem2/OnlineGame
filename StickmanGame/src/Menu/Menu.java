package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Menu extends JFrame {

	static Menu window;
	String[] options = { "Solo", "Multiplayer", "Exit game" };
	int solo = 0, multiplayer = 1, exit = 2;
	int selected = 0;
	boolean picked = false;
	boolean toggle, updated = true;

	BufferedImage bg;

	public Menu() {
		super("Menu");

		window = this;

		addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						selected--;
						if (selected < 0)
							selected = options.length - 1;
						break;
					case KeyEvent.VK_DOWN:
						selected++;
						if (selected == options.length)
							selected = 0;
						break;
					case KeyEvent.VK_ENTER:
						if (selected == exit)
							System.exit(0);
						if (selected == solo) {
							Thread start = new Thread(new Runnable(){
								public void run() {
									try{
										//Thread.sleep(500);
									}catch(Exception e){}
									new Game();
									window.dispose();
								}
							});
							start.start();
						}
						picked = !picked;
						break;
				}
				updated = true;
			}

			public void keyReleased(KeyEvent e) {}
		});

		Thread refresh = new Thread(new Runnable() {

			public void run() {
				while (true) {
					if (updated || picked)
						repaint();

					try {
						if (picked)
							Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		refresh.start();

		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void paint(Graphics g) {
		int spacing = 30;

		if (window != null) {
			g.setColor(Color.darkGray);
			g.fillRect(0, 0, window.getWidth(), window.getHeight());

			g.setFont(new Font("Arial", Font.BOLD, 16));

			for (int i = 0; i < options.length; i++) {
				if (i == selected) {
					if (!picked)
						g.setColor(Color.RED);
					else {

						toggle = !toggle;
						if (toggle)
							g.setColor(Color.RED);
						else
							g.setColor(Color.white);

					}
				} else
					g.setColor(Color.YELLOW);

				int fontSize = g.getFont().getSize();

				g.drawString(options[i],
				        window.getWidth() / 2
				                - (options[i].length() * fontSize) / 4,
				        window.getHeight() / 3 + i * spacing);
			}
		}

		int fontSize = g.getFont().getSize();

		int y = window.getHeight() / 3 + selected * spacing - 5;

		int x = window.getWidth() / 2 - (options[selected].length() * fontSize)
		        / 4;

		g.setColor(Color.BLACK);
		g.drawLine(0, y, window.getWidth(), y);

		g.drawLine(x, 0, x, window.getHeight());

		updated = false;
	}

	public static void main(String[] args) {
		new Menu();
	}

}
