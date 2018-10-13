package ca.carleton.pvz.level;

import java.awt.Dimension;

public class Level {
	private String levelName;
	private Dimension dimension;
	
	public Level(String name, int width, int height) {
		levelName = name;
		dimension = new Dimension(width, height);
	}
	
	public Dimension getDimension() {
		return dimension;
	}
}
