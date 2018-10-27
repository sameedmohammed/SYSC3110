package ca.carleton.pvz.zombie;

public class Zombie {
	
	private int health = 600;
	
	
	public int getHealth() {
		
		return health;
	}
	
	public void setHealth(int i) {
		
		health = i;
	}
	
	public String toString() {
		return "Z " + health;
	}
 
}
