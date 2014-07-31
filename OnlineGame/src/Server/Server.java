package Server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import Client.Images;

public class Server {

	static ArrayList<Connection> connections;
	static ServerSocket server;
	static String data, lastData = "";
	static Enemy enemy;
	static Images images;

	public static void main(String[] args) throws Exception {
		server = new ServerSocket(45682, 5);
		connections = new ArrayList<Connection>();
		images = new Images();
		enemy = new Enemy();

		Thread sendToAll = new Thread(new Runnable() {
			public void run() {
				while (true) {
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

	public static void sendToAll() {
		data = "";

		for (int i = 0; i < connections.size(); i++) {
			Connection c = connections.get(i);
			data += c.user + "/" + type.player + "/" + c.x + "/" + c.y + "\n";
		}

		data += "Enemy/" + type.enemy + "/" + enemy.x + "/" + enemy.y + "\n";

		if (!lastData.equals(data)) {
			for (int i = 0; i < connections.size(); i++) {
				final Connection c = connections.get(i);
				try {
					c.out.writeObject(data);
					c.out.flush();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						c.online = false;
						c.socket.close();
						connections.remove(c);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
			lastData = data;
		}
	}
}
