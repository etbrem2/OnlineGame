package Server;

import java.awt.Color;
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

	
	
	Thread update = new Thread(new Runnable(){
		public void run() {
			while(online){
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

			if (data.contains("up"))
				up = true;
			else
				up = false;
			if (data.contains("down"))
				down = true;
			else
				down = false;
			if (data.contains("left"))
				left = true;
			else
				left = false;
			if (data.contains("right"))
				right = true;
			else
				right = false;
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

		if (x + pic.getWidth() > Server.maxWidth)
			x = Server.maxWidth - pic.getWidth();
		if (y + pic.getHeight() > Server.maxHeight)
			y = Server.maxHeight - pic.getHeight();
	}
	public void drawPlayer(Graphics g){
		g.drawImage(pic,x,y,null);
	}
	public void drawName(Graphics g){
		g.setColor(Color.black);
		g.drawString(user, x, y-5);
	}
}
