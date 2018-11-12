package ca.carleton.pvz;

import java.awt.Point;

import ca.carleton.pvz.actor.Actor;
import ca.carleton.pvz.actor.PeaShooter;
import ca.carleton.pvz.actor.Sunflower;
import ca.carleton.pvz.actor.Zombie;
import ca.carleton.pvz.command.Presets;
import ca.carleton.pvz.level.Wave;

public class ActionProcessor {
	private int previousTurn;
	private int peaShooterCooldown;
	private int sunflowerCooldown;
	private boolean peaShooterOnCooldown;
	private boolean sunflowerOnCooldown;
	private boolean waveDefeated;
	private int sunPoints; // in-game currency spent on plants
	private int turn;

	private Wave wave;
	private PlantsVZombies game;

	public ActionProcessor(PlantsVZombies game) {
		this.game = game;
		previousTurn = 0;
		peaShooterCooldown = 0;
		sunflowerCooldown = 0;
		sunPoints = 500;
		turn = 0;
		wave = new Wave(1, 3);
		peaShooterOnCooldown = false;
		sunflowerOnCooldown = false;
		waveDefeated = false;
	}

	public void processNextTurn() {
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
			Zombie.moveZombies(game.getWorld().getCurrentLevel()); // shifting already-placed zombies one to the left
																	// each turn
		}

		if (wave.getWaveNumber() == 1 && turn >= 3 && wave.getRemainingZombies() > 0) { // zombies spawn after turn
																						// == 3 for first wave
			game.print(Presets.ZOMBIES_SPAWNING);
			game.getWorld().updateCurrentLevel(Wave.spawnZombieOnLevel(game.getWorld().getCurrentLevel()));
			wave.setRemainingZombies(wave.getRemainingZombies() - 1);
		}

		if (wave.getWaveNumber() == 2 && turn >= 3 && wave.getRemainingZombies() > 0) {
			game.print(Presets.ZOMBIES_SPAWNING);
			game.getWorld().updateCurrentLevel(Wave.spawnZombieOnLevel(game.getWorld().getCurrentLevel()));
			wave.setRemainingZombies(wave.getRemainingZombies() - 1);
		}

		if (wave.getWaveNumber() == 3 && turn >= 3 && wave.getRemainingZombies() > 0) {
			game.print(Presets.ZOMBIES_SPAWNING);
			game.getWorld().updateCurrentLevel(Wave.spawnZombieOnLevel(game.getWorld().getCurrentLevel()));
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
	}

	public void processPlaceActor(Actor actor, int xPos, int yPos) {
		String plantType;

		if (actor instanceof PeaShooter) {
			plantType = "peashooter";
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
		} else if (actor instanceof Sunflower) {
			plantType = "sunflower";
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
		}
	}

}
