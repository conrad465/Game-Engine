package engine.systems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import engine.components.Component;
import engine.exceptions.EngineException;

/**
 * Abstract system class to make implementing systems much easier.
 * Only addComponent and execute need to be overridden, to pick which components the system needs
 * and determine the system logic.
 * @author fitzj
 */
public abstract class AbstractSystem implements ISystem {

	/**
	 * Map of all entities being handled by the system
	 */
	private Map<Integer,Map<String,Component>> handledComponents;
	
	/**
	 * Map of rendered entities being currently processed
	 */
	private Set<Integer> activeComponents;
	
	public AbstractSystem() {
		handledComponents = new ConcurrentHashMap<>();
		activeComponents = new HashSet<>();
	}
	
	/**
	 * Adds an entity to the handled components list if it meets system's requirements
	 * @param pid			ID of entity
	 * @param components	Map representing entity
	 */
	@Override
	public abstract void addComponent(int pid, Map<String,Component> components);
	
	/**
	 * Special method for subclasses to add a component directly to handled map,
	 * if it passes the subsystem's requirements
	 * @param pid			ID of entity
	 * @param components	Map representing entity
	 */
	protected void directAddComponent(int pid, Map<String,Component> components) {
		handledComponents.put(pid, components);
	}

	/**
	 * Special method for subclasses to check if an entity contains certain components,
	 * for an indefinite number of components
	 * @param map	Entity to check
	 * @param keys	List of component keys to check
	 * @return		True if entity contains all specified keys
	 */
	protected boolean checkComponents(Map<String,Component> map, String...keys) {
		for(String key : keys) {
			if(!map.containsKey(key)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Removes entity from handled map
	 * @param pid	ID of entity to remove
	 */
	@Override
	public void removeComponent(int pid) {
		handledComponents.remove(pid);
	}

	/**
	 * Sets active entities to render
	 * @param actives	Set of active entities
	 */
	@Override
	public void setActives(Set<Integer> actives) {
		Set<Integer> myActives = new HashSet<>(actives);
		myActives.retainAll(handledComponents.keySet());
		activeComponents = myActives;
	}

	/**
	 * Calls on system to modify components accordingly, given a time period
	 * @param time	Amount of time passed since last execution
	 * @throws EngineException 
	 */
	@Override
	public abstract void execute(double time) throws EngineException;
	
	/**
	 * Special method for subsystems to get handled components
	 * @return	Handled components
	 */
	protected Map<Integer,Map<String,Component>> getHandled() {
		return handledComponents;
	}
	
	/**
	 * Special method for subsystem to get active entities
	 * @return	Active entities
	 */
	protected Set<Integer> getActives() {
		return activeComponents;
	}
	
	public void setActives() {
		activeComponents = handledComponents.keySet();
	}

}
