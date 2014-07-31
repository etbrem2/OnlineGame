package Server;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

import Client.Images;

public class Connection {

	String user;
	BufferedImage pic;

	ObjectInputStream in;
	ObjectOutputStream out;
	Socket socket;
	boolean online = true;

	int x, y, speed = 5;
	boolean up, right, down, left;

	int maxWidth = 600, maxHeight = 600;

	Thread update = new Thread(new Runnable() {
		public void run() {
			while (online) {
				move();

				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	});

	public Connection(Socket c) {
		socket = c;

		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			user = (String) in.readObject();

			pic = Server.images.imageFromID(type.player);

			Thread getInfo = new Thread(new Runnable() {
				public void run() {
					while (online) {
						getInfo();
					}
				}
			});

			getInfo.start();
			update.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getInfo() {
		try {
			String data = (String) in.readObject();

			String dir = getValues(data,"dir");

			up = dir.contains("up");
			down = dir.contains("down");
			right = dir.contains("right");
			left = dir.contains("left");

			String screenSize = getValues(data,"screenSize");
			
			String[]sizes = screenSize.split("/");
			
			maxWidth = Integer.parseInt(sizes[0]);
			maxHeight = Integer.parseInt(sizes[1]);
		} catch (Exception e) {
			e.printStackTrace();
			online = false;
			try {
				socket.close();
				Server.connections.remove(this);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public String getValues(String data, String name) {
		String open = "<" + name + ">";
		String close = "</" + name + ">";
		return data.substring(data.indexOf(open) + open.length(), data.indexOf(close));
	}

	public void move() {
		if (right)
			x += speed;
		if (left)
			x -= speed;
		if (down)
			y += speed;
		if (up)
			y -= speed;

		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;

		if (x + pic.getWidth() > maxWidth)
			x = maxWidth - pic.getWidth();
		if (y + pic.getHeight() > maxHeight)
			y = maxHeight - pic.getHeight();
	}

	public void drawPlayer(Graphics g) {
		g.drawImage(pic, 0, 0, null);
	}

	public void drawName(Graphics g) {
		g.drawString(user, 0, y - 5);
	}
}
