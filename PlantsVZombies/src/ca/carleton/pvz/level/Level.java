package ca.carleton.pvz.level;

import java.awt.Dimension;
import java.awt.Point;

import ca.carleton.pvz.plant.Plant;
import ca.carleton.pvz.plant.PlantManager;

public class Level {
	private String levelName;
	private Dimension dimension;
	private Plant[][] grass;
	private PlantManager plantManager;
	
	public Level(String name, int width, int height) {
		levelName = name;
		dimension = new Dimension(width, height);
		grass = new Plant[width][height];
		plantManager = new PlantManager();
		
		for(Plant[] pArray : grass) {
			for(Plant p : pArray) {
				p = null;
			}
		}
		
		
	}
	
	public void placePlant(Plant plant, Point p) {
		grass[p.x][p.y] = plant;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public String toString() {
		String s = "";
		for(int row = 0; row < dimension.height; row++)  {
			s += "| ";
			for(int col = 0; col < dimension.width; col++) {
				if(grass[col][row] != null) {
					s += grass[col][row].toString() + " | ";
				} else {
					s += "  | ";
				}
			}
			s += "\n";
		}
		return s;
	}
}
