package de.ilurch.mapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import de.ilurch.game.Game;
import de.ilurch.mapping.Room.Tile;

public class RoomManager {

	private final File savePath;
	private ArrayList<Room> maps = new ArrayList<>();

	public RoomManager(Class<Game> clazz) throws URISyntaxException {
		savePath = new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "/maps");
	}

	public void loadMaps() {
		for (File file : savePath.listFiles()) {
			maps.add(loadMap(file.getName()));
		}
	}
	
	public ArrayList<Room> getRooms() {
		return maps;
	}

	public void saveMap(Room map) {
		try {
			File file = new File(savePath, map.getId() + ".map");
			savePath.mkdirs();
			PrintStream stream;

			file.createNewFile();
			stream = new PrintStream(file);
			for (int[] to2DIntegerArray : map.getIntLevel(0)) {
				stream.println(Arrays.toString(to2DIntegerArray).replace("[", "").replace("]", ""));
			}
			stream.flush();
			stream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Room loadMap(String filename) {
		String[] lines = null;
		int[][] array = new int[20][20];
		try {
			lines = readLines(new File(savePath, filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < lines.length; i++) {
			String[] line = lines[i].split(", ");
			for (int j = 0; j < line.length; j++) {
				array[i][j] = Integer.valueOf(line[j]);
			}
		}
		int i = Integer.valueOf(filename.replace(".map", ""));
		Room r = new Room(i);
		return readMapFromIntegerArray(r, array);
	}

	private String[] readLines(File file) throws IOException {
		FileReader fileReader = new FileReader(file);
		ArrayList<String> lines;
		try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			lines = new ArrayList<>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines.toArray(new String[lines.size()]);
	}

	private Room readMapFromIntegerArray(Room room, int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				room.getLevel(0)[i][j] = new Tile(Material.getById(array[i][j]));
			}
		}
		return room;
	}

}
