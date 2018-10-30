package ca.carleton.pvz.command;

/**
 * A constant class for storing preset console outputs.
 * 
 */
public final class Presets {

	public static final String WELCOME = "Welcome to Plants vs. Zombies (text-based)!\n"
			+ "Type \"help\" if you need help.\n\nLevel 1\nYou start out with 500 sun points!\n";

	public static final String INVALID = "\nInvalid command. Type \"help\" if you need help.\n";

	public static final String HELP = "\nYour valid command words are:" + Parser.getCommands()
			+ "\nYou can type \"help\" followed by (a space and) a valid command (other than \"help\") to learn how to use the command.\n";

	public static final String GAME_OVER = "Game over! You failed to protect your home from the zombies :(\n"
			+ "Please type \"restart\" if you wish to try again.\n";

	public static final String INVALID_PLANT_TYPE = "\nInvalid plant type.\n";

	public static final String NOT_ENOUGH_SUNPOINTS = "\nYou do not have enough sun points to purchase the ";

	public static final String PLANTTYPE_COOLDOWN = " cooldown in effect.\n";

	public static final String PLACE_WHAT = "\nPlace what?\n";

	public static final String PLACE_WHERE = "\nPlace where?\n";

	public static final String ZOMBIES_SPAWNING = "\nZombies are spawning!";

	public static final String WAVE_COMPLETE = "\nWave complete! Type \"next wave\" to progress to the next wave of zombies.\n";

	public static final String NEXT_WHAT = "\nNext what? (\"next\" should be followed by \"turn\" to advance to the next game state.\n";

	public static final String RESTART_HELP = "\nStarts a new game.\n";

	public static final String NEXT_HELP = "\nType \"next turn\" to advance the game to the next turn.\n";

	public static final String PLACE_HELP = "\nPlaces the specified type of plant at the specified coordinates. Takes three args in the format:"
			+ "\n\"place <plant type> <x-coordinate> <y-coordinate>\"\nValid plant types are \"sunflower\", which cost 50 sun points, "
			+ "and \"peashooter\", which cost 100 sun points.\nCoordinates must be ints and start at (0, 0).\n";

	public static final String QUIT_HELP = "\nQuits the game (terminates the app).\n";

	public static final String PLACE_ON_ZOMBIE = "\nYou cannot place anything on top of a zombie!\n";

	public static final String INVALID_POINT = "\nSorry, the point you entered does not exist on the current level.\n";

}
