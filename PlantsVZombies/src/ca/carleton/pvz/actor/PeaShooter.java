package ca.carleton.pvz.actor;

/**
 * A class for the pea-shooting plant.
 *
 */
public class PeaShooter extends Actor {

	private int hits; // number of hits on zombies

	/**
	 * Creates a new pea-shooting plant. This type of plant can shoot and "kill"
	 * zombies.
	 */
	public PeaShooter() {
		hits = 0;
	}

	/**
	 * Gets the number of times this pea shooter has hit a zombie with its
	 * projectile.
	 * 
	 * @return The number of times this pea shooter has hit a zombie with its
	 *         projectile.
	 */
	public int getHits() {
		return hits;
	}

	/**
	 * Resets this pea shooter's hits upon advancing to the next turn.
	 */
	public void newTurn() {
		hits = 0;
	}

	/**
	 * Increments this pea shooter's number of hits by one.
	 */
	public void addHit() {
		++hits;
	}

	/**
	 * Returns a String representing this pea shooter.
	 * 
	 * @return A String representing this pea shooter.
	 */
	@Override
	public String toString() {
		return "🌱";
	}
}