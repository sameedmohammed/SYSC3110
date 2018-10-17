package ca.carleton.pvz;

import ca.carleton.pvz.command.Command;
import ca.carleton.pvz.command.Parser;
import ca.carleton.pvz.command.Presets;
import ca.carleton.pvz.level.LevelOne;

/**
 * Main class for Plants vs Zombies game
 * 
 */
public class PlantsVZombies {
	private World gameWorld;
	private Parser parser;
	
	public PlantsVZombies() {
		parser = new Parser();
		gameWorld = new World();
		gameWorld.addLevelToWorld(new LevelOne());
		playGame();
		
	}
	
	public void playGame() {
		print(Presets.WELCOME);
		boolean finished = false;
		while(!finished) {
			finished = processCommand(parser.getCommand());
		}
	}
	
	private boolean processCommand(Command command) {
		boolean wantToQuit = false;
		
		if(command.isUnknown()) {
			print(Presets.INVALID);
			return false;
		}
		
		String commandWord = command.getCommandWord();
		if(commandWord.equals("help")) {
			print(Presets.HELP);
		} else if(commandWord.equals("place")) {
			
		} else if(commandWord.equals("quit")) {
			wantToQuit = true;
		}
		
		return wantToQuit;
	}
	
	public World getWorld() {
		return gameWorld;
	}
	
	public void print(String s) {
		System.out.println(s);
	}
}
