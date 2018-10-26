package ca.carleton.pvz.command;

import java.util.Scanner;

public class Parser {
	private static CommandWords commands = new CommandWords(); 
    private Scanner reader;         
	
    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        reader = new Scanner(System.in);
    }
    
    /**
     * Get the command from the user.
     * 
     * @return The next command from the user
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;
        String word4 = null;
        
        System.out.print("> ");

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                if(tokenizer.hasNext()) {
                	word3 = tokenizer.next();
                	if(tokenizer.hasNext()) {
                    	word4 = tokenizer.next();
                    }
                }
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(commands.isCommand(word1)) {
            return new Command(word1, word2, word3, word4);
        }
        else {
            return new Command(null, word2, word3, word4); 
        }
    }

    /**
     * Print out a list of valid command words.
     */
    public static String getCommands()
    {
        return commands.getCommandList();
    }
}
