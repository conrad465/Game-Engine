package authoring.entities;

import engine.components.Component;

public class BlankEntity extends Entity{

	public BlankEntity(int ID) {
		super(ID);
	}

	@Override
	public void add(Component c) {
		components.add(c);
	}

}
