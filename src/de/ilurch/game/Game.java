package de.ilurch.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Game {

	public static int width = (int) (800*1.5);
	public static int height = (int) (600*1.5);
	public static int fps = 0;
	
	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.createBuffer();
		
		long lastFrame = System.currentTimeMillis();
		long diff = 0;
		while (true) {

			long thisFrame = System.currentTimeMillis();
			float timeSinceLastFrame = (float) ((thisFrame - lastFrame) / 1000.0);
			diff = System.currentTimeMillis() - lastFrame;
			lastFrame = thisFrame;
			if (diff != 0) {
				fps = (int) ((long) 1000 / diff);
			}
			frame.update(timeSinceLastFrame);
			frame.repaint();
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Game.class.getClassLoader().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static AudioInputStream loadAudio(String path) {
		try {
			return AudioSystem.getAudioInputStream(Game.class.getClassLoader().getResourceAsStream(path));
		} catch (UnsupportedAudioFileException | IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}