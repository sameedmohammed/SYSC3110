package ca.carleton.pvz;

import ca.carleton.pvz.command.CommandProcessor;
import ca.carleton.pvz.command.Presets;
import ca.carleton.pvz.gui.GUIController;
import ca.carleton.pvz.level.LevelOne;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class manages the flow of the game.
 * 
 */
public class PlantsVZombies extends Application {

	private World gameWorld; // stores the levels to be played
	private CommandProcessor commandProcessor; // processes user input
	private ActionProcessor actionProcessor;
	private boolean gameOver;
	private static GUIController controller;

	/**
	 * The start method for the JavaFX GUI. Loads GUI from fxml file and
	 * creates/shows a scene containing it.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(GUIController.class.getResource("PVZGUI.fxml"));
		BorderPane borderPane = loader.load();

		// Get the Controller from the FXMLLoader
		controller = loader.getController();
		controller.setGame(this);
		Scene scene = new Scene(borderPane, 1030, 615);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public GUIController getController() {
		return controller;
	}

	/**
	 * Constructs a new game to be played.
	 */
	public PlantsVZombies() {
		gameWorld = new World();
		actionProcessor = new ActionProcessor(this);
		// commandProcessor = new CommandProcessor(this);
		gameWorld.addLevel(new LevelOne());
		gameOver = false;

		// playGame();

	}

	// TODO We likely won't need this playGame method anymore... as we don't
	// constantly poll for new commands with GUI.
	/**
	 * Repeatedly prompts the user for input until "quit" is entered.
	 */
	public void playGame() {
		print(Presets.WELCOME);
		print(gameWorld.getCurrentLevel().toString());
		boolean finished = false;
		while (!finished) {
			finished = commandProcessor.processCommand();
		}
	}

	public World getWorld() {
		return gameWorld;
	}

	/**
	 * Sets gameOver to true.
	 */
	public void setGameOver() {
		gameOver = true;
	}

	/**
	 * Is game over?
	 * 
	 * @return true if game is over, false otherwise.
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Print out the current level being played.
	 */
	public void printGame() {
		print(getWorld().getCurrentLevel().toString());
	}

	/**
	 * Get the game's action processor
	 * 
	 * @return the game action processor
	 */
	public ActionProcessor getActionProcessor() {
		return actionProcessor;
	}

	/**
	 * Shorthand for printing to the terminal.
	 * 
	 * @param s
	 *            The String to be printed.
	 */
	public void print(String s) {
		System.out.println(s);
	}

}
