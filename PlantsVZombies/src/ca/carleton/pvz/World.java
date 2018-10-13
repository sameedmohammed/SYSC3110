package ca.carleton.pvz;

import java.util.ArrayList;

import ca.carleton.pvz.level.Level;
import ca.carleton.pvz.level.LevelOne;

public class World {
	private ArrayList<Level> levels;
	private Level currentLevel;
	
	public World(int numLevels) {
		levels = new ArrayList<Level>();
		addLevelsToWorld(numLevels);
	}
	
	/**
	 * Initialize levels ArrayList with level objects.
	 * @param numLevels number of levels for the current world to have
	 */
	private void addLevelsToWorld(int numLevels) {
		if(numLevels < 1) throw new IllegalArgumentException("World must have at least one level");
		
		if(numLevels < 2) levels.add(new LevelOne());
	}
	
	/**
	 * Get the currently active level
	 * @return
	 */
	public Level getCurrentLevel() {
		return currentLevel;
	}
	
}
