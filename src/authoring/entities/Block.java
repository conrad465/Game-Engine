package authoring.entities;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.systems.collisions.CollisionDirection;

/**
 * Block class that acts as a preset. Makes it easier to users to create an enemy without needing 
 * to manually add components.
 * @author Hemanth Yakkali(hy115)
 *
 */
public class Block extends InteractableEntity {

	private final static String TYPE =  "Block";

	/**
	 * Initialize 
	 * @param ID
	 * @param name
	 */
	public Block(int ID, String name) {
		super(ID);
		this.setName(name);
		//addDefaultComponents();
	}

	private void addDefaultComponents() {
		Collidable c = new Collidable(this.getID());
		//c.setOnDirection(CollisionDirection.Bot, Actions.bounce(0));
		this.add(c);
	}

}
