package ca.carleton.pvz.plant;

import java.awt.Point;

public abstract class Plant {
	private int cost;
	private Point position;
	
	public Plant(int cost, Point position) {
		this.cost = cost;
		this.position = position;
	}
	
	public int getCost() { return cost; }
}
