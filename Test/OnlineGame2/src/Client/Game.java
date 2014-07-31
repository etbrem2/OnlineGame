package Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame {

	Game window;

	boolean up, right, down, left;
	boolean running = false;
	boolean update = true;

	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	String ip = "127.0.0.1";
	int port = 45682;

	String username = "";
	static BufferedImage image;

	

	public Game() throws Exception {
		super("Move the square");

		window = this;

		setVisible(true);
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		init();

		final Thread getInfo = new Thread(new Runnable() {
			public void run() {
				running = true;
				while (running) {
					try {
						image = ImageIO.read(socket.getInputStream());
						
						repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});

		final Thread send = new Thread(new Runnable() {
			public void run() {
				while (running)
					if (update)
						try {
							String buttons = "";

							if (up)
								buttons += "up/";
							if (right)
								buttons += "right/";
							if (down)
								buttons += "down/";
							if (left)
								buttons += "left/";

							out.writeObject(buttons);
							out.flush();

							update = false;
						} catch (IOException e) {
							e.printStackTrace();
						}
			}
		});
		
		Thread gameLoop = new Thread(new Runnable() {
			public void run() {

				try {
					setup();

					out.writeObject(username);
					out.flush();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				getInfo.start();
				send.start();
			}

		});

		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					up = true;
					break;
				case KeyEvent.VK_RIGHT:
					right = true;
					break;
				case KeyEvent.VK_DOWN:
					down = true;
					break;
				case KeyEvent.VK_LEFT:
					left = true;
					break;
				}
				update = true;
			}

			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					up = false;
					break;
				case KeyEvent.VK_RIGHT:
					right = false;
					break;
				case KeyEvent.VK_DOWN:
					down = false;
					break;
				case KeyEvent.VK_LEFT:
					left = false;
					break;
				}
				update = true;
			}
		});

		gameLoop.start();
	}

	public void init() throws Exception {

		running = true;

		createBufferStrategy(3);
	}

	public void paint(Graphics g1) {

		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		

		if(image!=null)
			g.drawImage(image,0,0,null);
		else if(window!=null){
			g.setColor(Color.black);
			g.drawString("Please wait..", window.getWidth()/2, window.getHeight()/2);
		}

		g.dispose();

		bs.show();

	}

	public void move() {
		if (!window.hasFocus()) {
			right = false;
			up = false;
			down = false;
			left = false;

			update = true;
		}
	}

	private void setup() throws Exception {

		socket = new Socket(InetAddress.getByName(ip), port);

		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		while (username.length() == 0) {
			username = JOptionPane.showInputDialog("Enter username");
		}
	}

	public static void main(String[] args) throws Exception {
		Game temp = new Game();
	}

}
