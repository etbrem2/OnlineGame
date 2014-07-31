package Test;

import Client.Game;
import Server.Server;

public class Test {
	public static void main(final String[] args) {
		Thread server = new Thread(new Runnable() {
			public void run() {
				try {
					Server.main(args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Thread game1 = new Thread(new Runnable(){
			public void run() {
				try {
					Game.main(args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Thread game2 = new Thread(new Runnable(){
			public void run() {
				try {
					Game.main(args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Thread game3 = new Thread(new Runnable(){
			public void run() {
				try {
					Game.main(args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		server.start();
		game1.start();
	//	game2.start();
	}

}
