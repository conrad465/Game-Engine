package authoring;

import java.io.File;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import authoring.logging.AuthoringLogger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class MainApplication extends Application {
	Stage mainStage; 

	Consumer<Scene> changeScene = newScene -> {
		mainStage.setScene(newScene);
		mainStage.sizeToScene();
		mainStage.centerOnScreen();
	};
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AuthoringLogger.setup();
		SplashScreen splashScreen = new SplashScreen(changeScene);
		mainStage = primaryStage;
		Scene s = new Scene(splashScreen, splashScreen.getWidth(), splashScreen.getHeight());
		s.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());
		mainStage.setTitle("One Class, One Method");
		File imageFile = new File("data/images/mystery.jpg");
		Image image = SwingFXUtils.toFXImage(ImageIO.read(imageFile), null);
		mainStage.getIcons().add(image);
		mainStage.setScene(s);
		mainStage.sizeToScene();
		mainStage.show();
		mainStage.centerOnScreen();
	}


	public static void main(String[] args) {
		launch(args);
	}

}

