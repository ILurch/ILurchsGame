package de.ilurch.mapping;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.ilurch.game.Game;

public class Sprite {

	private int width;
	private int height;
	private int columns;
	private int lines;

	private BufferedImage image;
	private List<BufferedImage> images = new ArrayList<>();

	public Sprite(String path, int width, int height, int columns, int lines) {
		this.width = width;
		this.height = height;
		this.columns = columns;
		this.lines = lines;
		image = Game.loadImage(path);
	}

	public void generateSprites() {
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				BufferedImage img = image.getSubimage(j * width, i * height, width, height);
				images.add(img);
			}
		}
	}
	
	public BufferedImage getSprite() {
		return image;
	}

	public List<BufferedImage> getImages() {
		return images;
	}
}