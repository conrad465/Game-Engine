package authoring.views.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import engine.components.Component;
import java.util.function.Consumer;

import authoring.entities.Entity;
import authoring.forms.PropertiesComponentFormCollection;

/**
 * Opens up the Local Properties window so that an editor can edit certain features of an entity,
 * such as poison, health, velocity, etc. 
 * @author Collin Brown (cdb55)
 * @author Dylan Powers (ddp19)
 * @author Hemanth Yakkali(hy115)
 *
 */
public class LocalPropertiesView extends PropertiesView {

	private Consumer<List<Component>> onSubmit;
	private Entity entity;
	private String type;
	private PropertiesComponentFormCollection componentFormCollection;
	private Properties language = new Properties();

	/**
	 * Initialize the object with a given broadcast method
	 * @param entityNumber
	 */
	public LocalPropertiesView(Entity entity, Consumer<List<Component>> onSubmit) {
		super();
		this.entity = entity;
		this.type = entity.getPresetType();
		this.onSubmit = onSubmit;
	}

	/**
	 * Gets the title for the window.
	 * @return the title for this window.
	 */
	@Override
	public String title() {
		String[] title = this.getFormBundle().getString("Local").split(" ");
		return title[0]+" "+this.entity.getID()+" "+title[1]; //need to split to add level number in the middle
	}

	@Override
	public void setLanguage(Properties lang) {
		language = lang;
	}

	@Override
	protected void fill() {
		componentFormCollection = new PropertiesComponentFormCollection(entity, new String[] {""}, e-> {save((List<Component>) e);});
		this.getRoot().getChildren().add(componentFormCollection);
		componentFormCollection.setLanguage(language);
		List<String> componentsToAdd = new ArrayList<String>();
		for(Component c : this.entity.getComponentList()) {
			componentsToAdd.add(c.getKey());
		}
		componentFormCollection.fill(componentsToAdd);
		this.getRoot().getScene().getWindow().sizeToScene();
	}
	
	private void save(List<Component> componentsToAdd) {
		for(Component c : componentsToAdd) {
			System.out.println("Here: " + c.toString());
		}
		onSubmit.accept(componentsToAdd);
		this.makeSubmitAlert();
		this.close();
	}

}
