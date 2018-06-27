package gameplayer.buttons;


import java.io.File;

import data.DataGameState;
import data.DataRead;
import gameplayer.controller.Controller;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FileUploadButton extends Button implements IGamePlayerButton {
	
		private Stage myStage;
		private final String BUTTON_NAME = "Upload Game";
		private DataGameState gameState;
		private Controller gameController;
		
		public FileUploadButton(Stage stage) {
			myStage = stage;
			this.setText(BUTTON_NAME);
			this.setEvent();
		}

		public void setEvent() {
			this.setOnAction(E -> {
				fileUpload();
				gameController = new Controller(myStage, gameState);
				myStage.setScene(gameController.getControllerScene());
			});
		}

		private void fileUpload() {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
			Window mainStage = this.getScene().getWindow();
			File file = fileChooser.showOpenDialog(mainStage);
	        if (file != null) {
	        		gameState = DataRead.loadPlayerFile(file);
	        }
	       
		}
		
}	
