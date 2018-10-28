package ca.carleton.pvz.plant;

public class Sunflower extends Plant {
	
	private int tick_time;

	public Sunflower() {}
		

	
	public void placed_tick(int i) {
		
		tick_time =  i;
	}
	
	public int returnPlacedTickTime() {
		
		return tick_time;
	}
	
	public String toString() {
		return "S";
	}
}
