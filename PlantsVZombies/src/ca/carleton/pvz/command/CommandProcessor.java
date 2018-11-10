package ca.carleton.pvz.command;

import java.awt.Point;
import java.util.Random;
import ca.carleton.pvz.PlantsVZombies;
import ca.carleton.pvz.actor.Actor;
import ca.carleton.pvz.actor.PeaShooter;
import ca.carleton.pvz.actor.Sunflower;
import ca.carleton.pvz.actor.Zombie;
import ca.carleton.pvz.level.Wave;

/**
 * Processes the user-inputed command and advances the game accordingly.
 *
 */
public class CommandProcessor {

	private PlantsVZombies game;
	private Parser parser;

	/**
	 * Constructs a CommandProcessor for game state advancement.
	 *
	 * @param game
	 *            The PvZ game whose states will be advanced by this
	 *            CommandProcessor.
	 */
	public CommandProcessor(PlantsVZombies game) {
		this.game = game;
		parser = new Parser();
	}

	/**
	 * Prompts for user input via the parser, then processes the input.
	 *
	 * @return true if the user wants to quit playing, false otherwise.
	 */
	public boolean processCommand() {

		boolean wantToQuit = false;
		Command command = parser.getCommand();

		if (command.isUnknown()) {
			game.print(Presets.INVALID);
			return false;
		}

		String commandWord = command.getCommandWord();

		switch (commandWord) {
		case "help":
			if (command.hasSecondWord()) {
				processHelp(command);
			} else {
				game.print(Presets.HELP);
			}
			break;
		case "place":
			processPlace(command);
			break;
		case "quit":
			System.exit(0);
		case "next":
			processNext(command);
			break;
		case "restart":
			if (command.hasSecondWord()) {
				game.print(Presets.INVALID);
			} else {
				new PlantsVZombies();
			}
			break;
		default:
			break;
		}

		return wantToQuit;

	}

	/**
	 * Tells the user how to use the valid commands.
	 *
	 * @param command
	 *            The given command.
	 */
	private void processHelp(Command command) {

		if (!command.hasThirdWord()) {

			switch (command.getSecondWord()) {
			case "quit":
				game.print(Presets.QUIT_HELP);
				break;
			case "place":
				game.print(Presets.PLACE_HELP);
				break;
			case "next":
				game.print(Presets.NEXT_HELP);
				break;
			case "restart":
				game.print(Presets.RESTART_HELP);
				break;
			default:
				game.print(Presets.INVALID);
				break;
			}

		} else {

			game.print(Presets.INVALID);

		}
	}

	/**
	 * Advances the game to the next state (turn) based on the current state.
	 *
	 * @param command
	 *            The given command.
	 */
	private void processNext(Command command) {

		if (!command.hasSecondWord()) {
			game.print(Presets.NEXT_WHAT);

		} else if (command.getSecondWord().equals("turn")) {

			game.getActionProcessor().processNextTurn();

		} else {
			game.print(Presets.INVALID);
		}

	}

	/**
	 * Processes the placement of a plant.
	 *
	 * @param command
	 *            The given command.
	 */
	private void processPlace(Command command) {

		/*
		 * if (wave.getWaveNumber() >= 3 && waveDefeated) {
		 * game.print("\nCongrats! You finished the first level of Plants vs. Zombies\n"
		 * ); game.print("\nPlease type \"restart\" if you wish to play again.\n");
		 * return; }
		 */

		if (!command.hasSecondWord()) {
			game.print(Presets.PLACE_WHAT);
			return;
		} else if (!command.hasThirdWord()) {
			game.print(Presets.PLACE_WHERE);
			return;
		} else if (!command.hasFourthWord()) {
			game.print(Presets.PLACE_WHERE);
			return;
		}

		int xPos, yPos;
		try {
			xPos = Integer.parseInt(command.getThirdWord());
			yPos = Integer.parseInt(command.getFourthWord());
		} catch (NumberFormatException ex) {
			game.print(Presets.PLACE_WHERE);
			return;
		}

		String plantType = command.getSecondWord();

		if (!game.getWorld().getCurrentLevel().isPointValid(new Point(xPos, yPos))) {
			game.print(Presets.INVALID_POINT);
			return;
		}

		Actor o = game.getWorld().getCurrentLevel().getCell(xPos, yPos);
		if (o instanceof Zombie) {
			game.print(Presets.PLACE_ON_ZOMBIE);
			return;
		}

		if (plantType.equalsIgnoreCase("peashooter")) {

			game.getActionProcessor().processPlaceActor(new PeaShooter(), xPos, yPos);

		} else if (plantType.equalsIgnoreCase("sunflower")) {

			game.getActionProcessor().processPlaceActor(new Sunflower(), xPos, yPos);

		} else {
			game.print(Presets.INVALID_PLANT_TYPE);
		}
	}

}
