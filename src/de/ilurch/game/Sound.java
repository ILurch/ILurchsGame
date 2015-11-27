package de.ilurch.game;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public enum Sound {

	BURP("burp.wav", 0), WALK_STONE("walk.wav", 1);

	private Clip clip;
	private final String filename;
	private final int id;

	private Sound(String filename, int id) {
		this.filename = filename;
		this.id = id;
		try {
			AudioInputStream sound = Game.loadAudio("assets/sound/" + filename);
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (IOException | LineUnavailableException ex) {
			Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void play(boolean save) {
		if (save && isRunning()) {
			return;
		}
		clip.setFramePosition(0);
		clip.start();

	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}

	public boolean isRunning() {
		return clip.isRunning();
	}

	public String getFileName() {
		return filename;
	}

	public int getID() {
		return id;
	}

	public static void stopAll() {
		for (Sound s : Sound.values()) {
			s.stop();
		}
	}

}
