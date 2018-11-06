package ca.carleton.pvz.actor;

import java.awt.Point;

import ca.carleton.pvz.PlantsVZombies;

/**
 * A class for the pea-shooting plant.
 *
 */
public class PeaShooter extends Actor {

	private int hits; // number of hits on zombies
	private PlantsVZombies map;

	/**
	 * Creates a new pea-shooting plant. This type of plant can shoot and "kill"
	 * zombies.
	 */
	public PeaShooter() {
		hits = 0;
	}

	/**
	 * Gets the number of times this pea shooter has hit a zombie with its
	 * projectile.
	 * 
	 * @return The number of times this pea shooter has hit a zombie with its
	 *         projectile.
	 */
	public int getHits() {
		return hits;
	}

	/**
	 * Resets this pea shooter's hits upon advancing to the next turn.
	 */
	public void newTurn() {
		hits = 0;
	}

	/**
	 * Increments this pea shooter's number of hits by one.
	 */
	public void addHit() {
		++hits;
	}
	
	/**
	 * Calls upon each pea shooter on map to shoot at any zombies in the same lane
	 * 
	 * @param game The gameWorld to be modified by this method
	 * 
	 * @return map The resulting gameWorld after pea shooters are done shooting at zombies
	 */
	public PlantsVZombies shootZombies(PlantsVZombies game) {
		map = game;
		for (int i = 0; i < game.getWorld().getCurrentLevel().getDimension().height; ++i) {
			for (int j = 0; j < game.getWorld().getCurrentLevel().getDimension().width; ++j) {
				Actor o = game.getWorld().getCurrentLevel().getCell(i, j);
				if (o instanceof PeaShooter) { // if peashooter, shoot all zombies to the right of peashooter
					((PeaShooter) o).newTurn();
					int i1 = i;
					for (int index = i1; index < game.getWorld().getCurrentLevel()
							.getDimension().height; ++index) {
						Actor o1 = game.getWorld().getCurrentLevel().getCell(index, j);
						if (o1 instanceof Zombie) {
							while (((PeaShooter) o).getHits() < 4) {

								// while loop - zombie gets hit up to 4 times or health becomes zero
								((Zombie) o1).setHealth(((Zombie) o1).getHealth() - 100);
								((PeaShooter) o).addHit();
								if (((Zombie) o1).getHealth() <= 0) {
									game.getWorld().getCurrentLevel().placeActor(null, new Point(index, j));
								}

								// if zombie dies and peashooter isn't done shooting, progress to zombies to
								// right
								if (((Zombie) o1).getHealth() == 0 && ((PeaShooter) o).getHits() < 4) {
									for (int i2 = i1 + 1; i2 < game.getWorld().getCurrentLevel()
											.getDimension().height; ++i2) {
										Actor o2 = game.getWorld().getCurrentLevel().getCell(i2, j);
										if (o2 instanceof Zombie) {
											while (((PeaShooter) o).getHits() < 4) {
												((Zombie) o2).setHealth(((Zombie) o2).getHealth() - 100);
												((PeaShooter) o).addHit();
												if (((Zombie) o2).getHealth() <= 0) {
													game.getWorld().getCurrentLevel().placeActor(null,
															new Point(index, j));
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		return map;
	}

	/**
	 * Returns a String representing this pea shooter.
	 * 
	 * @return A String representing this pea shooter.
	 */
	@Override
	public String toString() {
		return "P";
	}
}