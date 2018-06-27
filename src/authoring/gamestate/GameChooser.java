package authoring.gamestate;

import java.io.File;
import java.util.Properties;
import java.util.function.Consumer;

import authoring.MainApplication;
import authoring.views.AuthoringPane;
import authoring.views.popups.PopUp;
import data.DataUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class to display a window that will list all of the games so that the user can choose one.
 * @author dylanpowers
 *
 */
public class GameChooser implements AuthoringPane, NameChooser {

	private final String GAMES_PATH = "games/";
	VBox root = new VBox();
	private final int PANE_SIZE = 600;
	private final String BACKGROUND_CSS = "name-selector";
	private ListView<String> gamesView;
	
	/**
	 * Initialize the game chooser by opening a window that displays all of the current game names.
	 */
	public GameChooser() { 
		gamesView = new ListView<>();
		ObservableList<String> games = FXCollections.observableArrayList();
		File gamesPath = new File(GAMES_PATH);
		for (File f : gamesPath.listFiles()) {
			games.add(f.getName());
		}
		gamesView.setItems(games);
		gamesView.setMaxWidth(PANE_SIZE - 100);
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(gamesView);
		root.setMaxWidth(PANE_SIZE - 100);
	}

	@Override
	public void setLanguage(Properties language) {
		
	}

	/**
	 * Defines what to do when the window is popped up, and what to do when it is closed.
	 * @param onClose the consumer to define what to do when the window is closed
	 */
	@Override
	public void showAndWait(Consumer<String> onClose) {
		Scene scene = new Scene(root, PANE_SIZE, PANE_SIZE);
		scene.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());
		root.getStyleClass().add(BACKGROUND_CSS);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		gamesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				DataUtils.setGame(newValue);
				onClose.accept(newValue);
				stage.close();
			}
		});
	}
}
