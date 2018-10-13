package ca.carleton.pvz.plant;

import java.awt.Point;

import ca.carleton.pvz.PlantsVZombies;
import ca.carleton.pvz.World;

/**
 * Handles creation, placement, of new Plant objects. 
 * Plants have a timer (if you drop a planet, you must wait a set amount of time before dropping it again).
 *
 */
public class PlantManager {
	public static final int DEFAULT_TIMER = 10;
	public static final int SUNFLOWER_TIMER = 10;
	
	public PlantManager() {
		
	}
	
	public void placePlant(Point position) {
		//PlantsVZombies.getWorld().getCurrentLevel().getDimension();
	}
}
