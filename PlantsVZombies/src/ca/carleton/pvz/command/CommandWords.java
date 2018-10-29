package ca.carleton.pvz.command;

public class CommandWords {
	// a constant array that holds all valid command words
    private static final String[] validCommands = { "quit", "help", "place", "next" };
    
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }
    
    /**
     * Check whether a given String is a valid command word. 
     * @param s The String to check
     * @return true if it is valid, false otherwise
     */
    public boolean isCommand(String s)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(s))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    
    /**
     * Gets all valid commands.
     * @return Returns string of valid commands.
     */
    public String getCommandList() 
    {
        String list = "";
        for(String command : validCommands) {
            list = String.join(" ", list, command);
        }
        return list;
    }
}
