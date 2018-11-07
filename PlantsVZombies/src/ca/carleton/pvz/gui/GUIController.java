package ca.carleton.pvz.gui;

import java.net.URL;
import java.util.ResourceBundle;

import ca.carleton.pvz.PlantsVZombies;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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
		    @Override public void handle(ActionEvent e) {
		    	//testing controller -> game interaction
		       System.out.println(game.getWorld().getCurrentLevel().toString()); 
		    }
		});
	}
	
	public GUIController() {
		
	}
	
	public void setGame(PlantsVZombies game) {
		this.game = game;
	}
}
