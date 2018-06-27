package gameplayer.levelunlock;

import gameplayer.controller.LevelController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SelectLevel extends ScrollPane {
    private int levelProgress;
    private int numOfLevels;
    private ArrayList<LevelItem> levels;
    private VBox levelColumn;
    private Scene myScene;
    private LevelController myController;

    private static final int PANE_WIDTH = 400;
    private static final int PANE_HEIGHT = 400;

    public SelectLevel(int levelProgress, int numOfLevels, Stage stage, LevelController controller){
        super();
        this.myController = controller;
        this.levelProgress = levelProgress;
        this.numOfLevels = numOfLevels;
        levels = new ArrayList<>();
        levelColumn = new VBox();

        /*stage.setWidth(PANE_WIDTH);
        stage.setHeight(PANE_HEIGHT);*/

        levelColumn.prefWidthProperty().bind(stage.widthProperty());
        levelColumn.prefHeightProperty().bind(stage.heightProperty());
        levelColumn.setMinWidth(PANE_WIDTH);
        levelColumn.setMinHeight(PANE_HEIGHT);
        levelColumn.setSpacing(40);
        levelColumn.setPadding(new Insets(40, 0, 40, 0));
        levelColumn.getStyleClass().add("vbox");

        createLevels();

        this.setContent(levelColumn);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);

        myScene = new Scene(this, PANE_WIDTH, PANE_HEIGHT);
        myScene.getStylesheets().add(getClass().getResource("LevelStyle.css").toExternalForm());
    }

    private void createLevels(){
        for(int i = 1; i < numOfLevels + 1; i++){
            LevelItem temp = new LevelItem(i, i > levelProgress, myController);
            temp.prefWidthProperty().bind(levelColumn.widthProperty());
            levels.add(temp);
        }

        levelColumn.getChildren().addAll(levels);
    }

    public void updateLevelProgress(int levelProgress){
        this.levelProgress = levelProgress;
        for(LevelItem l : levels){
            // if level is less than level progress, set level to unlocked
            l.setLocked(!(l.getLevel() <= levelProgress));
        }
    }

    public Scene getMyScene(){
        return myScene;
    }
}
