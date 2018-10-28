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
	 * Returns this level's label.
	 * 
	 * @return A String representing this level.
	 */
	@Override
	public String toString() {
		return getLevelName() + "\nYou start out with 500 sun points!\n";
	}
}
