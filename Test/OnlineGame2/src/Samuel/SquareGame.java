package Samuel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class SquareGame extends JFrame implements KeyListener
{
	BufferedImage image;
	boolean running = true;
	boolean up = false;
	boolean right = false;
	boolean down = false;
	boolean left = false;
	boolean gameOver = false;
	BufferedImage [] square = new BufferedImage[7];
	BufferedImage [] smallSquare = new BufferedImage[7];
	int X = 0;
	int Y = 20;
	int x = (int) (Math.random()*770);
	int y = (int) (Math.random()*550+20);		
	int squareColor = 0;
	int score = 0;
	int smallSquareColor = 0;
	
	
	
	
	Runnable refresh = new Runnable(){

		public void run() 
		{
			smallSquareColor = (int) (Math.random()*7);
			while(running)
			{
				repaint();
				
				if(X >= x-104 && X <= x+30 )
				{
					if(Y > y-104)
					{
						if(Y < y+30)
						{
							if(squareColor == smallSquareColor)
							{
							x = (int) (Math.random()*770);
							y = (int) (Math.random()*550+20);
							smallSquareColor = (int) (Math.random()*7);
							score++;
							}
							else gameOver = true;
						}
					}
				}
				
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	};
	Thread run;
	
	
	
	public SquareGame()
	{
		super("Square Game");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800, 600);
		
		init();
		
		addKeyListener(this);
		
		run = new Thread(refresh);
		
		run.start();
		
	}
	
	
	
	
	public void paint(Graphics g1)
	{
		g1.setColor(Color.white);
		g1.fillRect(0, 0, 800, 600);
		if(up == true && Y>20)
			Y = Y-4;
		if(right == true && X<700)
			X = X+4;
		if(down == true && Y<500)
			Y = Y+4;
		if(left == true && X>0)
			X = X-4;
		
		
		g1.drawImage(smallSquare[smallSquareColor],x,y,null);
		g1.drawImage(square[squareColor],X,Y,null);
		g1.setColor(Color.black);
		g1.drawString("Score: "+score,375,40);
		
		if(gameOver)
			{
			g1.setColor(Color.white);
			g1.fillRect(0, 0, 800, 600);
			g1.drawImage(image,-50,0,null);
			}
		
	}

	public void init() 
	{
			try {
				square[0] = ImageIO.read(getClass().getResourceAsStream("/square/azureSquare.png"));
				square[1] = ImageIO.read(getClass().getResourceAsStream("/square/blueSquare.png"));
				square[2] = ImageIO.read(getClass().getResourceAsStream("/square/purpleSquare.png"));
				square[3] = ImageIO.read(getClass().getResourceAsStream("/square/redSquare.png"));
				square[4] = ImageIO.read(getClass().getResourceAsStream("/square/orangeSquare.png"));
				square[5] = ImageIO.read(getClass().getResourceAsStream("/square/yellowSquare.png"));
				square[6] = ImageIO.read(getClass().getResourceAsStream("/square/greenSquare.png"));
				smallSquare[0] = ImageIO.read(getClass().getResourceAsStream("/square/smallAzureSquare.png"));
				smallSquare[1] = ImageIO.read(getClass().getResourceAsStream("/square/smallBlueSquare.png"));
				smallSquare[2] = ImageIO.read(getClass().getResourceAsStream("/square/smallPurpleSquare.png"));
				smallSquare[3] = ImageIO.read(getClass().getResourceAsStream("/square/smallRedSquare.png"));
				smallSquare[4] = ImageIO.read(getClass().getResourceAsStream("/square/smallOrangeSquare.png"));
				smallSquare[5] = ImageIO.read(getClass().getResourceAsStream("/square/smallYellowSquare.png"));
				smallSquare[6] = ImageIO.read(getClass().getResourceAsStream("/square/smallgreenSquare.png"));
				image = ImageIO.read(getClass().getResourceAsStream("/square/gameOver.png"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}
	
	
	
	
	
	
	public static void main(String[]args)
	{
		SquareGame window = new SquareGame();
		
	
	}


	
	




	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_UP)
			up = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			right = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			down = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			left = true;
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			squareColor = (squareColor+1)%7;
			
	}




	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_UP)
			up = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			right = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			down = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			left = false;
		
	}







	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}