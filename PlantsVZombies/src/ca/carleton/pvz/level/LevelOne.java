package ca.carleton.pvz.level;

/**
 * The first level in the game.
 *
 */
public class LevelOne extends Level {

	/**
	 * Constructs level 1.
	 */
	public LevelOne() {
		super("Level 1", 5, 5);
	}

	/**
	 * Returns the level label.
	 * 
	 * @return A String representing the level.
	 */
	public String toString() {
		return "Level 1";
	}
}
