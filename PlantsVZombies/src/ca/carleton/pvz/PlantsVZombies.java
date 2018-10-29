package ca.carleton.pvz;

import java.awt.Point;

import ca.carleton.pvz.command.Command;
import ca.carleton.pvz.command.Parser;
import ca.carleton.pvz.command.Presets;
import ca.carleton.pvz.level.LevelOne;
import ca.carleton.pvz.plant.PeaShooter;
import ca.carleton.pvz.plant.Plant;
import ca.carleton.pvz.plant.Sunflower;

/**
 * Main class for Plants vs Zombies game
 * 
 */
public class PlantsVZombies {
	private World gameWorld;
	private Parser parser;
    private LevelOne level_one;
    private int sunpoints;
    private int tick;
    private int prev_tick;
    private int wave_num;

    private boolean pea_placed;
    private boolean sunflower_placed;
    private int pea_cooldown;
    private int sunflower_cooldown;
	
    public PlantsVZombies() {
        parser = new Parser();
        gameWorld = new World();
        level_one = new LevelOne();
        gameWorld.addLevelToWorld(level_one);
        sunpoints = 500;
        tick = 0;
        prev_tick = 0;
        wave_num = 1;
        pea_placed = false;
        sunflower_placed = false;
        pea_cooldown = 0;
        sunflower_cooldown = 0;
        playGame();

    }
	
	public void playGame() {
		print(Presets.WELCOME);
		boolean finished = false;
		while(!finished) {
			finished = processCommand(parser.getCommand());
		}
	}
	
	public World getWorld() {
		return gameWorld;
	}
	
	public void print(String s) {
		System.out.println(s);
	}
	
	private boolean processCommand(Command command) {
		boolean wantToQuit = false;
		
		if(command.isUnknown()) {
			print(Presets.INVALID);
			return false;
		}
		
		String commandWord = command.getCommandWord();
		
		if(commandWord.equals("help")) {
			processHelp(command);
			
		} else if(commandWord.equals("place")) {
			processPlace(command);
			
		} else if(commandWord.equals("quit")) {
			wantToQuit = true;
		} else if(commandWord.equals("next")) {
            processNext(command);
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
	
	
	private void processPlace(Command command) {
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
		Plant plantToPlace;
		int xPos = Integer.parseInt(command.getThirdWord());
		int yPos = Integer.parseInt(command.getFourthWord());
		if(plantType.equalsIgnoreCase("peashooter")) {
			
			
			if (pea_placed == true) {
				print("Peashooter cooldown in effect"); //Include number of ticks left
				return;
			}
			else {
				plantToPlace = new PeaShooter();
				gameWorld.getCurrentLevel().place(plantToPlace, new Point(xPos, yPos));
				sunpoints = sunpoints - 100;	
				pea_placed = true;
				pea_cooldown = tick;
				print(gameWorld.getCurrentLevel().toString());
				
			}
			
			
		} else if(plantType.equalsIgnoreCase("sunflower")) {
			if (sunflower_placed == true) {
				
				print("Sunflower cooldown in effect"); //Include number of ticks left
				return;
				
			}
			else {
				
				plantToPlace = new Sunflower();
				gameWorld.getCurrentLevel().place(plantToPlace, new Point(xPos, yPos));
				sunpoints = sunpoints - 50;
				sunflower_placed = true;
				sunflower_cooldown = tick;
				print(gameWorld.getCurrentLevel().toString());
				
			}
			
		} else {
			
			plantToPlace = null;
			print("Invalid plant type.");
			return;
			
			}
	}

	

	
	private void processNext(Command command) {
        if(!command.hasSecondWord()) {
            print("next what ");
            return;
        }
        else if (command.getSecondWord().equals("tick")) {
            tick++;

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

            if (wave_num == 1 && tick == 3) { //zombies spawn after tick = 3 for first wave
                print("Zombies are spawning!");
                //spawn zombies
            }
            print(gameWorld.getCurrentLevel().toString());
            return;
        }
        else if (command.getSecondWord().equals("wave")) {
            return;
        }
        else {
            print("Invalid command \n" + "Type 'help' if you need help.");
        }

    }
}
