package ca.carleton.pvz.level;

/**
 * The first level in the game.
 *
 */
public class LevelOne extends Level {

	/**
	 * Constructs the first level.
	 */
	public LevelOne() {
		super("Level 1", 5, 5);
	}

	/**
	 * Prints the level label.
	 * 
	 * @return A String representing the level.
	 */
	public String toString() {
		return "Level 1";
	}
}
