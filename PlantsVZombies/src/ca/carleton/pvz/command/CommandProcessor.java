package ca.carleton.pvz.command;

import java.awt.Point;

import ca.carleton.pvz.World;
import ca.carleton.pvz.plant.PeaShooter;
import ca.carleton.pvz.plant.Plant;
import ca.carleton.pvz.plant.Sunflower;

public class CommandProcessor {

	private Parser parser;
	private World gameWorld;

	public CommandProcessor(World gameWorld) {
		parser = new Parser();
		this.gameWorld = gameWorld;
	}

	public boolean processCommand() {

		boolean wantToQuit = false;
		Command command = parser.getCommand();

		if (command.isUnknown()) {
			print(Presets.INVALID);
			return false;
		}

		String commandWord = command.getCommandWord();

		if (commandWord.equals("help")) {
			print(Presets.HELP);

		} else if (commandWord.equals("place")) {
			processPlace(command);

		} else if (commandWord.equals("quit")) {
			wantToQuit = true;
		}

		return wantToQuit;

	}

	private void processPlace(Command command) {

		if (!command.hasSecondWord()) {
			print("Place what?");
			return;

		} else if (!command.hasThirdWord()) {
			print("Place where?");
			return;

		} else if (!command.hasFourthWord()) {
			print("Place where?");
			return;
		}

		String plantType = command.getSecondWord();
		int xPos = Integer.parseInt(command.getThirdWord());
		int yPos = Integer.parseInt(command.getFourthWord());
		Plant plantToPlace;

		if (plantType.equalsIgnoreCase("peashooter")) {
			plantToPlace = new PeaShooter();

		} else if (plantType.equalsIgnoreCase("sunflower")) {
			plantToPlace = new Sunflower();

		} else {
			plantToPlace = null;
			print("Invalid plant type.");
		}

		if (plantToPlace != null) {
			gameWorld.getCurrentLevel().placePlant(plantToPlace, new Point(xPos, yPos));
			print(gameWorld.getCurrentLevel().toString());
		}
		
	}

	public void print(String s) {
		System.out.println(s);
	}

}
