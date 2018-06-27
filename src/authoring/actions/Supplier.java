package authoring.actions;

import authoring.entities.Entity;
import authoring.factories.ButtonElement;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class Supplier {
/*Author @Conrad overarching class defines most of the setup for popups that give action information to the actionadder

 */
    private final int BOXSIZE = 60;
    private static final String CANCEL = "Cancel";
    private static final String ENTER = "Enter";

    protected Stage myStage;
    protected Scene myScene;
    protected Group root;
    protected Text prompt;
    protected VBox menu;
    protected Entity entity;
    protected HBox buttons;
    protected ButtonElement enter;
    protected ButtonElement cancel;
    protected Class data;

    public Supplier(String prompt, int xsize, int ysize, Entity e){
        init(prompt,xsize,ysize);
        entity = e;
        configureMenu();
        myStage.show();

    }

    private void init(String prompt, int xsize, int ysize){
        myStage = new Stage();
        root = new Group();
        myScene = new Scene(root,xsize,ysize);
        myStage.setResizable(false);
        myStage.setTitle(prompt);
        myStage.setScene(myScene);
        setupMenu();
    }

    public void setupMenu(){
        menu = new VBox();
        buttons = new HBox();
        prompt = new Text();
        menu.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        enter = new ButtonElement(ENTER);
        enter.setOnAction(e->close());
        cancel = new ButtonElement(CANCEL);
        cancel.setOnAction(e->close());

        root.getChildren().add(menu);
        menu.getChildren().addAll(prompt,buttons);
        buttons.getChildren().addAll(enter, cancel);
    }

    protected abstract void configureMenu();

    protected abstract Object getData();

    public void close(){
        myStage.close();
    }

}
