package authoring.actions;

import authoring.entities.Entity;
import engine.systems.collisions.CollisionDirection;
import javafx.scene.control.ComboBox;

public class DirectionSupplier extends Supplier{
/* Author @Conrad prompts the user for a Direction to assist in action making
 */
    private CollisionDirection dir;
    private static final int XSIZE = 150;
    private static final int YSIZE =70; //55
    private static final String PROMPT = "Select a direction you want associated with this action";
    private static final String COMBO_PROMPT = "Select Direction";


    DirectionSupplier(Entity e){
        super(PROMPT,XSIZE,YSIZE,e);
    }

    @Override
    protected void configureMenu() {
        ComboBox entityList = new ComboBox();
        entityList.setPromptText(COMBO_PROMPT);
        for(CollisionDirection direction : CollisionDirection.values()){
            entityList.getItems().add(direction.name());
        }
        entityList.setOnAction(e->{dir= CollisionDirection.valueOf((String)entityList.getValue());});
        menu.getChildren().add(0,entityList);
    }

    @Override
    protected Object getData() {
        return dir;
    }
}
