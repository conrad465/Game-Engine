package authoring.entities;

import engine.components.presets.KoopaCollision;

/**
 * Enemy class that acts as a preset. Makes it easier to users to create an enemy without needing 
 * to manually add components.
 * @author Hemanth Yakkali(hy115)
 */
public class Enemy extends InteractableEntity {

	private final static String TYPE = "Enemy";

	public Enemy(int ID, String name) {
		super(ID);
		this.setName(name);
		this.setPresetType(TYPE);
		addDefaultComponent();
	}

	private void addDefaultComponent() {
		this.add(new KoopaCollision(this.getID()));
	}

}
