package engine.components;

import javafx.scene.input.KeyCode;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This is the component class that allows the designer to assign an action to an entity based on a key code/key input,
 * such that the user can decide what should be done by the entity either by a preset selection or writing in their
 * own code
 *
 * @author cndracos, fitzj
 */
public class KeyInput implements Component, BehaviorComponent {

	private Map<KeyCode, Consumer<Map<String, Component>>> codes;

	public static final String KEY = "KeyInput";
	
	private int pid;
	
	public KeyInput(int pid) {
		this.pid = pid;
		codes = new HashMap<>();
	}

	public boolean containsCode (KeyCode key) {
		return codes.containsKey(key);
	}

	/**
	 * This method allows the user to allow a code-action pairing to a specific
	 * entity's keyInput component, allowing multiple actions to one entity
	 *
	 * @param code the keycode that will trigger the action
	 * @param con the action that happens when the key is pressed
	 */
	public void addCode (KeyCode code, Consumer<Map<String, Component>> con) {
		codes.put(code, con);
	}
	
	public void action(Set<KeyCode> codeSet, Map<String, Component> entityMap) {
        codeSet.forEach(key -> {
        	if(codes.containsKey(key)) {
        		if(codes.get(key) != null) {
        			codes.get(key).accept(entityMap);
        		}
        	}
        });
	}

	
	public String getKey() { 
		return KEY; 
	}

	@Override
	public void addBehavior(Object identifier, Consumer<Map<String,Component>> con) {
		if(identifier instanceof KeyCode) {
			this.addCode((KeyCode) identifier, con);
		}
	}

	@Override
	public void addBehavior(Object identifier, BiConsumer<Map<String,Component>,Map<String,Component>> bic) {
		if(identifier instanceof KeyCode) {
			this.addCode((KeyCode) identifier, entity -> {
				bic.accept(entity, null);
			});
		}
	}
	
	

	public int getPID() {
		return pid;
	}

	@Override
	public void appendBehavior(Object identifier, Consumer<Map<String, Component>> con) {
		if(identifier instanceof KeyCode && codes.containsKey(identifier)) {
			Consumer<Map<String,Component>> old = codes.get(identifier);
			codes.put((KeyCode) identifier, e1 -> {
				old.accept(e1);
				con.accept(e1);
			});
		} else {
			this.addBehavior(identifier, con);
		}
	}

	@Override
	public void appendBehavior(Object identifier, BiConsumer<Map<String, Component>, Map<String, Component>> bic) {
		if(identifier instanceof KeyCode && codes.containsKey(identifier)) {
			this.appendBehavior(identifier, e1 -> {
				bic.accept(e1, new HashMap<>());
			});
		} else {
			this.addBehavior(identifier, bic);
		}
	}
}


