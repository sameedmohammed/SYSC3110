package ca.carleton.pvz.plant;

/**
 * A class for the pea-shooting plant.
 *
 */
public class PeaShooter extends Plant {

	private int hits; // number of hits on zombies

	public PeaShooter() {
		hits = 0;
	}

	public String toString() {
		return "P";
	}

	public int returnHits() {
		return hits;
	}

	public void newTurn() {
		hits = 0;
	}

	public void addHit() {
		++hits;
	}

}
