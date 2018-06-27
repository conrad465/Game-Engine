package authoring.entities;

import authoring.forms.PropertiesComponentForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class ComponentAdder {

    Entity entity;

    public ComponentAdder(Entity entity){
        this.entity=entity;
        createContextMenuList();
    }


    private ObservableList<MenuItem> createContextMenuList() {
        ObservableList<MenuItem> menu = FXCollections.observableArrayList();
        ResourceBundle compontents = ResourceBundle.getBundle("authoring.resources.Components");
        for(String component : compontents.keySet()){
            MenuItem menuItem = new MenuItem(component);
            menuItem.setOnAction(e->{

                Group root = new Group();
				try {
					root = new Group(new PropertiesComponentForm(entity.getID(),component, null));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                Stage popup = new Stage();
                popup.setScene(new Scene(root));
                popup.show();
            });
            menu.add(menuItem);
        }
        return menu;
    }
}
