package engine.components;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import java.util.function.Consumer;

/**
 * Component to be added to enemies that have 'custom' actions (following the player), moving in a circle, etc.,
 * to be implemented by the designer by writing in a method for the entity to perform.
 *
 * @author cndracos
 */
public class AI implements Component, BehaviorComponent {

    public static final String KEY = "AI";

    private Consumer<Map<String, Component>> action ;

    private int pid;
    
    public AI (int pid) {
        this.pid = pid;
         action = e->{};
    }

	/**
	 * Sets the action for an AI to perform on its own components
	 * @param action
	 */
	public void setAction (Consumer<Map<String, Component>> action) {
        this.action = action;
    }

	/**
	 * Does the action on its components based off of the logic of the actions
	 * @param components
	 */
	public void doAction(Map<String, Component> components) {
        action.accept(components);
    }

	public String getKey() {
		return KEY;
	}

	public int getPID() {
		return pid;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addBehavior(Object identifier, Consumer con) {
		setAction(con);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addBehavior(Object identifier, BiConsumer bic) {
		setAction(entity -> {
			bic.accept((Map<String, Component>) entity, new HashMap<>());
		});
	}

	@Override
	public void appendBehavior(Object identifier, Consumer<Map<String, Component>> con) {
		Consumer<Map<String, Component>> old = action;
		action = entity -> {
			old.accept(entity);
			con.accept(entity);
		};
	}

	@Override
	public void appendBehavior(Object identifier, BiConsumer<Map<String, Component>, Map<String, Component>> bic) {
		appendBehavior(identifier, entity -> {
			bic.accept(entity, new HashMap<>());
		});
	}
}
