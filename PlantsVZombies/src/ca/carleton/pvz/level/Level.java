package ca.carleton.pvz.level;

import java.awt.Dimension;
import java.awt.Point;

import ca.carleton.pvz.actor.Actor;
import ca.carleton.pvz.actor.PlantManager;

/**
 * An abstract class from which all the levels in the game inherit.
 *
 */
public class Level {

	private String levelName;
	private Dimension levelDimension;
	protected Actor[][] grid; // the grid in which one can place plants
	private PlantManager plantManager;
	private int turn; // the current turn

	/**
	 * Initializes the fields of a level object.
	 *
	 * @param levelName The name of the level.
	 * @param width The width (number of horizontal cells) of the level.
	 * @param height The height (number of vertical cells) of the level.
	 */
	public Level(String levelName, int width, int height) {

		this.levelName = levelName;
		levelDimension = new Dimension(width, height);
		grid = new Actor[width][height];
		plantManager = new PlantManager();
		turn = 0;

		// initialize grid (playable area)
		for (Actor[] row : grid) {
			for (Actor cell : row) {
				cell = null;
			}
		}
	}

	/**
	 * Gets the current turn.
	 *
	 * @return The current turn.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Increments turn by one.
	 */
	public void incTurn() {
		++turn;
	}

	/**
	 * Gets the previous turn.
	 *
	 * @return The previous turn.
	 */
	public int getPrevTurn() {
		return turn - 1;
	}

	/**
	 * Get the cell located at the given coordinates.
	 *
	 * @param x The x-coordinate (column number).
	 * @param y The y-coordinate (row number).
	 * @return The cell located at the given coordinates, or null if the
	 *         coordinates are invalid.
	 */
	public Actor getCell(int x, int y) {
		if (isPointValid(new Point(x, y))) {
			return grid[x][y];
		}
		return null;
	}

	/**
	 * Place a plant or zombie at the given point.
	 *
	 * @param a A plant or zombie object to be placed.
	 * @param p The point at which to place the given object.
	 */
	public void placeActor(Actor a, Point p) {
		if (isPointValid(p)) {
			grid[p.x][p.y] = a;
		}
	}

	/**
	 * Returns the Dimension object comprising the width and height of the grid.
	 *
	 * @return The Dimension object comprising the width and height of the grid.
	 */
	public Dimension getDimension() {
		return levelDimension;
	}

	/**
	 * Gets this level's name.
	 *
	 * @return This level's name.
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * Check if the given point is a valid position on the grid.
	 *
	 * @param p The point whose validity will be evaluated.
	 * @return true if valid, false otherwise.
	 */
	public boolean isPointValid(Point p) {
		return p.x < levelDimension.width && p.x >= 0 && p.y < levelDimension.height && p.y >= 0;
	}

	/**
	 * Returns the game grid as a String.
	 *
	 * @return The game grid as a String.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int row = 0; row < levelDimension.height; ++row) {
			s += "| ";
			for (int col = 0; col < levelDimension.width; ++col) {
				if (grid[col][row] != null) {
					s += grid[col][row] + " | ";
				} else {
					s += "  | ";
				}
			}
			s += "\n";
		}
		return s;
	}
}
