package data.testing;

import data.ResourceGetter;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class URLUploadTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root,900, 900);
        stage.setScene(scene);
        stage.show();
        ResourceGetter iget = new ResourceGetter();
        iget.selectImage();
    }

    public static void main(String args[])
    {
         launch(args);
    }
}
