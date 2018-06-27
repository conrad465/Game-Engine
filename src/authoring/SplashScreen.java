package authoring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.lang.reflect.Method;

import java.util.Properties;
import java.util.function.Consumer;
import java.util.logging.Logger;

import authoring.gamestate.AuthoringStateLoader;
import authoring.gamestate.GameChooser;
import authoring.gamestate.GameNameChooser;
import authoring.gamestate.GameState;
import authoring.views.MainView;
import data.DataGameState;
import data.DataRead;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class SplashScreen extends VBox {
	Properties properties;
	Consumer changeScene;

	private static final int SPLASH_SCREEN_HEIGHT = 300;
	private static final int SPLASH_SCREEN_WIDTH = 400;

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public SplashScreen(Consumer<Scene> cs) {
		properties = new Properties();
		changeScene = cs;

		try {
			properties.load(new FileInputStream("src/resources/menus/SplashScreen/SplashScreen.properties"));
		} catch (IOException e) {
			LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
		}

		this.setWidth(SPLASH_SCREEN_WIDTH);
		this.setHeight(SPLASH_SCREEN_HEIGHT);
		this.setAlignment(Pos.CENTER);
		this.getStyleClass().add("root");
		this.getStyleClass().add("splash-screen-wrapper");

		for(String key: properties.stringPropertyNames()) {
			try {
				this.getChildren().add(buildButton(key, this.getClass().getMethod(key)));
			} catch (Exception e) {
				System.out.println("Error creating button " + key);	
				LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
			}
		}

	}

	public void playGame() {
	}

	/**
	 * Creates a new gameAuthoringEnviornment view based on the 
	 */
	public void loadAuthor() {
		GameChooser gc = new GameChooser();
		gc.showAndWait(name -> {
			File playerFile = AuthoringStateLoader.findStateFile(name);
			DataGameState dgs = DataRead.loadPlayerFile(playerFile);
			MainView mainView  = new MainView(name, dgs);
			Parent layout = mainView.build();
			Scene s = new Scene(layout, mainView.getIDEWidth(), mainView.getIDEHeight());
			s.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());
			changeScene.accept(s);
		});
	}
	
	public void newAuthor() {
		GameNameChooser gnc = new GameNameChooser();
		gnc.showAndWait(name -> {
			MainView mainView  = new MainView(name);
			Parent layout = mainView.build();
			Scene s = new Scene(layout, mainView.getIDEWidth(), mainView.getIDEHeight());
			s.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());
			changeScene.accept(s);
		});
	}
	

	/**
	 * Builds a button with the text equivalent to 
	 * @param name the name of the type of button to build
	 * @param method the consumer to be invoked on click
	 * @return the final button
	 */
	public Button buildButton(String name, Method method) {
		Button b = new Button();
		b.setText(properties.getProperty(name));
		b.setMinWidth(this.getSplashWidth());
		b.getStyleClass().add("splash-screen-button");
		b.setOnMouseClicked(e -> {
			try {
				method.invoke(this, null);
			} catch (Exception e1) {
				LOGGER.log(java.util.logging.Level.SEVERE, e1.toString(), e1);
			}});
		return b;	
	}

	/**
	 * Returns a double that is the size of the splashScreen window
	 * @return double SplashScreenHeight
	 */
	public double getSplashHeight() {
		return this.getHeight();
	}

	/**
	 * Returns a double that is the size of the splashScreen window
	 * @return double SplashScreenWidth
	 */
	public double getSplashWidth() {
		return this.getWidth();
	}
}
