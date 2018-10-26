package ca.carleton.pvz;

import ca.carleton.pvz.command.CommandProcessor;
import ca.carleton.pvz.command.Presets;
import ca.carleton.pvz.level.LevelOne;

/**
 * The Plants vs. Zombies game.
 */
public class PlantsVZombies {

	private World gameWorld;
	private CommandProcessor commandProcessor;

	public PlantsVZombies() {
		gameWorld = new World();
		commandProcessor = new CommandProcessor(gameWorld);
		gameWorld.addLevelToWorld(new LevelOne());
		playGame();
	}

	public void playGame() {
		print(Presets.WELCOME);
		boolean finished = false;
		while (!finished) {
			finished = commandProcessor.processCommand();
		}
	}

	public World getWorld() {
		return gameWorld;
	}

	public void print(String s) {
		System.out.println(s);
	}

}
