import java.util.ArrayList;


public class Player {

	int x,y,speed;
	boolean up,right,left,shift;
	
	ArrayList<Animation> animations;
	int currentAnim = 0;
	
	
	final int stand = 0;
	final int walkRight = 1;
	final int walkLeft = 2;
	final int jumpRight = 3;
	final int jumpLeft = 4;
	final int jumpUp = 5;
	final int sprintRight = 6;
	final int sprintLeft = 7;
	
	public Player(){
		addAnims();
	}
	
	public void addAnims(){
		// There should only be one walk animation for both left and right
		// and a boolean named faceingRight
		// It is possible to mirror an image by setting the width to negative:
		// g.drawImage(image,x + image.getWidth(),y, -image.getWidth(),image.getHeight(),null);
		animations = new ArrayList<Animation>();
		
		Animation stand = new Animation(100);
		stand.addFrame("stickman1.png");
		
		Animation walkRight = new Animation(100);
		for(int i=2;i<6;i++)
			walkRight.addFrame("stickman"+i+"R.png");
		
		Animation walkLeft = new Animation(100);
		for(int i=2;i<6;i++)
			walkLeft.addFrame("stickman"+i+"L.png");
		
		Animation jumpRight = new Animation(100);
		jumpRight.addFrame("SMjumpR.png");
		
		Animation jumpLeft = new Animation(100);
		jumpLeft.addFrame("SMjumpR.png");
		
		Animation jumpUp = new Animation(100);
		jumpUp.addFrame("stickmanJump.png");
		
		Animation sprintRight = new Animation(100);
		for(int i=1;i<6;i++)
			sprintRight.addFrame("stickman"+i+"SprintR.png");
		
		Animation sprintLeft = new Animation(100);
		for(int i=1;i<6;i++)
			sprintLeft.addFrame("stickman"+i+"SprintL.png");
		
		animations.add(stand);
		animations.add(walkRight);
		animations.add(walkLeft);
		animations.add(jumpRight);
		animations.add(jumpLeft);
		animations.add(jumpUp);
		animations.add(sprintRight);
		animations.add(sprintLeft);
	}

	public void update(){
		checkCollision();
		move();
		decideAnim();
		
		animations.get(currentAnim).update();
	}
	
	public void checkCollision(){
		
	}
	public void move(){
		
	}
	public void decideAnim(){
		if(up){
			
		}
		if(right){
			if(shift)
				currentAnim = sprintRight;
			else currentAnim = walkRight;
		}
		if(left){
			if(shift)
				currentAnim = sprintLeft;
			else currentAnim = walkLeft;
		}
	}
}