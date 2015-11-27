package de.ilurch.mapping;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import de.ilurch.game.Animation;
import de.ilurch.game.Game;
import de.ilurch.game.Keyboard;

public class Player {

	private float x;
	private float y;

	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;
	
	private final int maxSpeed = 150;
	private final int acc = 1000;
	private final int nextImage = 20;

	private int aniCount = 0;
	private int soundCount = 0;
	private int width = 50;
	private int height = 50;
	private boolean moving;

	private Animation animation;
	private BufferedImage shadow = Game.loadImage("assets/img/shadow.png");

	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		this.animation = new Animation(new Sprite("assets/img/char.png", 50, 50, 13, 16));
		animation.setBoundaryValues(0, 13 * 6);
	}

	public void draw(Graphics g) {
		g.drawImage(shadow, (int) x, (int) y, null);
		g.drawImage(animation.getBufferedImage(), (int) x, (int) y, null);
	}

	public void update(float tslf, Room room) {

		float moveAmountU = (float) (speedUp * tslf);
		float moveAmountD = (float) (speedDown * tslf);
		float moveAmountL = (float) (speedLeft * tslf);
		float moveAmountR = (float) (speedRight * tslf);
		int acc = (int) (this.acc * tslf);
		
		if ((speedUp != 0 || speedDown != 0) && (speedLeft != 0 || speedRight != 0)) {
			moveAmountU = moveAmountU * 0.75F;
			moveAmountD = moveAmountD * 0.75F;
			moveAmountL = moveAmountL * 0.75F;
			moveAmountR = moveAmountR * 0.75F;
		}

		// Smooth Up Movement
		if (Keyboard.isKeyPressed(KeyEvent.VK_W)) {
			if (speedUp < maxSpeed) {
				speedUp += acc;
			} else {
				speedUp = maxSpeed;
			}
			moving = true;
			y = y - moveAmountU;
		} else {
			if (speedUp != 0) {
				speedUp -= acc;
				if (speedUp < 0)
					speedUp = 0;
			}
			y -= moveAmountU;

		}

		// Smooth Down Movement
		if (Keyboard.isKeyPressed(KeyEvent.VK_S)) {
			if (speedDown < maxSpeed) {
				speedDown += acc;
			} else {
				speedDown = maxSpeed;
			}
			moving = true;
			y += moveAmountD;
		} else {
			if (speedDown != 0) {
				speedDown -= acc;
				if (speedDown < 0)
					speedDown = 0;
			}
			y += moveAmountD;
		}

		// Smooth Left Movement
		if (Keyboard.isKeyPressed(KeyEvent.VK_A)) {
			if (speedLeft < maxSpeed) {
				speedLeft += acc;
			} else {
				speedLeft = maxSpeed;
			}
			x -= moveAmountL;
			moving = true;
		} else {
			if (speedLeft != 0) {
				speedLeft -= acc;
				if (speedLeft < 0)
					speedLeft = 0;
			}
			x -= moveAmountL;
		}

		// Smooth Right Movement
		if (Keyboard.isKeyPressed(KeyEvent.VK_D)) {
			if (speedRight < maxSpeed) {
				speedRight += acc;
			} else {
				speedRight = maxSpeed;
			}
			moving = true;
			x += moveAmountR;
		} else {
			if (speedRight != 0) {
				speedRight -= acc;
				if (speedRight < 0)
					speedRight = 0;
			}
			x += moveAmountR;
		}
		
		if (!Keyboard.isKeyPressed(KeyEvent.VK_W) && !Keyboard.isKeyPressed(KeyEvent.VK_A) && !Keyboard.isKeyPressed(KeyEvent.VK_S) && !Keyboard.isKeyPressed(KeyEvent.VK_D)) {
			animation.jumpTo(animation.getStart());
			moving = false;
		}
		
		// Animation
		aniCount += 1;
		if (aniCount > nextImage && moving) {
			animation.nextImage();
			aniCount = 0;
		}
		if (moveAmountU > moveAmountD)
			animation.setBoundaryValues(26, 32);
		if (moveAmountD > moveAmountU)
			animation.setBoundaryValues(0, 6);
		if (moveAmountU == 0 && moveAmountD == 0) {
			if (moveAmountR > moveAmountL)
				animation.setBoundaryValues(13, 19);
			if (moveAmountL > moveAmountR)
				animation.setBoundaryValues(39, 45);
		}
		
		//Walksound
		if (moving) {
			soundCount += 1;
			System.out.println(soundCount * tslf);
			if (soundCount * tslf >= 0.4F) {
				int tileX = (int) (x/32);
				int tileY = (int) (y/32);
				Material mat = room.getLevel(0)[tileX][tileY].getMaterial();
				mat.getWalkSound().play(false);
				soundCount = 0;
			}
		} else 
			soundCount = 0;
		
		// Borders
		int xMin = (Game.width - room.getImage().getWidth()) / 2;
		int yMin = (Game.height - room.getImage().getHeight()) / 2;
		int xMax = Game.width - xMin;
		int yMax = Game.height - yMin;

		if (x < xMin)
			x = xMin;
		if (x + width > xMax)
			x = xMax - width;
		if (y < yMin)
			y = yMin;
		if (y + height > yMax)
			y = yMax - height;

	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
