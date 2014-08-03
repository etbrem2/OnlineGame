package Entity;
import java.awt.Graphics;
import java.util.ArrayList;
import Display.Animation;
import Menu.Game;

public class Player {

	int x, y, speed = 10, sprintSpeed = 15;
	int maxJumpSpeed = 30, speedY, gravity = 3;

	public boolean up, right, left, shift;
	boolean jumping, touchingGround;

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

	public Player(int xx, int yy) {
		addAnims();
		x = xx;
		y = yy;
	}
	

	public void addAnims() {
		// There should only be one walk animation for both left and right
		// and a boolean named faceingRight
		// It is possible to mirror an image by setting the width to negative:
		// g.drawImage(image,x + image.getWidth(),y,
		// -image.getWidth(),image.getHeight(),null);
		animations = new ArrayList<Animation>();

		Animation stand = new Animation(100);
		stand.addFrame("/stickman1.png");

		Animation walkRight = new Animation(100);
		for (int i = 2; i < 6; i++)
			walkRight.addFrame("/stickman" + i + "R.png");

		Animation walkLeft = new Animation(100);
		for (int i = 2; i < 6; i++)
			walkLeft.addFrame("/stickman" + i + "L.png");

		Animation jumpRight = new Animation(100);
		jumpRight.addFrame("/SMjumpR.png");

		Animation jumpLeft = new Animation(100);
		jumpLeft.addFrame("/SMjumpL.png");

		Animation jumpUp = new Animation(100);
		jumpUp.addFrame("/stickmanJump.png");

		Animation sprintRight = new Animation(100);
		for (int i = 1; i < 6; i++)
			sprintRight.addFrame("/stickman" + i + "SprintR.png");

		Animation sprintLeft = new Animation(100);
		for (int i = 1; i < 6; i++)
			sprintLeft.addFrame("/stickman" + i + "SprintL.png");

		animations.add(stand);
		animations.add(walkRight);
		animations.add(walkLeft);
		animations.add(jumpRight);
		animations.add(jumpLeft);
		animations.add(jumpUp);
		animations.add(sprintRight);
		animations.add(sprintLeft);
	}

	public void update() {
		checkCollision();
		decideAnim();
		move();

		animations.get(currentAnim).update();

	}

	public void checkCollision() {
		if (y > Game.window.getHeight()
				- animations.get(currentAnim).getImage().getHeight()) {

			y = Game.window.getHeight()
					- animations.get(currentAnim).getImage().getHeight();
			speedY = 0;
		}
		touchingGround = (y == Game.window.getHeight()
				- animations.get(currentAnim).getImage().getHeight());
	}

	public void move() {

		if (jumping) {
			if (right)
				x += speed;
			if (left)
				x -= speed;

			speedY -= gravity;

			y -= speedY;

		} else {
			if (right) {
				if (shift && touchingGround)
					x += sprintSpeed;
				else
					x += speed;
			}
			if (left) {
				if (shift && touchingGround)
					x -= sprintSpeed;
				else
					x -= speed;
			}
			if (!touchingGround) {
				speedY += gravity;

				if (y + speedY < Game.window.getHeight()
						- animations.get(currentAnim).getImage().getHeight())
					y += speedY;
				else
					y = Game.window.getHeight()
							- animations.get(currentAnim).getImage()
									.getHeight();
			}
		}

		if (touchingGround)
			speedY = 0;
		if (speedY == 0)
			jumping = false;
	}

	public void decideAnim() {
		int lastAnim = currentAnim;
		if (right) {
			if (shift)
				currentAnim = sprintRight;
			else
				currentAnim = walkRight;
		}
		if (left) {
			if (shift)
				currentAnim = sprintLeft;
			else
				currentAnim = walkLeft;
		}

		if (right && left)
			currentAnim = stand;
		if (!left && !right)
			currentAnim = stand;

		if (up && touchingGround) {
			jumping = true;
			speedY = maxJumpSpeed;
			touchingGround = false;
		}

		if (jumping || !touchingGround) {
			currentAnim = jumpUp;
			if (right && !left)
				currentAnim = jumpRight;
			if (left && !right)
				currentAnim = jumpLeft;
		}
		if(lastAnim != currentAnim)
			animations.get(currentAnim).start();
	}

	public void draw(Graphics g) {
		g.drawImage(animations.get(currentAnim).getImage(), x, y, null);
	}
}
