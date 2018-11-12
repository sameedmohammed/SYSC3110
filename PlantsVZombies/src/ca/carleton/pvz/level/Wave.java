package ca.carleton.pvz.level;

import java.awt.Point;
import java.util.Random;

import ca.carleton.pvz.PlantsVZombies;
import ca.carleton.pvz.actor.Zombie;

/**
 * Stores a wave of zombies.
 *
 */
public class Wave {

	private int remainingZombies;
	private int waveNumber;
	private PlantsVZombies game;
	private Random r;

	/**
	 * Creates a new wave comprising the specified number of zombies.
	 *
	 * @param waveNumber This wave's sequence number.
	 * @param numZombies The number of zombies initially comprising this wave.
	 */
	public Wave(int waveNumber, int numZombies) {
		this.waveNumber = waveNumber;
		remainingZombies = numZombies;
	}

	/**
	 * Spawns zombies on game map according to waveNumber and numberofZombies.
	 *
	 * @param map The game map to be modified when zombies are spawning.
	 * @return The resulting game map after new zombies have spawned.
	 */
	public PlantsVZombies spawnZombies(PlantsVZombies map) {
		game = map;
		r = new Random();
		int randomRow = r.nextInt(5);
		Zombie zombie = new Zombie();
		game.getWorld().getCurrentLevel().placeActor(zombie, new Point(4, randomRow));
		return game;
	}

	/**
	 * Gets the number of zombies remaining from this wave.
	 *
	 * @return The number of zombies remaining from this wave.
	 */
	public int getRemainingZombies() {
		return remainingZombies;
	}

	/**
	 * Gets this wave's number.
	 *
	 * @return This wave's number.
	 */
	public int getWaveNumber() {
		return waveNumber;
	}

	/**
	 * Sets this wave's number.
	 *
	 * @param waveNum The number to be assigned to this wave.
	 */
	public void setWaveNumber(int waveNum) {
		waveNumber = waveNum;
	}

	/**
	 * Sets the number of zombies remaining in this wave.
	 *
	 * @param numZombies The number of zombies to be assigned to this wave.
	 */
	public void setRemainingZombies(int numZombies) {
		remainingZombies = numZombies;
	}

}
