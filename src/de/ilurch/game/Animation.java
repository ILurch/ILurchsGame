package de.ilurch.game;

import java.awt.image.BufferedImage;

import de.ilurch.mapping.Sprite;

public class Animation {

	private Sprite sprite;
	private int start;
	private int stop;
	private int currentImage;

	public Animation(Sprite sprite) {
		this.sprite = sprite;
		sprite.generateSprites();
	}

	public BufferedImage getBufferedImage() {
		return sprite.getImages().get(currentImage);
	}

	public void setBoundaryValues(int start, int stop) {
		this.start = start;
		this.stop = stop;
	}

	public void nextImage() {
		if (currentImage > stop)
			currentImage = start;
		else if (currentImage < start)
			currentImage = start;
		else
			currentImage += 1;
	}

	public int getStart() {
		return start;
	}
	
	public int getStop() {
		return stop;
	}

	public void jumpTo(int jump) {
		this.currentImage = jump;
	}
}
