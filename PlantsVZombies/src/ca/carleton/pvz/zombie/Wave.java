package ca.carleton.pvz.zombie;

/**
 * Stores a wave of zombies.
 *
 */
public class Wave {

	private Zombie[] zombies;
	private int numZombies;
	private int remainingZombies;

	public Wave(int numZombies) {
		zombies = new Zombie[numZombies];
		this.numZombies = numZombies;
		this.remainingZombies = numZombies;

		for (int i = 0; i < numZombies; i++) {
			zombies[i] = new Zombie();
		}
	}

	public int getRemainingZombies() {
		return remainingZombies;
	}
}
