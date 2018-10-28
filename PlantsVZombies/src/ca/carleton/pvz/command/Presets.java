package ca.carleton.pvz.command;

/**
 * A constant class for storing preset console outputs.
 */
public final class Presets {

	public static final String WELCOME = "Welcome to Plants vs. Zombies (text-based)!\n"
			+ "Type \"help\" if you need help.\n" + "You currently have 500 sunpoints.\n ";

	public static final String INVALID = "You entered an invalid command. Type \"help\" for valid commands.\n";

	public static final String HELP = "Your valid command words are:\n" + Parser.getCommands()
			+ "\n(You can type \"help\" followed by a valid command to learn how to use the command.)\n";

}
