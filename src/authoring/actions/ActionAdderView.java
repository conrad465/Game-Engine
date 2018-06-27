package authoring.actions;

import authoring.entities.Entity;
import authoring.factories.ButtonElement;
import data.DataUtils;
import engine.actions.Actions;
import engine.components.Component;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class ActionAdderView extends Supplier{
    /* @Author Conrad this class is the UI for the actionadder feature, it allows the user to
        configure the action and component pair they want to define and queries actionadder to
        do create
     */

    private ActionAdder actionAdder;
    private ComboBox<String> components;
    private ComboBox<String> actions;
    private static final int SIZE = 240;
    private static final double RATIO = 2.5;
    private static final String ADD = "Add an action";
    private static final String COMP_CONFIG = "Configure a Component";
    private static final String ACTION_CONFIG = "Configure an action";
    private static final String TRY = "Try a combination";
    private static final Set<String> ACCEPTED_COMPONENTS = new HashSet<>(Arrays.asList("AI", "Collidable", "KeyInput"));


    public ActionAdderView(Entity entity){
        super(ADD, SIZE, (int)Math.round(SIZE/RATIO),entity);
    }

    protected void configureMenu(){
        /* define the options that the user sees when building actions
         */
        actionAdder = new ActionAdder(entity);
        menu.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        enter.setOnAction(e->{ build();});
        components = new ComboBox<>();
        components.setPromptText(COMP_CONFIG);
        for(Component component : entity.getComponentList()){
            if(ACCEPTED_COMPONENTS.contains(component.getKey()))
                components.getItems().add(component.getKey());
        }

        actions = new ComboBox<>();
        actions.setPromptText(ACTION_CONFIG);
        for(String action : actionAdder.getActions().keySet()){
            actions.getItems().add(action);
        }

        ButtonElement attempt = new ButtonElement(TRY);
        attempt.setOnAction(e->getSuppliers());
        buttons.getChildren().add(0, attempt);
        menu.getChildren().add(0, components);
        menu.getChildren().add(0, actions);

    }

    private void build(){
        /* asks the actionadder to build an action then set it to a component
         */
        actionAdder.buildActionComponent(actions.getValue(), components.getValue());
    }

    private void getSuppliers(){
        /* calls the actionadder to build prompts that ask user for objects necessary to define actions
            via method calls to engile.Actions
         */
         actionAdder.buildConsumer(actions.getValue(), components.getValue());
    }

    @Override
    protected Object getData() {
        return null;
    }
}
