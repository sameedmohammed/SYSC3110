package ca.carleton.pvz;

import java.util.Stack;

import ca.carleton.pvz.level.Level;

/**
 * A class to store the levels in the game.
 *
 */
public class World {

	private Stack<Level> levels;

	// unspent sun points get carried over from level to level
	private int sunPoints;

	/**
	 * Constructs a new game world.
	 */
	public World() {
		levels = new Stack<>();
		sunPoints = 500;
	}

	/**
	 * Adds a level to the stack.
	 *
	 * @param level The level to be added to the stack.
	 */
	public void addLevel(Level level) {
		if (level != null) {
			levels.add(level);
		}
	}

	/**
	 * Gets the current level.
	 *
	 * @return The current level.
	 */
	public Level getCurrentLevel() {
		return levels.peek();
	}

	public void updateCurrentLevel(Level level) {
		levels.set(0, level);
	}
}
