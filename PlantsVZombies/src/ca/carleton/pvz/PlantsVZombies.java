package ca.carleton.pvz;

import java.awt.Point;

import ca.carleton.pvz.command.Command;
import ca.carleton.pvz.command.Parser;
import ca.carleton.pvz.command.Presets;
import ca.carleton.pvz.level.LevelOne;
import ca.carleton.pvz.plant.PeaShooter;
import ca.carleton.pvz.plant.Plant;
import ca.carleton.pvz.plant.Sunflower;

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
	
	public World getWorld() {
		return gameWorld;
	}
	
	public void print(String s) {
		System.out.println(s);
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
			processPlace(command);
			
		} else if(commandWord.equals("quit")) {
			wantToQuit = true;
		}
		return wantToQuit;
	}

	
	private void processPlace(Command command) {
		if(!command.hasSecondWord()) {
			print("Place what?");
			return;
		} else if(!command.hasThirdWord()) {
			print("Place where?");
			return;
		} else if(!command.hasFourthWord()) {
			print("Place where?");
			return;
		}
		
		String plantType = command.getSecondWord();
		Plant plantToPlace;
		int xPos = Integer.parseInt(command.getThirdWord());
		int yPos = Integer.parseInt(command.getFourthWord());
		if(plantType.equalsIgnoreCase("peashooter")) {
			plantToPlace = new PeaShooter();
			
		} else if(plantType.equalsIgnoreCase("sunflower")) {
			plantToPlace = new Sunflower();
			
		} else {
			plantToPlace = null;
			print("Invalid plant type.");
		}
		
		
		if(plantToPlace != null) {
			gameWorld.getCurrentLevel().placePlant(plantToPlace, new Point(xPos, yPos));
			print(gameWorld.getCurrentLevel().toString());
		}
	
	}
}
