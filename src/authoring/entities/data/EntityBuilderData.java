package authoring.entities.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class EntityBuilderData {

	private Map<Class, Object[]> componentAttributes = new HashMap<>();

	private final static String COMPONENT_PREFIX = "engine.components.";
	private final String NAME_ERROR_MESSAGE = "There must be a value in the \"Name\" field.\n";
	private final String COMPONENT_ERROR_MESSAGE = "The component %s does not exist. Please check the package engine.components to make sure that the component has been created.\n";
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public EntityBuilderData() {

	}

	/**
	 * Add a component to the entityBuilderData
	 * @param c Class of component to be created
	 * @param val value of said component
	 */
	public void setComponent(Class c, String val) {
		try {
			if (componentAttributes.containsKey(c)) {
				componentAttributes.remove(c);
			}
			componentAttributes.put(c, new Object[] {val});
		} catch (Exception e) {
			throw new AuthoringException(COMPONENT_ERROR_MESSAGE, AuthoringAlert.SHOW, c.getName());
		}
	}


	/**
	 * Saves an entity
	 * @param list
	 * @throws Exception
	 */
	public void save(List<Object[]> list) throws Exception {
		System.out.println(list.size());
		for(Object[] component : list) {
			if(component != null) {
				try {
					System.out.println(component[0].toString() + ":" + component[1].toString());
					componentAttributes.put(Class.forName(COMPONENT_PREFIX + component[0]), (Object[]) component[1]);
				} catch (Exception e) {
					throw new AuthoringException(COMPONENT_ERROR_MESSAGE, AuthoringAlert.SHOW, component);
				}
			}
		}
		EntitySaver saver = new EntitySaver();
		try {
			Object l = this.componentAttributes.get(engine.components.Name.class)[0];
		} catch(Exception k){
			AuthoringException e = new AuthoringException(NAME_ERROR_MESSAGE, AuthoringAlert.SHOW);
			k.printStackTrace();
		}

		saver.writeXML(componentAttributes, (String) this.componentAttributes.get(engine.components.Name.class)[0]);
	}

	/**
	 * Gets the type of the entity being created
	 * @return String Entity type to be created
	 */
	public String getType() {
		return (String) this.componentAttributes.get(engine.components.Type.class)[0];
	}

	/**
	 * Returns a map<Class, Object[]> that represents all the components of a new entity
	 * @return map<Class, Object[]>
	 */
	public Map<Class, Object[]> getComponentAttributes() {
		return componentAttributes;
	}

}
