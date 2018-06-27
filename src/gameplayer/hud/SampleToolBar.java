package gameplayer.hud;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import engine.components.Component;
import gameplayer.controller.GameManager;
import gameplayer.labels.IGameStatusLabel;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;

public class SampleToolBar extends ToolBar{

	private HBox toolbarLayout;
	private HUDFactory gameStatusFactory;
	private int activeLevel;
	private Map<String, Component> playerComponentsforLevel;
	private Map<String, Boolean> listOfStatusLabels;
	private List<IGameStatusLabel> labelsToDisplay;
	private List<String> approvedLabels;
	private GameManager gameManager;
	private Map<Integer, Map<String, Component>> playerKeys;
	private static int HBOX_LENGTH = 250;
	Map<Integer, Map<String, Boolean>> hudPropMap;


	/**
	 * Builds a Sample Tool Bar that acts as the HUD for the game
	 */
	public SampleToolBar(GameManager gamemanager, Map<Integer, Map<String, Boolean>> HUDPropMap) {
		gameManager = gamemanager;
		hudPropMap = HUDPropMap;
		playerKeys = gameManager.getPlayerKeys();
		
		setActiveLevel(gameManager.getActiveLevel());
	}	
	

	private List<String> extractSelectedLabels(Map<String, Boolean> listOfStatusLabels){
		List<String> approvedLabels = new ArrayList<String>();
		for (String s: listOfStatusLabels.keySet()) {
			if (listOfStatusLabels.get(s)) {
				approvedLabels.add(s);
			}
		}
		return approvedLabels;
	}

	/**
	 * Update the gameState Values using the gameManager values
	 */
	public void updateGameStatusLabels(GameManager gameManager) {
		for (IGameStatusLabel label : labelsToDisplay) {
			label.update(label.extractGameStateValue(gameManager));
		}
	}


	/**
	 * Method to change the active Level of the HUD to correctly update values based on the level.
	 * @param level
	 */
	public void setActiveLevel(int level) {
		playerComponentsforLevel = playerKeys.get(level);
		listOfStatusLabels = hudPropMap.get(level); 
		approvedLabels = extractSelectedLabels(listOfStatusLabels);
		
		gameStatusFactory = new HUDFactory();
		labelsToDisplay = gameStatusFactory.create(approvedLabels);
		
		toolbarLayout = new HBox(HBOX_LENGTH);
		for (IGameStatusLabel l: labelsToDisplay) {
			toolbarLayout.getChildren().add((Label) l);
		}
		this.getItems().clear();
		this.getItems().add(toolbarLayout);
	}
}
