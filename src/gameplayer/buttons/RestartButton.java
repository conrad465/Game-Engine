package gameplayer.buttons;

import java.util.Map;

import authoring.gamestate.Level;
import data.DataGameState;
import engine.components.Component;
import gameplayer.controller.Controller;
import gameplayer.menu.PauseMenu;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RestartButton extends Button implements IGamePlayerButton{

	private final String BUTTON_NAME = "Restart Game";
	private Controller gameController;
	private Stage myStage;
	private PauseMenu pauseMenu;
	private DataGameState initialGameState;

	public RestartButton(Stage stage, Controller g, PauseMenu pausemenu) {
		pauseMenu = pausemenu;
		myStage = stage;
		gameController = g;
		this.setText(BUTTON_NAME);
		this.setEvent();
	}


	public void setEvent() {
		this.setOnAction(e->{
			pauseMenu.hide();
			gameController.restartGame();
		});
	}
}