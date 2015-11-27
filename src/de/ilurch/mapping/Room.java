package de.ilurch.mapping;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.ilurch.game.Game;

public class Room {

	public final static int MAX_SIZE_X = Game.width / 32;
	public final static int MAX_SIZE_Y = Game.height / 32;
	
	private final int id;
	private List<Tile[][]> levels = new ArrayList<>();
	private BufferedImage image;
	private int xSize;
	private int ySize;
	private boolean updateRequired = true;
	
	public Room(int id, int ySize, int xSize) {
		this.id = id;
		this.ySize = ySize;
		this.xSize = xSize;
		Tile[][] tilelevel0 = new Tile[xSize][ySize];
		Tile[][] tilelevel1 = new Tile[xSize][ySize];
		levels.add(tilelevel0);
		levels.add(tilelevel1);
		fill(Material.STONE, 1);
		fill(Material.AIR, 0);
	}
	
	public Room(int id) {
		this(id, 20, 30);
	}

	public int getId() {
		return id;
	}

	public Tile[][] getLevel(int level) {
		return levels.get(level);
	}

	public void fill(Material mat, int tilelevel) {
		for (int i = 0; i < levels.get(tilelevel).length; i++) {
			for (int j = 0; j < levels.get(tilelevel)[i].length; j++) {
				if (levels.get(tilelevel)[i][j] == null)
					levels.get(tilelevel)[i][j] = new Tile(mat);
				else
					levels.get(tilelevel)[i][j].setMaterial(mat);
			}
		}
		updateRequired = true;
	}

	public void update() {
		if (updateRequired) {
			image = new BufferedImage(32 * xSize, 32 * ySize, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			for (int l = 0; l < levels.size(); l++) {
				for (int i = 0; i < levels.get(l).length; i++) {
					for (int j = 0; j < levels.get(l)[i].length; j++) {
						BufferedImage img = levels.get(l)[i][j].getMaterial().getBufferedImage();
						g.drawImage(img, i * 32, j * 32, 32, 32, null);
					}
				}
			}
			g.dispose();
			updateRequired = false;
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public int[][] getIntLevel(int level) {
		int[][] array = new int[20][20];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				array[i][j] = getLevel(level)[i][j].getMaterial().getId();
			}
		}
		return array;
	}

	public int getYSize() {
		return ySize;
	}

	public int getXSize() {
		return xSize;
	}

	public static final class Tile {

		private Material material;
		private boolean enterable;

		public Tile(Material mat, boolean enterable) {
			this.setMaterial(mat);
			this.setEnterable(enterable);
		}

		public Tile(Material mat) {
			this(mat, true);
		}

		public Material getMaterial() {
			return material;
		}

		public void setMaterial(Material material) {
			this.material = material;
		}

		public boolean isEnterable() {
			return enterable;
		}

		public void setEnterable(boolean enterable) {
			this.enterable = enterable;
		}
	}

}
