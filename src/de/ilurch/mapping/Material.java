package de.ilurch.mapping;

import java.awt.image.BufferedImage;

import de.ilurch.game.Sound;

public enum Material {

	AIR(0), GRASS(1), DIRT(2), SNOW(3), STONE(4, Sound.WALK_STONE), WATER(5), LAVA(6);

	private static Sprite sprite;
	private Sound walkSound;
	private int id;

	private Material(int id) {
		this(id, null);
	}

	private Material(int id, Sound sound) {
		this.id = id;
		this.walkSound = sound;
	}

	public Sound getWalkSound() {
		return walkSound;
	}
	
	public BufferedImage getBufferedImage() {
		if (sprite == null) {
			sprite = new Sprite("assets/img/materialsprite.png", 50, 50, 6, 6);
			sprite.generateSprites();
		}
		return sprite.getImages().get(id);
	}

	public static Material getById(int id) {
		for (Material mat : Material.values()) {
			if (mat.id == id)
				return mat;
		}
		return null;
	}

	public int getId() {
		return id;
	}

}
