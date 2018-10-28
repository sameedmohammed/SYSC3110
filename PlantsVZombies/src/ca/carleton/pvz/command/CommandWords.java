package ca.carleton.pvz.command;

/**
 * A class to store all valid commands and evaluate the validity of a given
 * command.
 *
 */
public class CommandWords {

	// a constant array that holds all valid command words
	private static final String[] validCommands = { "quit", "help", "place", "next", "restart" };

	/**
	 * Check whether a given String is a valid command word.
	 *
	 * @param s The String to check.
	 * @return true if it is valid, false otherwise.
	 */
	public boolean isCommand(String s) {
		for (String validCommand : validCommands) {
			if (validCommand.equals(s)) {
				return true;
			}
		}
		// if we get here, the string was not found in the commands
		return false;
	}

	/**
	 * Gets all valid commands.
	 *
	 * @return String of valid commands.
	 */
	public String getCommandList() {
		String list = "";
		for (String command : validCommands) {
			list = String.join(" ", list, command);
		}
		return list;
	}
}
