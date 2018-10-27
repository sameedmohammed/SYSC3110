package ca.carleton.pvz.command;

import java.awt.Point;
import java.util.Random;

import ca.carleton.pvz.command.Parser;
import ca.carleton.pvz.command.Presets;

import ca.carleton.pvz.PlantsVZombies;
import ca.carleton.pvz.World;
import ca.carleton.pvz.plant.PeaShooter;
import ca.carleton.pvz.zombie.Zombie;
import ca.carleton.pvz.plant.Sunflower;



public class CommandProcessor {

	private Parser parser;
	private World gameWorld;
	
	private int sunpoints;
	private int tick;
	private int prev_tick;
	private int wave_num;
	
	private boolean pea_placed;
	private boolean sunflower_placed;
	private int pea_cooldown;
	private int sunflower_cooldown;
	
	
	
	private int zombies_spawned;
	private boolean zombies_killed;
	
	private boolean gameOver;

	public CommandProcessor(World gameWorld) {
		
		sunpoints = 500;
		tick = 0;
		prev_tick = 0;
		wave_num = 1;
		pea_placed = false;
		sunflower_placed = false;
		pea_cooldown = 0;
		sunflower_cooldown = 0;
		zombies_spawned = 0;
		gameOver = false;
		zombies_killed = false;
		
		parser = new Parser();
		this.gameWorld = gameWorld;
		

		
	}

	public boolean processCommand() {
		boolean wantToQuit = false;
		Command command = parser.getCommand();
		
		if(command.isUnknown()) {
			print(Presets.INVALID);
			return false;
		}
		
		String commandWord = command.getCommandWord();
		
		if(commandWord.equals("help")) {
			if(command.hasSecondWord()) {
				processHelp(command);
			}
			else {
				print(Presets.HELP);
			}
			
			
		} else if(commandWord.equals("place")) {
			processPlace(command);
			
		} else if(commandWord.equals("quit")) {
			System.exit(1);
		}
		
		else if(commandWord.equals("next")) {
			processNext(command);
			
		}
		
		else if (commandWord.equals("restart")) {
			processRestart(command);
			
		}
		return wantToQuit;
	}

    private void processHelp(Command command) {
        String wholeWord = ("" + command.getCommandWord() + " " + command.getSecondWord()+ "");
        if (wholeWord.equals("help quit")) {
            print("Using this command will quit the game.");
        } else if (wholeWord.equals("help place")) {
            print("Using this command places a plant in chosen location... ie. 'place [plant-type] [column coordinate] [row coordinate]");
        } else if (wholeWord.equals("help next")) {
            print("Using this command will advance the game to the next tick.");
        } else if (!command.hasSecondWord()) {
            print (Presets.HELP);
        } else {
            print (Presets.INVALID);
        }
    }
    
    private void processRestart(Command command) {
    	
    	if(command.hasSecondWord()) {
    		print (Presets.INVALID);
    		return;
    	}
    	
    	PlantsVZombies newGame = new PlantsVZombies();

    }
    
    
    private void processNext(Command command) {
		if(!command.hasSecondWord()) {
			print("Next what? ");
			return;
		}
		else if (command.getSecondWord().equals("tick")) {
			tick++;
			//Loop below searches if zombies have been eliminated, this is the absolute minimum turn number zombies can be killed per wave
			
			//Wave complete logic

			if(wave_num == 1 && zombies_killed == true) {
				System.out.println("Wave complete! Type 'next wave' to progress to the next wave of Plants Vs Zombies");
				return;
			}
			if(wave_num == 2 && zombies_killed == true) {
				System.out.println("Wave complete! Type 'next wave' to progress to the next wave of Plants Vs Zombies");
				return;	
			}
			
			if (wave_num >= 3 && zombies_killed == true) {
				System.out.println("Congrats! You finished the first level of Plants Vs Zombies");
				System.out.println("Please type 'restart' if you wish to play again");
				return;
				
			}

			
			if(sunflower_placed == true && tick - sunflower_cooldown == 2) {
				sunflower_placed = false;
			}
			
			if(pea_placed == true && tick - pea_cooldown == 2) {
				pea_placed = false;
			}
			
			if (tick - prev_tick == 3) { //passive sun points logic, every 3 ticks, increment sun points by 25;
				prev_tick = tick;
				sunpoints = sunpoints + 25;
			}	
			
			if (wave_num >= 1) {

				for (int i = 0; i < gameWorld.getCurrentLevel().getDimension().height; i++) {
					for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; j++) {
						Object o = gameWorld.getCurrentLevel().returnObject(i,j);
						
						if (o instanceof Sunflower) {
							if ((tick - ((Sunflower) o).returnPlacedTickTime()) % 2 == 0) {
								sunpoints = sunpoints + 25;
							}
							
						}
						
					}
				
				}
				
			}
			
			if(tick > 3 ) { //shooting zombies
				
				for (int i = 0; i < gameWorld.getCurrentLevel().getDimension().height; i++) {
					for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; j++) {
						Object o = gameWorld.getCurrentLevel().returnObject(i,j);
						
						
						if (o instanceof PeaShooter) { //if peashooter, shoot all zombies to right of peashooter
							((PeaShooter) o).newTurn();
							int i1 = i;
							for (int index = i1; index < gameWorld.getCurrentLevel().getDimension().height; index++) {
								Object o1 = gameWorld.getCurrentLevel().returnObject(index,j);
								
								if (o1 instanceof Zombie) {

									while (((PeaShooter) o).returnHits() < 4) {
									//while loop - zombie gets up to 4 times or health becomes zero
										((Zombie) o1).setHealth(((Zombie) o1).getHealth() - 100);

										((PeaShooter) o).addHit();
										if (((Zombie) o1).getHealth() <= 0) {
											gameWorld.getCurrentLevel().place(null, new Point(index, j));
										}
										
							
									
									if (((Zombie) o1).getHealth() == 0 && ((PeaShooter) o).returnHits() < 4) { //if zombie dies and peashooter isnt done shooting peas, progress to zombies to right
										for (int i2 = i1 +1; i2 < gameWorld.getCurrentLevel().getDimension().height; i2++) {
											Object o2 = gameWorld.getCurrentLevel().returnObject(i2,j);
											if (o2 instanceof Zombie) {
												
												while (((PeaShooter) o).returnHits() < 4 ) {
													//while loop - zombie gets up to 4 times or health becomes zero
													((Zombie) o2).setHealth(((Zombie) o2).getHealth() - 100);
													((PeaShooter) o).addHit();
													if (((Zombie) o2).getHealth() <= 0) {
														gameWorld.getCurrentLevel().place(null, new Point(index, j));

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
			
			}
			
			
			if (tick > 3) { //shifting already placed zombies 1 to left each turn
				for (int i = 0; i < gameWorld.getCurrentLevel().getDimension().height; i++) {
					for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; j++) {
						
						Object o = gameWorld.getCurrentLevel().returnObject(i,j);
						
						
						if (o instanceof Zombie) {
							Object z1 = gameWorld.getCurrentLevel().returnObject(i,j);
							gameWorld.getCurrentLevel().place(z1, new Point(i-1, j));	
							gameWorld.getCurrentLevel().place(null, new Point(i, j));	
	
						}
					}
				}

			}
			
			

			if (wave_num == 1 && tick >= 3 && zombies_spawned < 3) { //zombies spawn after tick = 3 for first wave
				print("Zombies are spawning!");
				
				Random random = new Random();
				int tmp = random.nextInt(5); 
					
				Zombie z = new Zombie();
				gameWorld.getCurrentLevel().place(z, new Point(4, tmp));	
				zombies_spawned++;
			}
			
			if (wave_num == 2 && tick >= 3 && zombies_spawned < 5) { //zombies spawn after tick = 3 for first wave
				print("Zombies are spawning!");
				
				Random random = new Random();
				int tmp = random.nextInt(5); 
					
				Zombie z = new Zombie();
				gameWorld.getCurrentLevel().place(z, new Point(4, tmp));	
				zombies_spawned++;
			}
			
			if (wave_num == 3 && tick >= 3 && zombies_spawned < 7) { //zombies spawn after tick = 3 for first wave
				print("Zombies are spawning!");
				
				Random random = new Random();
				int tmp = random.nextInt(5); 
					
				Zombie z = new Zombie();
				gameWorld.getCurrentLevel().place(z, new Point(4, tmp));	
				zombies_spawned++;
			}
			
			if (tick > 6) { //searching if any zombies made it to end game

					for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; j++) {
						
						Object o = gameWorld.getCurrentLevel().returnObject(0, j);
						
						if (o instanceof Zombie) {
							print(gameWorld.getCurrentLevel().toString());
							System.out.println("Game over! You failed to protect your field from the zombies. :(");
							System.out.println("Please type 'restart' if you wish to try again");
							gameOver = true;
							return;
							
						}
						

				}
				
			}	
			
			
			
			if ((wave_num == 1 && tick >= 6) || (wave_num == 2 && tick >= 8 ) || (wave_num == 3 && tick >= 10 )) {  //
				zombies_killed = true;
				for (int i = 0; i < gameWorld.getCurrentLevel().getDimension().height; i++) {
					for (int j = 0; j < gameWorld.getCurrentLevel().getDimension().width; j++) {
						Object o = gameWorld.getCurrentLevel().returnObject(i,j);
						if (o instanceof Zombie) {

							zombies_killed = false;
							

						}
						
					}
				}
		
			}


			System.out.println("You currently have " + sunpoints + " sunpoints");
		
			print(gameWorld.getCurrentLevel().toString());
			return;
		}
		else if (command.getSecondWord().equals("wave")) {
			if (gameOver == true ) {
				print(gameWorld.getCurrentLevel().toString());
				System.out.println("Game over! You failed to protect your field from the zombies. :(");
				System.out.println("Please type 'restart' if you wish to try again");
				gameOver = true;
				return;
			}
			else {
				if(wave_num == 1 && zombies_killed == true) {
					zombies_killed = false;
					zombies_spawned = 0;
					tick = 0;
					wave_num = 2;
					System.out.println("Wave 2 will be commencing shortly");
					return;
				}
				if(wave_num == 2 && zombies_killed == true) {
					zombies_killed = false;
					zombies_spawned = 0;
					tick = 0;
					wave_num = 3;	
					System.out.println("Wave 3 will be commencing shortly");
					return;
				}
				
				if (wave_num >= 3 && zombies_killed == true) {
					System.out.println("Congrats! You finished the first level of Plants Vs Zombies");
					System.out.println("Please type 'restart' if you wish to play again");
					return;
					
				}
				else {
					
					System.out.println("You haven't killed the current wave of zombies!");
				}
			}
			return;
		}
		else {
			print("Invalid command \n" + "Type 'help' if you need help.");
		}
		
    }

private void processPlace(Command command) {
		
		if (wave_num >= 3 && zombies_killed == true) {
			System.out.println("Congrats! You finished the first level of Plants Vs Zombies");
			System.out.println("Please type 'restart' if you wish to play again");
			return;
			
		}
		
		if(!command.hasSecondWord()) {
			print("Place what?");
			return;
		} else if(!command.hasThirdWord()) {
			print("Place where?");
			return;
		} else if(!command.hasFourthWord()) {
			print("Place where?");
			return;
		}
		
		String plantType = command.getSecondWord();

		int xPos = Integer.parseInt(command.getThirdWord());
		int yPos = Integer.parseInt(command.getFourthWord());
		
		Object o = gameWorld.getCurrentLevel().returnObject(xPos, yPos);
		if (o instanceof Zombie) {
			System.out.println("You cannot place anything on top of a zombie! ");
			return;
			
		}
		
		


		if(plantType.equalsIgnoreCase("peashooter")) {
			
			
			if (pea_placed == true) {
				print("Peashooter cooldown in effect"); //Include number of ticks left
				return;
			}
			
			else if (sunpoints - 100 < 0) {
				print("You do not have enough sunpoints to purchase the peashooter"); 
				return;	
			}
			else {
				PeaShooter plantToPlace;
				plantToPlace = new PeaShooter(); 		
				gameWorld.getCurrentLevel().place(plantToPlace, new Point(xPos, yPos));
				sunpoints = sunpoints - 100;	
				pea_placed = true;
				pea_cooldown = tick;
				System.out.println("You currently have " + sunpoints + " sunpoints");
				print(gameWorld.getCurrentLevel().toString());
				
			}
			
			
		} else if(plantType.equalsIgnoreCase("sunflower")) {
			if (sunflower_placed == true) {
				print("Sunflower cooldown in effect"); //Include number of ticks left
				return;
				
			}
			
			else if (sunpoints - 50 < 0) {
				print("You do not have enough sunpoints to purchase the sunflower"); 
				return;	
				
			}
			else {
				Sunflower plantToPlace;
				plantToPlace = new Sunflower();
				plantToPlace.placed_tick(tick);
				gameWorld.getCurrentLevel().place(plantToPlace, new Point(xPos, yPos));
				sunpoints = sunpoints - 50;
				sunflower_placed = true;
				sunflower_cooldown = tick;
				System.out.println("You currently have " + sunpoints + " sunpoints");
				print(gameWorld.getCurrentLevel().toString());
				
			}
			
		} else {
			

			print("Invalid plant type.");
			return;
			
			}
		}

	public void print(String s) {
		System.out.println(s);
}

}