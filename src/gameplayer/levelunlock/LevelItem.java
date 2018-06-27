package gameplayer.levelunlock;

import gameplayer.controller.LevelController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LevelItem extends Button {

    private BooleanProperty locked;
    private int level;
    private LevelController myController;

    private static final String LOCKED_STRING = "images/locked.png";
    //private static final String UNLOCKED_STRING = "images/unlocked.png";
    private static final int IMAGE_HEIGHT = 20;
    private static final int IMAGE_WIDTH = 20;

    public LevelItem(int level, boolean locked, LevelController controller){
        super();
        this.setText("Level " + level);
        this.myController = controller;
        this.level = level;
        this.locked = new SimpleBooleanProperty();
        this.locked.addListener((o, oldVal, newVal) -> {
            if(newVal){
                ImageView temp = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(LOCKED_STRING)));
                temp.setFitHeight(IMAGE_HEIGHT);
                temp.setFitWidth(IMAGE_WIDTH);
                this.setGraphic(temp);
                this.getStyleClass().add("lockedButton");
                this.setOnAction(e -> {});
            }
            else{
               /* ImageView temp = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(UNLOCKED_STRING)));
                temp.setFitHeight(IMAGE_HEIGHT);
                temp.setFitWidth(IMAGE_WIDTH);*/
                this.setGraphic(null);
                this.getStyleClass().add("unlockedButton");
                this.setOnAction(e -> myController.changeGameLevel(this.level));
            }
        });
        setLocked(true);
        setLocked(locked);
    }

    public void setLocked(boolean locked){
        this.locked.setValue(locked);
    }

    public int getLevel(){
        return level;
    }
}
