package ca.carleton.pvz.actor;

/**
 * Creates a sunflower, which accumulates sun points each turn. Sun points are
 * the in-game currency which the user spends on more plants to defeat the
 * zombies.
 *
 */
public class Sunflower extends Actor {

	private int turnPlaced; // the turn this sunflower was planted

	/**
	 * Creates a new sunflower.
	 */
	public Sunflower() {
		turnPlaced = 0;
	}

	/**
	 * Sets the turn this sunflower was planted.
	 * 
	 * @param turnNumber The turn this sunflower was planted.
	 */
	public void setTurnPlaced(int turnNumber) {
		turnPlaced = turnNumber;
	}

	/**
	 * Gets the turn this sunflower was planted.
	 * 
	 * @return The turn this sunflower was planted.
	 */
	public int getTurnPlaced() {
		return turnPlaced;
	}

	/**
	 * Returns a String representation of this sunflower.
	 * 
	 * @return A String representation of this sunflower.
	 */
	@Override
	public String toString() {
		return "S";
	}
}
