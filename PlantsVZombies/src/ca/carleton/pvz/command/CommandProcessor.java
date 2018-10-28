package ca.carleton.pvz.command;

import java.awt.Point;
import java.util.Random;
import ca.carleton.pvz.PlantsVZombies;
import ca.carleton.pvz.World;
import ca.carleton.pvz.plant.PeaShooter;
import ca.carleton.pvz.plant.Sunflower;
import ca.carleton.pvz.zombie.Zombie;

/**
 * Processes the user-inputed command and advances the game accordingly.
 *
 */
public class CommandProcessor {

	private Parser parser;
	private World gameWorld;
	private int sunPoints; // in-game currency spent on plants
	private int turn;
	private int previousTurn;
	private int waveNumber; // zombie wave number
	private boolean peaShooterOnCooldown;
	private boolean sunflowerOnCooldown;
	private int peaShooterCooldown;
	private int sunflowerCooldown;
	private int numZombies; // number of zombies currently on the board
	private boolean waveDefeated;
	private boolean gameOver;

	/**
	 * Constructs a CommandProcessor for game state advancement.
	 *
	 * @param gameWorld The stack of levels to be played.
	 */
	public CommandProcessor(World gameWorld) {
		parser = new Parser();
		this.gameWorld = gameWorld;
		sunPoints = 500;
		turn = 0;
		previousTurn = 0;
		waveNumber = 1;
		peaShooterOnCooldown = false;
		sunflowerOnCooldown = false;
		peaShooterCooldown = 0;
		sunflowerCooldown = 0;
		numZombies = 0;
		gameOver = false;
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
			print(Presets.INVALID);
			return false;
		}

		String commandWord = command.getCommandWord();

		switch (commandWord) {
		case "help":
			if (command.hasSecondWord()) {
				processHelp(command);
			} else {
				print(Presets.HELP);
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
				print(Presets.INVALID);
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
	 * @param command The given command.
	 */
	private void processHelp(Command command) {

		if (!command.hasThirdWord()) {

			switch (command.getSecondWord()) {
			case "quit":
				print("Quits the game (terminates the app).\n");
				break;
			case "place":
				print("Places the specified type of plant at the specified coordinates. Takes three args in the format:"
						+ "\n\"place <plant type> <x-coordinate> <y-coordinate>\"\n");
				break;
			case "next":
				print("Type \"next turn\" to advance the game to the next turn,"
						+ "or \"next wave\" to bring on the next wave of zombies.\n");
				break;
			case "restart":
				print("Starts a new game.\n");
				break;
			default:
				print(Presets.INVALID);
				break;
			}

		} else {

			print(Presets.INVALID);

		}
	}

	/**
	 * Advances the game to the next state (turn) based on the current state.
	 *
	 * @param command The given command.
	 */
	private void processNext(Command command) {

		if (!command.hasSecondWord()) {
			print("Next what? (\"next\" should be followed by \"turn\" to advance to the next game state,"
					+ "\nor \"wave\" to bring on the next wave of zombies.)\n");

		} else if (command.getSecondWord().equals("turn")) {
			++turn;

			// wave logic
			if (waveNumber == 1 && waveDefeated) {
				print("Wave complete! Type 'next wave' to progress to the next wave of zombies");
				return;
			}
			if (waveNumber == 2 && waveDefeated) {
				print("Wave complete! Type 'next wave' to progress to the next wave of zombies");
				return;
			}

			if (waveNumber >= 3 && waveDefeated) {
				print("Congrats! You finished the first level of Plants Vs Zombies");
				print("Please type 'restart' if you wish to play again");
				return;

			}

			if (sunflowerOnCooldown && turn - sunflowerCooldown == 2) {
				sunflowerOnCooldown = false;
			}

			if (peaShooterOnCooldown && turn - peaShooterCooldown == 2) {
				peaShooterOnCooldown = false;
			}

			if (turn - previousTurn == 3) { // passive sun points logic (every 3 turns, increase sun points by 25)
				previousTurn = turn;
				sunPoints += 25;
			}

			if (waveNumber >= 1) {
				for (int i = 0; i < gameWorld.getCurrentLevel().getDimension().height; ++i) {
					for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; ++j) {
						Object o = gameWorld.getCurrentLevel().getCell(i, j);
						if (o instanceof Sunflower) {
							if ((turn - ((Sunflower) o).getTurnPlaced()) % 2 == 0) {
								sunPoints += 25;
							}
						}
					}
				}
			}

			if (turn > 3) { // shooting zombies

				for (int i = 0; i < gameWorld.getCurrentLevel().getDimension().height; ++i) {
					for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; ++j) {
						Object o = gameWorld.getCurrentLevel().getCell(i, j);

						if (o instanceof PeaShooter) { // if peashooter, shoot all zombies to right of peashooter
							((PeaShooter) o).newTurn();
							int i1 = i;
							for (int index = i1; index < gameWorld.getCurrentLevel().getDimension().height; ++index) {
								Object o1 = gameWorld.getCurrentLevel().getCell(index, j);

								if (o1 instanceof Zombie) {

									while (((PeaShooter) o).getHits() < 4) {
										// while loop - zombie gets hit up to 4 times or health becomes zero
										((Zombie) o1).setHealth(((Zombie) o1).getHealth() - 100);

										((PeaShooter) o).addHit();
										if (((Zombie) o1).getHealth() <= 0) {
											gameWorld.getCurrentLevel().placePlant(null, new Point(index, j));
										}

										// if zombie dies and peashooter isn't done shooting, progress to zombies to
										// right
										if (((Zombie) o1).getHealth() == 0 && ((PeaShooter) o).getHits() < 4) {
											for (int i2 = i1 + 1; i2 < gameWorld.getCurrentLevel()
													.getDimension().height; ++i2) {
												Object o2 = gameWorld.getCurrentLevel().getCell(i2, j);
												if (o2 instanceof Zombie) {

													while (((PeaShooter) o).getHits() < 4) {
														// while loop - zombie gets hit up to 4 times or health becomes
														// zero
														((Zombie) o2).setHealth(((Zombie) o2).getHealth() - 100);
														((PeaShooter) o).addHit();
														if (((Zombie) o2).getHealth() <= 0) {
															gameWorld.getCurrentLevel().placePlant(null,
																	new Point(index, j));
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

			if (turn > 3) { // shifting already-placed zombies one to the left each turn
				for (int i = 0; i < gameWorld.getCurrentLevel().getDimension().height; ++i) {
					for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; ++j) {

						Object o = gameWorld.getCurrentLevel().getCell(i, j);

						if (o instanceof Zombie) {
							Object z1 = gameWorld.getCurrentLevel().getCell(i, j);
							gameWorld.getCurrentLevel().placePlant(z1, new Point(i - 1, j));
							gameWorld.getCurrentLevel().placePlant(null, new Point(i, j));
						}
					}
				}
			}

			if (waveNumber == 1 && turn >= 3 && numZombies < 3) { // zombies spawn after turn == 3 for first wave
				print("Zombies are spawning!");
				Random random = new Random();
				int tmp = random.nextInt(5);
				Zombie z = new Zombie();
				gameWorld.getCurrentLevel().placePlant(z, new Point(4, tmp));
				++numZombies;
			}

			if (waveNumber == 2 && turn >= 3 && numZombies < 5) { // zombies spawn after turn == 3 for first wave
				print("Zombies are spawning!");
				Random random = new Random();
				int tmp = random.nextInt(5);
				Zombie z = new Zombie();
				gameWorld.getCurrentLevel().placePlant(z, new Point(4, tmp));
				++numZombies;
			}

			if (waveNumber == 3 && turn >= 3 && numZombies < 7) { // zombies spawn after turn == 3 for first wave
				print("Zombies are spawning!");
				Random random = new Random();
				int tmp = random.nextInt(5);
				Zombie z = new Zombie();
				gameWorld.getCurrentLevel().placePlant(z, new Point(4, tmp));
				++numZombies;
			}

			if (turn > 6) { // searching if any zombies made it to end game
				for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; ++j) {
					Object o = gameWorld.getCurrentLevel().getCell(0, j);
					if (o instanceof Zombie) {
						print(gameWorld.getCurrentLevel().toString());
						print("Game over! You failed to protect your home from the zombies :(");
						print("Please type \"restart\" if you wish to try again.");
						gameOver = true;
						return;
					}
				}
			}

			if ((waveNumber == 1 && turn >= 6) || (waveNumber == 2 && turn >= 8) || (waveNumber == 3 && turn >= 10)) {
				waveDefeated = true;
				for (int i = 0; i < gameWorld.getCurrentLevel().getDimension().height; ++i) {
					for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; ++j) {
						Object o = gameWorld.getCurrentLevel().getCell(i, j);
						if (o instanceof Zombie) {
							waveDefeated = false;
						}
					}
				}
			}
			print("You currently have " + sunPoints + " sun points.");
			print(gameWorld.getCurrentLevel().toString());

		} else if (command.getSecondWord().equals("wave")) {

			if (gameOver) {
				print(gameWorld.getCurrentLevel().toString());
				print("Game over! You failed to protect your home from the zombies :(");
				print("Please type \"restart\" if you wish to try again.");
				gameOver = true;

			} else {

				if (waveNumber == 1 && waveDefeated) {
					waveDefeated = false;
					numZombies = 0;
					turn = 0;
					waveNumber = 2;
					print("Wave 2 will arrive shortly.");
					return;
				}

				if (waveNumber == 2 && waveDefeated) {
					waveDefeated = false;
					numZombies = 0;
					turn = 0;
					waveNumber = 3;
					print("Wave 3 will arrive shortly.");
					return;
				}

				if (waveNumber >= 3 && waveDefeated) {
					print("Congrats! You finished the first level of Plants vs. Zombies.");
					print("Please type 'restart' if you wish to play again.");

				} else {
					print("You haven't killed the current wave of zombies!");
				}
			}

		} else {
			print("Invalid command \n" + "Type 'help' if you need help.");
		}
	}

	/**
	 * Processes the placement of a plant.
	 *
	 * @param command The given command.
	 */
	private void processPlace(Command command) {

		if (waveNumber >= 3 && waveDefeated) {
			print("Congrats! You finished the first level of Plants vs. Zombies");
			print("Please type \"restart\" if you wish to play again.");
			return;
		}

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

		Object o = gameWorld.getCurrentLevel().getCell(xPos, yPos);
		if (o instanceof Zombie) {
			print("You cannot place anything on top of a zombie!");
			return;
		}

		if (plantType.equalsIgnoreCase("peashooter")) {

			if (peaShooterOnCooldown) {
				print("Peashooter cooldown in effect."); // include number of turns left
			} else if (sunPoints - 100 < 0) {
				print("You do not have enough sun points to purchase the peashooter.");

			} else {
				PeaShooter plantToPlace;
				plantToPlace = new PeaShooter();
				gameWorld.getCurrentLevel().placePlant(plantToPlace, new Point(xPos, yPos));
				sunPoints -= 100;
				peaShooterOnCooldown = true;
				peaShooterCooldown = turn;
				print("You currently have " + sunPoints + " sun points.");
				print(gameWorld.getCurrentLevel().toString());
			}

		} else if (plantType.equalsIgnoreCase("sunflower")) {

			if (sunflowerOnCooldown) {
				print("Sunflower cooldown in effect."); // include number of turns left
			} else if (sunPoints - 50 < 0) {
				print("You do not have enough sun points to purchase the sunflower.");

			} else {
				Sunflower plantToPlace = new Sunflower();
				plantToPlace.setTurnPlaced(turn);
				gameWorld.getCurrentLevel().placePlant(plantToPlace, new Point(xPos, yPos));
				sunPoints -= 50;
				sunflowerOnCooldown = true;
				sunflowerCooldown = turn;
				print("You currently have " + sunPoints + " sun points.");
				print(gameWorld.getCurrentLevel().toString());
			}

		} else {
			print("Invalid plant type.");
		}
	}

	/**
	 * Shorthand for printing to the terminal.
	 *
	 * @param s The String to be printed.
	 */
	public void print(String s) {
		System.out.println(s);
	}
}
