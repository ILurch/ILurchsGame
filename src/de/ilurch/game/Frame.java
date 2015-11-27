package de.ilurch.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import de.ilurch.mapping.RoomManager;
import de.ilurch.mapping.World;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private BufferStrategy buffer;
	private World world;
	private RoomManager rooms;
	
	public Frame() {
		super("Game");
		this.setSize(Game.width, Game.height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setVisible(true);
		
		try {
			this.rooms = new RoomManager(Game.class);
			this.rooms.loadMaps();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		this.world = new World(rooms);
		this.addKeyListener(new Keyboard());
		
	}

	void createBuffer() {
		createBufferStrategy(2);
		buffer = getBufferStrategy();
	}

	public void repaint() {
		Graphics g = buffer.getDrawGraphics();
		draw(g);
		g.dispose();
		buffer.show();
	}

	void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.width, Game.height);
		world.draw(g);
	}

	public void update(float tslf) {
		world.update(tslf);
	}

}
