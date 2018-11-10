package ca.carleton.pvz.gui;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import ca.carleton.pvz.PlantsVZombies;
import ca.carleton.pvz.actor.Actor;
import ca.carleton.pvz.actor.PeaShooter;
import ca.carleton.pvz.actor.Sunflower;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This class controls the JavaFX fxml user interface.
 * 
 * @author Group 5
 *
 */
public class GUIController {
	private PlantsVZombies game;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField peashooterCooldown;

	@FXML
	private Button nextTurnButton;

	@FXML
	private TextField sunflowerCooldown;

	@FXML
	private GridPane gameGrid;

	@FXML
	public void initialize() {
		assert peashooterCooldown != null : "fx:id=\"peashooterCooldown\" was not injected: check your FXML file 'pvzgui.fxml'.";
		assert nextTurnButton != null : "fx:id=\"nextTurnButton\" was not injected: check your FXML file 'pvzgui.fxml'.";
		assert sunflowerCooldown != null : "fx:id=\"sunflowerCooldown\" was not injected: check your FXML file 'pvzgui.fxml'.";
		assert gameGrid != null : "fx:id=\"gameGrid\" was not injected: check your FXML file 'pvzgui.fxml'.";
		nextTurnButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// testing controller -> game interaction
				game.getActionProcessor().processPlaceActor(new Sunflower(), 0, 0);
				game.getActionProcessor().processPlaceActor(new PeaShooter(), 0, 3);
				updateGameGrid();
				game.getActionProcessor().processNextTurn();
			}
		});

	}

	/**
	 * This empty constructor is required for proper loading of the JavaFX GUI
	 * controller
	 */
	public GUIController() {

	}

	/**
	 * Sets up the game grid sprites to represent the current level's state.
	 */
	public void updateGameGrid() {
		ObservableList<Node> children = gameGrid.getChildren();
		InputStream stream = getClass().getResourceAsStream("grass.png");
		Image grass = new Image(stream);
		for (Node child : children) {
			if (child instanceof ImageView) {
				ImageView imgView = (ImageView) child;
				int row, column;

				if (GridPane.getRowIndex(imgView) != null && GridPane.getColumnIndex(imgView) != null) {
					row = GridPane.getRowIndex(imgView);
					column = GridPane.getColumnIndex(imgView);
					Actor actor = game.getWorld().getCurrentLevel().getCell(column, row);

					if (actor != null) {
						imgView.setImage(actor.getSprite());
					} else {
						imgView.setImage(grass);
					}
				}
				imgView.setPreserveRatio(false);
			}
		}
	}

	/**
	 * Set the game that this controller controls
	 * 
	 * @param game
	 *            to control
	 */
	public void setGame(PlantsVZombies game) {
		this.game = game;
	}
}
