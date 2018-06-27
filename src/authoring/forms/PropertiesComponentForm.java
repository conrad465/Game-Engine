package authoring.forms;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Properties;
import java.util.function.Consumer;

import authoring.entities.data.PackageExplorer;
import authoring.factories.Element;
import authoring.factories.ElementFactory;
import authoring.factories.ElementType;
import engine.components.Component;
import engine.components.SingleDataComponent;
import engine.components.SingleStringComponent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * A wrapper class for forms that contains the label name and the text fields necessary, and can build components upon completion.
 * @author dylanpowers
 * @author Collin Brown(cdb55)
 *
 */
public class PropertiesComponentForm extends AbstractComponentForm implements ComponentForm {
	private int entity;

	/**
	 * Constructs the form with the given name and number of fields necessary, as determined by reflection.
	 * @param entity the entity that the component should be added to
	 * @param name the name of the component
	 * @param numFields the number of fields necessary for this component
	 */
	public PropertiesComponentForm(int eID, String name, Consumer onDelete) throws Exception {
		super(name, onDelete);
		entity = eID;
	}
	/**
	 * Constructs the form with the given name and number of fields necessary, as determined by reflection.
	 * @param name the name of the component
	 * @param existingValue the String form of the existing value of this component
	 * @throws Exception 
	 */
	public PropertiesComponentForm(int entity, String name, String existingValue, Consumer onDelete) throws Exception {
		this(entity, name, onDelete);
		this.field.setText(existingValue);
	}

	/**
	 * Builds the necessary component based upon the data that is inside of the text fields.
	 * Should be performed only when the user clicks the submit button.
	 * @return a component that accurately represents the data in this wrapper class
	 */
	public Component buildComponent() {
		if (!validComponent()) {
			System.out.println();
			return null;
		}
		String fullName =  COMPONENT_PREFIX + this.name;
		Object[] params = new Object[2];
		// first argument is always the entity ID
		params[0] = this.entity;
		try {
			Component comp = null;
			Class<?> clazz = Class.forName(fullName);
			Constructor<?> cons = clazz.getDeclaredConstructors()[0];
			if (SingleDataComponent.class.isAssignableFrom(clazz) && this.numFields > 0) {
				params[1] = Double.valueOf(this.field.getText());
				comp = (Component) cons.newInstance(params);
			} else if (SingleStringComponent.class.isAssignableFrom(clazz) && this.numFields > 0) {
				params[1] = this.field.getText();
				comp = (Component) cons.newInstance(params);
			} else {
				comp = (Component) cons.newInstance(this.entity);
			}
			
			return comp;
		} catch (Exception e) {
			LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
			return null;
		}
	}

	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public void setLanguage(Properties language) {
		// TODO Auto-generated method stub
	}
	@Override
	public void setValue(Object currentValue) {
		try {
			this.field.setText((String) currentValue);
		} catch(ClassCastException e) {
			this.field.setText(Double.toString((double) (currentValue)));
		}		
	}
}
