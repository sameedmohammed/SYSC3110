package ca.carleton.pvz.actor;

/**
 * Manages plant cooldowns. All cooldowns are global and therefore static.
 *
 */
public class PlantManager {

	public static boolean sunflowerPurchaseIsOnCooldown;
	public static boolean peashooterPurchaseIsOnCooldown;

	/**
	 * Resets all plant cooldowns (for starting a new level).
	 */
	public void resetCooldowns() {
		sunflowerPurchaseIsOnCooldown = false;
		peashooterPurchaseIsOnCooldown = false;
	}

}
