package ca.carleton.pvz;

import ca.carleton.pvz.command.CommandProcessor;
import ca.carleton.pvz.command.Presets;

import ca.carleton.pvz.level.LevelOne;

/**
 * This class manages the flow of the game.
 * 
 */
public class PlantsVZombies {

	private World gameWorld; // stores the levels to be played
	private CommandProcessor commandProcessor; // processes user input

	/**
	 * Constructs a new game to be played.
	 */
	public PlantsVZombies() {
		gameWorld = new World();
		commandProcessor = new CommandProcessor(gameWorld);
		gameWorld.addLevelToWorld(new LevelOne());
		playGame();
	}

	/**
	 * Repeatedly prompts the user for input until "quit" is entered.
	 */
	public void playGame() {
		print(Presets.WELCOME);
		print(gameWorld.getCurrentLevel().toString());
		boolean finished = false;
		while (!finished) {
			finished = commandProcessor.processCommand();
		}
	}

	/**
	 * A shorthand for print statements.
	 * 
	 * @param s The String to be printed.
	 */
	public void print(String s) {
		System.out.println(s);
	}
}
