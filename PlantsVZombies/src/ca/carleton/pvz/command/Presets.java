package ca.carleton.pvz.command;

public final class Presets {
	public static final String WELCOME = "Welcome to Plants VS Zombies (text-based)! \n"
										 + "Type 'help' if you need help. \n \n"
										 + "You currently have 500 sunpoints. \n ";
	
	public static final String INVALID = "You entered an invalid command. Type 'help' for valid commands. \n";
	
	public static final String HELP = "Your valid command words are: \n" + Parser.getCommands();
}
