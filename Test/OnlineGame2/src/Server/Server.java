package Server;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Client.Images;

public class Server {

	static ArrayList<Connection> connections;
	static ServerSocket server;
	static String data, lastData = "";
	static Enemy enemy;
	static int maxWidth = 1386, maxHeight = 704;
	static Images images;
	static BufferedImage display;
	
	public static void main(String[] args) throws Exception {
		server = new ServerSocket(45682, 5);
		connections = new ArrayList<Connection>();
		
		images = new Images();
		display = new BufferedImage(maxWidth,maxHeight,BufferedImage.TYPE_INT_RGB);
		
		
		Thread sendToAll = new Thread(new Runnable() {
			public void run() {
				while (true)
				{
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (connections.size() > 0)
						sendToAll();
				}
			}
		});

		sendToAll.start();

		while (true) {
			connections.add(new Connection(server.accept()));
		}

	}

	public static void updateImage(){
		Graphics g = display.getGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, maxWidth, maxWidth);
		
		for(int i=0;i<connections.size();i++)
			connections.get(i).drawPlayer(g);

		for(int i=0;i<connections.size();i++)
			connections.get(i).drawName(g);
	}
	
	public static void sendToAll() {
		updateImage();
		
		if(display!=null)
			for(int i=0;i<connections.size();i++)
				try {
					ImageIO.write(display, "PNG", connections.get(i).socket.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
}
