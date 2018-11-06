package ca.carleton.pvz.actor;

import java.awt.Point;

import ca.carleton.pvz.PlantsVZombies;

/**
 * A zombie which advances from the rightmost grid column to the left; when a
 * zombie reaches the leftmost column, it's game over!
 *
 */
public class Zombie extends Actor {
	private PlantsVZombies game;
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
	 * @param health
	 *            The zombie's health will be assigned this value.
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	public PlantsVZombies moveZombies(PlantsVZombies map) {

		game = map;

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

		return game;

	}

	/**
	 * Return a String representation of this zombie (including its health).
	 * 
	 * @return A String representation of this zombie (including its health).
	 */
	@Override
	public String toString() {
		return "Z";
	}

}
