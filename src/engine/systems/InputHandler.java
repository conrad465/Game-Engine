package engine.systems;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import engine.components.Component;
import engine.components.KeyInput;
import javafx.scene.input.KeyCode;

/**
 * System which delegates key codes pressed by the user to the components which contain that key code such that
 * they can then perform their actions
 *
 * @author cndracos, sv116, fitzj
 */
public class InputHandler extends AbstractSystem implements ISystem {

	private Set<KeyCode> activeCodes;

	public InputHandler() {
		super();
		activeCodes = new HashSet<>();
	}

	@Override
	public void addComponent(int pid, Map<String, Component> components) {
		if (this.checkComponents(components, KeyInput.KEY)) {
			this.directAddComponent(pid, components);
		}
	}


	@Override
	public void execute(double time) {
		for (int id : this.getActives()) {
			Map<String, Component> components = this.getHandled().get(id);
			KeyInput k = (KeyInput) components.get(KeyInput.KEY);
			k.action(activeCodes, components);
		}
	}

	/**
	 * Removes code from activeCode list when user releases key
	 * @param code	Key being released
	 */
	public void removeCode(KeyCode code) {
		if(activeCodes.contains(code)) {
			activeCodes.remove(code);
		}
	}

	/**
	 * Adds a code to the activeCode list as long as the user is pressing that code
	 * @param code the key the user is currently pressing
	 */
	public void addCode(KeyCode code) {
		activeCodes.add(code);
	}

}
