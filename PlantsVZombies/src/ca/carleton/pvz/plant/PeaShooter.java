package ca.carleton.pvz.plant;

import java.awt.Point;

public class PeaShooter extends Plant {

	private int hits;

	
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
		hits = hits+1;
	}
	


}
