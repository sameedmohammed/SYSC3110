package ca.carleton.pvz;

import ca.carleton.pvz.command.Input;
import ca.carleton.pvz.level.LevelOne;

/**
 * Main class for Plants vs Zombies game
 * 
 */
public class PlantsVZombies {
	private World gameWorld;
	private Input parser;
	
	public PlantsVZombies() {
		parser = new Input();
		gameWorld = new World();
		gameWorld.addLevelToWorld(new LevelOne());
		
		
	}
	
	
	public World getWorld() {
		return gameWorld;
	}
}
