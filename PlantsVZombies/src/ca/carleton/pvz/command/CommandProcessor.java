package ca.carleton.pvz.command;

import java.awt.Point;
import java.util.Random;
import ca.carleton.pvz.PlantsVZombies;
import ca.carleton.pvz.World;
import ca.carleton.pvz.actor.Actor;
import ca.carleton.pvz.actor.PeaShooter;
import ca.carleton.pvz.actor.Sunflower;
import ca.carleton.pvz.actor.Zombie;

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
	private int waveNumber; // zombie wave number
	private boolean peaShooterOnCooldown;
	private boolean sunflowerOnCooldown;
	private int peaShooterCooldown;
	private int sunflowerCooldown;
	private int numZombies; // number of zombies currently on the board
	private boolean waveDefeated;

	/**
	 * Constructs a CommandProcessor for game state advancement.
	 *
	 * @param gameWorld The stack of levels to be played.
	 */
	public CommandProcessor(PlantsVZombies game) {
		this.game = game;
		parser = new Parser();
		sunPoints = 500;
		turn = 0;
		previousTurn = 0;
		waveNumber = 1;
		peaShooterOnCooldown = false;
		sunflowerOnCooldown = false;
		peaShooterCooldown = 0;
		sunflowerCooldown = 0;
		numZombies = 0;
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
	 * @param command The given command.
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
	 * @param command The given command.
	 */
	private void processNext(Command command) {

		if (!command.hasSecondWord()) {
			game.print(Presets.NEXT_WHAT);
			return;

		}
		

		
		else if (command.getSecondWord().equals("turn")) {
			++turn;

			// wave logic
			if (waveNumber == 1 && waveDefeated) {
				game.print(Presets.WAVE_COMPLETE);
				return;
			}
			if (waveNumber == 2 && waveDefeated) {
				game.print(Presets.WAVE_COMPLETE);
				return;
			}

			if (waveNumber >= 3 && waveDefeated) {
				game.print("Congrats! You finished the first level of Plants Vs Zombies");
				game.print("Please type 'restart' if you wish to play again");
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

			if (turn > 3) { // shooting zombies

				for (int i = 0; i < game.getWorld().getCurrentLevel().getDimension().height; ++i) {
					for (int j = 0; j < game.getWorld().getCurrentLevel().getDimension().width; ++j) {
						Actor o = game.getWorld().getCurrentLevel().getCell(i, j);

						if (o instanceof PeaShooter) { // if peashooter, shoot all zombies to right of peashooter
							((PeaShooter) o).newTurn();
							int i1 = i;
							for (int index = i1; index < game.getWorld().getCurrentLevel().getDimension().height; ++index) {
								Actor o1 = game.getWorld().getCurrentLevel().getCell(index, j);

								if (o1 instanceof Zombie) {

									while (((PeaShooter) o).getHits() < 4) {
										// while loop - zombie gets hit up to 4 times or health becomes zero
										((Zombie) o1).setHealth(((Zombie) o1).getHealth() - 100);

										((PeaShooter) o).addHit();
										if (((Zombie) o1).getHealth() <= 0) {
											game.getWorld().getCurrentLevel().placeActor(null, new Point(index, j));
										}

										// if zombie dies and peashooter isn't done shooting, progress to zombies to
										// right
										if (((Zombie) o1).getHealth() == 0 && ((PeaShooter) o).getHits() < 4) {
											for (int i2 = i1 + 1; i2 < game.getWorld().getCurrentLevel()
													.getDimension().height; ++i2) {
												Actor o2 = game.getWorld().getCurrentLevel().getCell(i2, j);
												if (o2 instanceof Zombie) {

													while (((PeaShooter) o).getHits() < 4) {
														// while loop - zombie gets hit up to 4 times or health becomes
														// zero
														((Zombie) o2).setHealth(((Zombie) o2).getHealth() - 100);
														((PeaShooter) o).addHit();
														if (((Zombie) o2).getHealth() <= 0) {
															game.getWorld().getCurrentLevel().placeActor(null,
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
				for (int i = 0; i < game.getWorld().getCurrentLevel().getDimension().height; ++i) {
					for (int j = 0; j < game.getWorld().getCurrentLevel().getDimension().width; ++j) {

						Actor o = game.getWorld().getCurrentLevel().getCell(i, j);

						if (o instanceof Zombie) {
							Actor z1 = game.getWorld().getCurrentLevel().getCell(i, j);
							game.getWorld().getCurrentLevel().placeActor(z1, new Point(i - 1, j));
							game.getWorld().getCurrentLevel().placeActor(null, new Point(i, j));
						}
					}
				}
			}

			if (waveNumber == 1 && turn >= 3 && numZombies < 3) { // zombies spawn after turn == 3 for first wave
				game.print(Presets.ZOMBIES_SPAWNING);
				Random random = new Random();
				int tmp = random.nextInt(5);
				Zombie z = new Zombie();
				game.getWorld().getCurrentLevel().placeActor(z, new Point(4, tmp));
				++numZombies;
			}

			if (waveNumber == 2 && turn >= 3 && numZombies < 5) { // zombies spawn after turn == 3 for first wave
				game.print(Presets.ZOMBIES_SPAWNING);
				Random random = new Random();
				int tmp = random.nextInt(5);
				Zombie z = new Zombie();
				game.getWorld().getCurrentLevel().placeActor(z, new Point(4, tmp));
				++numZombies;
			}

			if (waveNumber == 3 && turn >= 3 && numZombies < 7) { // zombies spawn after turn == 3 for first wave
				game.print(Presets.ZOMBIES_SPAWNING);
				Random random = new Random();
				int tmp = random.nextInt(5);
				Zombie z = new Zombie();
				game.getWorld().getCurrentLevel().placeActor(z, new Point(4, tmp));
				++numZombies;
			}

			if (turn > 6) { // searching if any zombies made it to end game
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

			if ((waveNumber == 1 && turn >= 6) || (waveNumber == 2 && turn >= 8) || (waveNumber == 3 && turn >= 10)) {
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
			game.print("You currently have " + sunPoints + " sun points.");
			game.printGame();



			if (game.isGameOver()) {
				game.printGame();
				game.print(Presets.GAME_OVER);
				game.setGameOver();
				return;

			} 

				if (waveNumber == 1 && waveDefeated) {
					waveDefeated = false;
					numZombies = 0;
					turn = 0;
					waveNumber = 2;
					game.print("Wave 2 will arrive shortly.");
					return;
				}

				if (waveNumber == 2 && waveDefeated) {
					waveDefeated = false;
					numZombies = 0;
					turn = 0;
					waveNumber = 3;
					game.print("Wave 3 will arrive shortly.");
					return;
				}

				if (waveNumber >= 3 && waveDefeated) {
					game.print("Congrats! You finished the first level of Plants vs. Zombies.");
					game.print("Please type 'restart' if you wish to play again.");

				} 


	}
		else {
			game.print(Presets.INVALID);
		}
		
		
	}

	/**
	 * Processes the placement of a plant.
	 *
	 * @param command The given command.
	 */
	private void processPlace(Command command) {

		if (waveNumber >= 3 && waveDefeated) {
			game.print("Congrats! You finished the first level of Plants vs. Zombies");
			game.print("Please type 'restart' if you wish to play again.");
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

		String plantType = command.getSecondWord();

		int xPos = Integer.parseInt(command.getThirdWord());
		int yPos = Integer.parseInt(command.getFourthWord());
		
		if(!game.getWorld().getCurrentLevel().isPointValid(new Point(xPos, yPos))) {
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
				game.print(plantType + Presets.PLANTTYPE_COOLDOWN); // include number of turns left
			} else if (sunPoints - 100 < 0) {
				game.print(Presets.NOT_ENOUGH_SUNPOINTS + plantType);

			} else {
				PeaShooter plantToPlace;
				plantToPlace = new PeaShooter();
				game.getWorld().getCurrentLevel().placeActor(plantToPlace, new Point(xPos, yPos));
				sunPoints -= 100;
				peaShooterOnCooldown = true;
				peaShooterCooldown = turn;
				game.print("You currently have " + sunPoints + " sun points.");
				game.printGame();
			}

		} else if (plantType.equalsIgnoreCase("sunflower")) {

			if (sunflowerOnCooldown) {
				game.print(plantType + Presets.PLANTTYPE_COOLDOWN); // include number of turns left
			} else if (sunPoints - 50 < 0) {
				game.print(Presets.NOT_ENOUGH_SUNPOINTS + plantType);

			} else {
				Sunflower plantToPlace = new Sunflower();
				plantToPlace.setTurnPlaced(turn);
				game.getWorld().getCurrentLevel().placeActor(plantToPlace, new Point(xPos, yPos));
				sunPoints -= 50;
				sunflowerOnCooldown = true;
				sunflowerCooldown = turn;
				game.print("You currently have " + sunPoints + " sun points.");
				game.printGame();
			}

		} else {
			game.print(Presets.INVALID_PLANT_TYPE);
		}
	}

}
