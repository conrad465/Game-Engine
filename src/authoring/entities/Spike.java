package authoring.entities;

import engine.components.presets.SpikeCollision;

public class Spike extends InteractableEntity {

	private final static String TYPE =  "Spike";

	public Spike(int ID, String name) {
		super(ID);
		this.setName(name);
		this.setPresetType(TYPE);
		this.add(new SpikeCollision(this.getID()));
	}

}
