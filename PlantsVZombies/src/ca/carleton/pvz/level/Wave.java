package ca.carleton.pvz.level;

import java.awt.Point;
import java.util.Random;

import ca.carleton.pvz.PlantsVZombies;
import ca.carleton.pvz.actor.Zombie;

/**
 * Stores a wave of zombies. This class is currently not being used, but will be
 * utilized in future iterations.
 *
 */
public class Wave {


	private int remainingZombies;
	private int waveNumber;
	private PlantsVZombies map;

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
	 * @param game The gameWorld to be modified when zombies are spawning.
	 * @return map The resulting gameWorld after zombies have spawned
	 */
	public PlantsVZombies spawnZombies(PlantsVZombies game) {
		map = game;
		Random random = new Random();
		int tmp = random.nextInt(5);
		Zombie z = new Zombie();
		game.getWorld().getCurrentLevel().placeActor(z, new Point(4, tmp));
		
		return map;
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
	 * Sets wave number
	 * @param waveNum New wave number to be assigned to wave object
	 */
	public void setWaveNumber(int waveNum) {
		
		waveNumber = waveNum;
	}
	
	/**
	 * Sets number of zombies per wave
	 * @param numZombies The number of zombies to be assigned to this wave spawn
	 */
	public void setRemainingZombies(int numberOfZombies) {
		
		remainingZombies = numberOfZombies;
		
	}
}
