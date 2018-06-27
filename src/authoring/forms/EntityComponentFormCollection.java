package authoring.forms;

import java.util.ArrayList;
import java.util.function.Consumer;

import authoring.entities.Entity;


/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class EntityComponentFormCollection extends AbstractComponentFormCollection {
	
	
	public EntityComponentFormCollection() {
		super(EntityComponentForm.class);
	}
	
	public EntityComponentFormCollection( String[] newExceptions, Consumer onSave) {
		super(newExceptions, onSave, EntityComponentForm.class);

	}

	@Override
	protected void save(Consumer onSave) {
		ArrayList<Object[]> componentsToAdd = new ArrayList<Object[]>();
		for(ComponentForm cf : this.getActiveForms()) {
			Object[] component = ((EntityComponentForm) cf).buildComponent();
			componentsToAdd.add(component);	
		}
		onSave.accept(componentsToAdd);
		
	}

	@Override
	protected boolean hasCurrentValue(String componentName) {
		return false;
	}

	@Override
	protected Object getCurrentValue(String componentName) {
		return null;
	}

	@Override
	protected void delete(ComponentForm cf) {
		this.getActiveForms().remove(cf);
		this.getChildren().remove(cf);
	}
}

