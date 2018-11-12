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
	
	private static Random r;
	private int remainingZombies;
	private int waveNumber;

	/**
	 * Creates a new wave comprising the specified number of zombies.
	 *
	 * @param waveNumber This wave's sequence number.
	 * @param numZombies The number of zombies initially comprising this wave.
	 */
	public Wave(int waveNumber, int numZombies) {
		this.waveNumber = waveNumber;
		remainingZombies = numZombies;
		r = new Random();
	}

	/**
	 * Spawns zombies on game map according to waveNumber and numberofZombies.
	 *
	 * @param map The game map to be modified when zombies are spawning.
	 * @return The resulting game map after new zombies have spawned.
	 */
	public static Level spawnZombieOnLevel(Level level) {
		int randomRow = r.nextInt(5);
		Zombie zombie = new Zombie();
		level.placeActor(zombie, new Point(4, randomRow));
		return level;
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
