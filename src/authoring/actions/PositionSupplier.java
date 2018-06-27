package authoring.actions;
//prompt keycode,
//positions
//entity
//direction
import authoring.entities.Entity;
import authoring.factories.ButtonElement;
import engine.components.XPosition;
import engine.components.YPosition;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PositionSupplier extends Supplier {

    private List<Point> path;
    private List<Circle> points;
    private boolean anchor= false;
    private static final int SIZE = 1500;
    private static final int RADIUS = 5;
    private final int LOCATION = 500;
    private Double offsetX;
    private Double offsetY;

    private static  final String PROMPT = "Define your path then click ok";
    private static final String REDO = "Redo";
    private static final String ANCHOR_PROMPT = "Set an anchor";


    public PositionSupplier(Entity e){
        super(ANCHOR_PROMPT,SIZE,SIZE,e);
        path = new ArrayList<>();
        points = new ArrayList<>();
    }


    protected void configureMenu(){
        myStage.initStyle(StageStyle.TRANSPARENT);
        myScene.setFill(Color.rgb(0, 0, 0, 0.05));
        myScene.setOnMouseClicked(e->logKey(e));

        menu.setLayoutX(LOCATION);
        menu.setLayoutY(LOCATION);
        ButtonElement redo = new ButtonElement(REDO);
        redo.setOnAction(e-> { restart();});
        prompt.setText(ANCHOR_PROMPT);
        buttons.getChildren().add(redo);
    }

    @Override
    protected Object getData() {
        return path;
    }

    private void restart(){
        path.clear();
        anchor=false;
        prompt.setText(ANCHOR_PROMPT);
        root.getChildren().removeAll(points);
    }

    private void logKey(MouseEvent e){
        Circle node;
        System.out.print("Hello?");
        if(anchor){
            int x = (int)Math.round(e.getX()-offsetX);
            int y = (int)Math.round(e.getY()-offsetY);
            path.add(new Point(x,y));
            node = new Circle(e.getX(), e.getY(),RADIUS, Color.GREEN);
            root.getChildren().add(node);
        }
        else
        {
            offsetX =  e.getX() - ((XPosition)entity.get(XPosition.KEY)).getData();
            offsetY =  e.getY() - ((YPosition)entity.get(YPosition.KEY)).getData();
            anchor = true;
            node = new Circle(e.getX(), e.getY(),RADIUS, Color.BLUE);
            prompt.setText(PROMPT);
        }
        try {
            root.getChildren().add(node);
        } catch (Exception e1) {

        }
        points.add(node);

    }
}
