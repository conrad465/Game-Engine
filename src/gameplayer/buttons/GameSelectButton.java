package gameplayer.buttons;
import data.DataGameState;
import gameplayer.controller.Controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GameSelectButton extends Button implements IGamePlayerButton{
	
	private Stage myStage;
	private Controller gameController;
	private DataGameState myGameState;
	private static final int IMAGE_WIDTH = 100;
	private static final int IMAGE_HEIGHT = 100;

	/**
	 * @param name of the Game
	 * @param currentGameState of the GameData to be imported
	 * @param image of the Game to used for the button.
	 */
	public GameSelectButton(Stage stage, String name, DataGameState currentGameState, Image image) {
		myGameState = currentGameState;
		myStage = stage;
		this.setText(name);
		ImageView gameImage = new ImageView(image);
		gameImage.setFitHeight(IMAGE_HEIGHT);
		gameImage.setFitWidth(IMAGE_WIDTH);
		this.setGraphic(gameImage);
		this.setEvent();
	}

	public void setEvent() {
		this.setOnAction(e -> {
			gameController = new Controller(myStage, myGameState);
		});
	}
}
