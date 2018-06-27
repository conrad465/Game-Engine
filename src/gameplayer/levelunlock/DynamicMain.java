package gameplayer.levelunlock;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DynamicMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
       // SelectLevel menu = new SelectLevel(1, 3, primaryStage);
        primaryStage.setTitle("Level Selector");
        //primaryStage.setScene(menu.getMyScene());
        primaryStage.show();
    }
}
