package de.ilurch.mapping;

import java.awt.Graphics;

import de.ilurch.game.Game;

public class World {

	private Player player;
	private Room room;
	private RoomManager rooms;

	public World(RoomManager rooms) {
		this.rooms = rooms;
		this.room = rooms.getRooms().get(0);
		this.player = new Player(Game.width / 2, Game.height / 2);
		room.fill(Material.STONE, 0);
		room.fill(Material.STONE, 1);
	}

	public void update(float tslf) {
		room.update();
		player.update(tslf, room);
	}

	public void draw(Graphics g) {
		int x = (Game.width - room.getImage().getWidth()) / 2;
		int y = (Game.height - room.getImage().getHeight()) / 2;

		g.drawImage(room.getImage(), x, y, null);
		player.draw(g);

	}

	public Player getPlayer() {
		return player;
	}
}