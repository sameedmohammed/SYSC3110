package ca.carleton.pvz.zombie;

/**
 * Stores a wave of zombies.
 *
 */
public class Wave {

	private Zombie[] zombies;
	private int remainingZombies;
	private int waveNumber;

	/**
	 * Creates a new wave comprising the specified number of zombies.
	 * 
	 * @param numZombies The number of zombies initially comprising this wave.
	 */
	public Wave(int waveNumber, int numZombies) {
		
		this.waveNumber = waveNumber;
		zombies = new Zombie[numZombies];
		remainingZombies = numZombies;

		// initialize the array entries with Zombie objects
		for (int i = 0; i < numZombies; ++i) {
			zombies[i] = new Zombie();
		}
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
}
