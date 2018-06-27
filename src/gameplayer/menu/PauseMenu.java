package gameplayer.menu;

import gameplayer.buttons.RestartButton;
import gameplayer.buttons.SaveGameButton;
import gameplayer.buttons.SwitchGameButton;
import gameplayer.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PauseMenu extends Popup {
	
	private SaveGameButton saveBtn;
	private RestartButton restartBtn;
	public SwitchGameButton switchBtn;
	private Controller gamePlayerController;
	private Stage myStage;
	private static final String PAUSE_LABEL = "Paused";
	private static final int BUTTON_SPACING = 10;
	/**
	 * Constructor for the Pause Menu Popup
	 */
	public PauseMenu(Stage stage, Controller controller) {
		myStage = stage;
		gamePlayerController = controller;
		VBox pane = new VBox();
		saveBtn = new SaveGameButton(this);
		switchBtn = new SwitchGameButton(stage, this);
		restartBtn = new RestartButton(stage, gamePlayerController, this);
		pane.setSpacing(BUTTON_SPACING);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(new Label(PAUSE_LABEL), restartBtn, switchBtn, saveBtn);
		this.getContent().add(pane);
		pane.getStylesheets().add("gameplayer/playstyle.css");
		pane.setFillWidth(true);
	}

	public void saveGame(){
		gamePlayerController.saveGame();
	}
}
