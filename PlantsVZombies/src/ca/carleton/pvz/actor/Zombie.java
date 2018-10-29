package ca.carleton.pvz.actor;

/**
 * A zombie which advances from the rightmost grid column to the left; when a
 * zombie reaches the leftmost column, it's game over!
 *
 */
public class Zombie extends Actor {

	private int health;

	/**
	 * Creates a new Zombie.
	 */
	public Zombie() {
		health = 600;
	}

	/**
	 * Gets the current health of this zombie.
	 * 
	 * @return The current health of this zombie.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Sets the health of this zombie.
	 * 
	 * @param health The zombie's health will be assigned this value.
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Return a String representation of this zombie (including its health).
	 * 
	 * @return A String representation of this zombie (including its health).
	 */
	@Override
	public String toString() {
		return "Z " + health;
	}

}
