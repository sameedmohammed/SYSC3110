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
	private int sunPoints; // in-game currency spent on plants
	private int turn;
	private int previousTurn;
	private Wave wave;
	private boolean peaShooterOnCooldown;
	private boolean sunflowerOnCooldown;
	private int peaShooterCooldown;
	private int sunflowerCooldown;

	private boolean waveDefeated;

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
		sunPoints = 500;
		turn = 0;
		previousTurn = 0;
		wave = new Wave(1, 3);
		peaShooterOnCooldown = false;
		sunflowerOnCooldown = false;
		peaShooterCooldown = 0;
		sunflowerCooldown = 0;
		waveDefeated = false;
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

			++turn;

			// wave logic
			if (wave.getWaveNumber() == 1 && waveDefeated) {
				game.print(Presets.WAVE_COMPLETE);
				return;
			}

			if (wave.getWaveNumber() == 2 && waveDefeated) {
				game.print(Presets.WAVE_COMPLETE);
				return;
			}

			if (wave.getWaveNumber() >= 3 && waveDefeated) {
				game.print("Congrats! You finished the first level of Plants vs. Zombies");
				game.print("Please type \"restart\" if you wish to play again");
				return;
			}

			if (sunflowerOnCooldown && turn - sunflowerCooldown == 2) {
				sunflowerOnCooldown = false;
			}

			if (peaShooterOnCooldown && turn - peaShooterCooldown == 2) {
				peaShooterOnCooldown = false;
			}

			// passive sun point logic -- every three turns, increase sun points by 25
			if (turn - previousTurn == 3) {
				previousTurn = turn;
				sunPoints += 25;
			}

			if (wave.getWaveNumber() >= 1) {
				for (int i = 0; i < game.getWorld().getCurrentLevel().getDimension().height; ++i) {
					for (int j = 0; j < game.getWorld().getCurrentLevel().getDimension().width; ++j) {
						Actor o = game.getWorld().getCurrentLevel().getCell(i, j);
						if (o instanceof Sunflower) {
							if ((turn - ((Sunflower) o).getTurnPlaced()) % 2 == 0) {
								sunPoints += 25;
							}
						}
					}
				}
			}

			
			if (turn > 3) {
				PeaShooter.shootZombies(game.getWorld().getCurrentLevel());
				Zombie.moveZombies(game.getWorld().getCurrentLevel()); // shifting already-placed zombies one to the left each turn
			}

			if (wave.getWaveNumber() == 1 && turn >= 3 && wave.getRemainingZombies() > 0) { // zombies spawn after turn
																							// == 3 for first wave
				game.print(Presets.ZOMBIES_SPAWNING);
				game = wave.spawnZombies(game);
				wave.setRemainingZombies(wave.getRemainingZombies() - 1);
			}

			if (wave.getWaveNumber() == 2 && turn >= 3 && wave.getRemainingZombies() > 0) {
				game.print(Presets.ZOMBIES_SPAWNING);
				game = wave.spawnZombies(game);
				wave.setRemainingZombies(wave.getRemainingZombies() - 1);
			}

			if (wave.getWaveNumber() == 3 && turn >= 3 && wave.getRemainingZombies() > 0) {
				game.print(Presets.ZOMBIES_SPAWNING);
				game = wave.spawnZombies(game);
				wave.setRemainingZombies(wave.getRemainingZombies() - 1);
			}

			if (turn > 6) { // searching for any zombies that made it to end game
				for (int j = 0; j < game.getWorld().getCurrentLevel().getDimension().width; ++j) {
					Actor o = game.getWorld().getCurrentLevel().getCell(0, j);
					if (o instanceof Zombie) {
						game.printGame();
						game.print(Presets.GAME_OVER);
						game.setGameOver();
						return;
					}
				}
			}

			if ((wave.getWaveNumber() == 1 && turn >= 6) || (wave.getWaveNumber() == 2 && turn >= 8)
					|| (wave.getWaveNumber() == 3 && turn >= 10)) {
				waveDefeated = true;
				for (int i = 0; i < game.getWorld().getCurrentLevel().getDimension().height; ++i) {
					for (int j = 0; j < game.getWorld().getCurrentLevel().getDimension().width; ++j) {
						Actor o = game.getWorld().getCurrentLevel().getCell(i, j);
						if (o instanceof Zombie) {
							waveDefeated = false;
						}
					}
				}
			}

			game.print("\nYou currently have " + sunPoints + " sun points.\n");
			game.printGame();

			if (game.isGameOver()) {
				game.printGame();
				game.print(Presets.GAME_OVER);
				game.setGameOver();
				return;
			}

			if (wave.getWaveNumber() == 1 && waveDefeated) {
				waveDefeated = false;
				wave.setRemainingZombies(5);
				turn = 0;
				wave.setWaveNumber(2);
				game.print("Wave 2 will arrive shortly.");
				return;
			}

			if (wave.getWaveNumber() == 2 && waveDefeated) {
				waveDefeated = false;
				wave.setRemainingZombies(7);
				turn = 0;
				wave.setWaveNumber(3);
				game.print("Wave 3 will arrive shortly.");
				return;
			}

			if (wave.getWaveNumber() >= 3 && waveDefeated) {
				game.print("Congrats! You finished the first level of Plants vs. Zombies.");
				game.print("Please type 'restart' if you wish to play again.");
			}

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

		if (wave.getWaveNumber() >= 3 && waveDefeated) {
			game.print("\nCongrats! You finished the first level of Plants vs. Zombies\n");
			game.print("\nPlease type \"restart\" if you wish to play again.\n");
			return;
		}

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

			if (peaShooterOnCooldown) {
				game.print("\n" + plantType + Presets.PLANTTYPE_COOLDOWN); // include number of turns left
			} else if (sunPoints - 100 < 0) {
				game.print(Presets.NOT_ENOUGH_SUNPOINTS + plantType + "\n");
			} else {
				PeaShooter plantToPlace;
				plantToPlace = new PeaShooter();
				game.getWorld().getCurrentLevel().placeActor(plantToPlace, new Point(xPos, yPos));
				sunPoints -= 100;
				peaShooterOnCooldown = true;
				peaShooterCooldown = turn;
				game.print("\nYou currently have " + sunPoints + " sun points.\n");
				game.printGame();
			}

		} else if (plantType.equalsIgnoreCase("sunflower")) {

			if (sunflowerOnCooldown) {
				game.print("\n" + plantType + Presets.PLANTTYPE_COOLDOWN); // include number of turns left
			} else if (sunPoints - 50 < 0) {
				game.print(Presets.NOT_ENOUGH_SUNPOINTS + plantType + "\n");

			} else {
				Sunflower plantToPlace = new Sunflower();
				plantToPlace.setTurnPlaced(turn);
				game.getWorld().getCurrentLevel().placeActor(plantToPlace, new Point(xPos, yPos));
				sunPoints -= 50;
				sunflowerOnCooldown = true;
				sunflowerCooldown = turn;
				game.print("\nYou currently have " + sunPoints + " sun points.\n");
				game.printGame();
			}

		} else {
			game.print(Presets.INVALID_PLANT_TYPE);
		}
	}

}
