package authoring.forms;

import java.util.ArrayList;
import java.util.function.Consumer;

import authoring.entities.Entity;
import engine.components.Component;
import engine.components.DataComponent;
import engine.components.StringComponent;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class PropertiesComponentFormCollection extends AbstractComponentFormCollection {
	Entity entity;
	private int currentRow;
	
	public PropertiesComponentFormCollection(Entity en, String[] newExceptions, Consumer onSave) {
		super(newExceptions, onSave, PropertiesComponentForm.class);
		entity = en;
		entityID = entity.getID();
	}


	@Override
	protected void save(Consumer onSave) {
		ArrayList<Component> componentsToAdd = new ArrayList<Component>();
		for(ComponentForm cf : this.getActiveForms()) {
			try {
			Component c = (Component) ((PropertiesComponentForm) cf).buildComponent();
			if(c.equals(null)) {
				continue;
			}
			componentsToAdd.add(c);		
			} catch(NullPointerException e) {
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		onSave.accept(componentsToAdd);
		
	}


	@Override
	protected boolean hasCurrentValue(String componentName) {
		for(Component c: entity.getComponentList()) {
			if(c.getKey().equals(componentName)) {
				return true;
			}
		}
		return false;
		
	}


	@Override
	protected Object getCurrentValue(String componentName) {
		Component component = entity.get(componentName);
		if(DataComponent.class.isAssignableFrom(component.getClass())) {
			return ((DataComponent) component).getData();
		} else if(StringComponent.class.isAssignableFrom(component.getClass())) {
			return ((StringComponent) component).getData();
		}
		return null;
		
	}

	@Override
	protected void delete(ComponentForm cf) {
		System.out.println("yuh" + cf.getName());
		this.getActiveForms().remove(cf);
		if(hasCurrentValue(cf.getName())) {
			this.entity.remove(getCurrentComponent(cf.getName()));
		}
		this.getChildren().remove(cf);
		
	}


	private Component getCurrentComponent(String name) {
		for(Component c : entity.getComponentList()) {
			if(c.getKey().equals(name)) {
				return c;
			}
		}
		return null;
	}


}
