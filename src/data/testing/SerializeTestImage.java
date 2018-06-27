

////proves that you cant serialize an image




//package data.testing;
//
//import DataGameState;
//import DataWrite;
//import engine.components.Component;
//import engine.components.Sprite;
//import frontend.components.Level;
//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//public class SerializeTestImage extends Application {
//
//    public static void main(String[] args) {
//      launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Group root = new Group();
//        Scene temp = new Scene(root,100, 100);
//        primaryStage.setScene(temp);
//        File imageFile;
//
//        Map<Level,Map<Integer,Map<String,Component>>> testState = new HashMap<>();
//
//        Level level1 = new Level(1);
//        Level level2 = new Level(2);
//
//        Map<Integer, Map<String, Component>> level1State = new HashMap<>();
//        Map<Integer, Map<String, Component>> level2State = new HashMap<>();
//
//        Integer entity1 = 1;
//        Integer entity2 = 2;
//
//        Map<String, Component> entity1Components = new HashMap<>();
//        Map<String, Component> entity2Components = new HashMap<>();
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Image File");
//        imageFile = fileChooser.showOpenDialog(primaryStage);
//        Image im  = ImageIO.read(imageFile);
//        BufferedImage bImage = new BufferedImage(10,10,1);
//        entity1Components.put("Sprite", new Sprite(1,imageFile.getName()));
//        level1State.put(1,entity1Components);
//        testState.put(level1,level1State);
//
//        DataGameState state = new DataGameState(testState);
//        DataWrite.saveFile(state, "Image Serializtion");
//
//    }
//}
