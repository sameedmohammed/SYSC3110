package ca.carleton.pvz.actor;

/**
 * The parent plant class, from which different plant species inherit.
 *
 */
public class Plant extends Actor {

	private int cooldown; // this plant's cooldown, in turns

	/**
	 * Constructs a new plant object with the specified cooldown.
	 *
	 * @param cooldown The cooldown of the newly constructed plant.
	 */
	public Plant(int cooldown) {
		this.cooldown = cooldown;
	}

}
