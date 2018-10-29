package ca.carleton.pvz.command;

/**
 * A constant class for storing preset console outputs.
 * 
 */
public final class Presets {

	public static final String WELCOME = "Welcome to Plants vs. Zombies (text-based)!\n"
			+ "Type \"help\" if you need help.\n\nLevel 1\nYou start out with 500 sun points!\n";

	public static final String INVALID = "Invalid command \n" + "Type 'help' if you need help.";

	public static final String HELP = "Your valid command words are:" + Parser.getCommands()
			+ "\nYou can type \"help\" followed by (a space and) a valid command (other than \"help\") to learn how to use the command.\n";
	
	public static final String GAME_OVER = "Game over! You failed to protect your home from the zombies :( \n" +
										  "Please type \\\"restart\\\" if you wish to try again. \n";
	
	public static final String INVALID_PLANT_TYPE = "Invalid plant type.";
	
	public static final String NOT_ENOUGH_SUNPOINTS = "You do not have enough sun points to purchase the ";
	
	public static final String PLANTTYPE_COOLDOWN = " cooldown in effect.";
	
	public static final String PLACE_WHAT = "Place what?";
	
	public static final String PLACE_WHERE = "Place where?";
	
	public static final String ZOMBIES_SPAWNING = "Zombies are spawning!";
	
	public static final String WAVE_COMPLETE = "Wave complete! Type 'next wave' to progress to the next wave of zombies";
	
	public static final String NEXT_WHAT = "Next what? (\"next\" should be followed by \"turn\" to advance to the next game state,"
			+ "\nor \"wave\" to bring on the next wave of zombies.)";
	
	public static final String RESTART_HELP = "Starts a new game.";
	
	public static final String NEXT_HELP = "Type \"next turn\" to advance the game to the next turn,"
			+ "or \"next wave\" to bring on the next wave of zombies.\n";
	
	public static final String PLACE_HELP = "Places the specified type of plant at the specified coordinates. Takes three args in the format:"
			+ "\n\"place <plant type> <x-coordinate> <y-coordinate>\"\n";
	
	public static final String QUIT_HELP = "Quits the game (terminates the app).";
	
	public static final String PLACE_ON_ZOMBIE = "You cannot place anything on top of a zombie!";
	
	public static final String INVALID_POINT = "Sorry, the point you entered does not exist on the current level.";
}
