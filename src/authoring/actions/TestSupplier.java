package authoring.actions;

import authoring.entities.Block;
import authoring.entities.Entity;
import authoring.gamestate.GameState;
import authoring.gamestate.Level;
import data.DataRead;
import data.DataWrite;
import engine.components.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TestSupplier extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene myScene = new Scene(root, 500,500, Color.AQUA);
        primaryStage.setScene(myScene);


        Entity block = new Block(1,"Block");
        block.add(new XPosition(1,0));
        block.add(new YPosition(1,0));
        block.add(new AI(1));
        block.add(new XVelocity(1,0));
        block.add(new YVelocity(1,0));
        block.add(new KeyInput(1));
        block.add(new Sprite(1,"8Bit.png"));
        block.add(new Player(1));

        Block block1 = new Block(2,"block2");
        block1.add(new XPosition(2,40));
        block1.add(new YPosition(2,40));
        block1.add(new AI(1));
        block1.add(new XVelocity(2,0));
        block1.add(new YVelocity(2,0));
        block1.add(new KeyInput(2));
        block1.add(new Sprite(2,"8Bit.png"));
        block1.add(new Player(2));

        Level le = new Level(1);
        le.addEntity(block);
        le.addEntity(block1);
        List<Level > levels = new ArrayList<>();
        block.setImage(DataRead.loadImage("8Bit.png"));
        GameState g = new GameState();
        g.addLevel(le);
        primaryStage.show();
        Button b = new Button("Add a file");
        b.setOnAction(e-> {

        });
        root.getChildren().addAll(block,block1,b);

        ActionAdderView pSup = new ActionAdderView(block);


    }
}
