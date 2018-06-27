package gameplayer;

import gameplayer.view.SplashScreenView;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

	private static final String TITLE = "VoogaSalad";
	private Stage mainStage;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		SplashScreenView splashScreen = new SplashScreenView(primaryStage);
		mainStage = primaryStage;
		mainStage.setTitle(TITLE);
		mainStage.setScene(splashScreen.getScene());
		mainStage.show();
	}
}

