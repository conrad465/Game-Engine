package authoring.entities;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import engine.components.Component;
import engine.components.EntityType;
import engine.components.Height;
import engine.components.Sprite;
import engine.components.Width;
import engine.components.XPosition;
import engine.components.YPosition;
import javafx.scene.image.ImageView;


/**
 * Super class to represent the top level of the entity chain. 
 * @author Dylan Powers
 * @author Hemanth Yakkali
 */
public abstract class Entity extends ImageView {
	
	private int ID;
	public final static int ENTITY_WIDTH = 50;
	public final static int ENTITY_HEIGHT = 50;
	public final static String ERROR_MESSAGE = "Error creating entity. Please try again.";
	protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	protected List<Component> components;
	private boolean entityType;
	private String presetType;
	private String name;

	/**
	 * Set the ID of this entity
	 * @param ID the ID of this entity
	 */
	public Entity(int ID) {
		System.out.println("making entity!");
		this.ID = ID;
		this.components = new ArrayList<>();
		this.setFitWidth(ENTITY_WIDTH);
		this.setFitHeight(ENTITY_HEIGHT);
		Consumer<List<Component>> onSubmit = componentsToAdd -> {
			for (Component c : componentsToAdd) {
				this.add(c);
			}
		};
		this.setOnMouseDragged(e -> {
			this.setPosition(e.getX() + this.getLayoutX() - this.getFitWidth()/2, e.getY() + this.getLayoutY() - this.getFitHeight()/2);
			e.consume();
		});
	}
	
	/**
	 * 
	 * @return Unique ID of the entity
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * 
	 * @param filename File path of the sprite image
	 * @throws FileNotFoundException
	 */
	protected void setSprite(String filename) throws FileNotFoundException {
		Sprite spriteComponent = new Sprite(this.getID(),filename);
		this.add(new Sprite(this.getID(),filename));
		this.setImage(spriteComponent.getImage().getImage());
	}
	
	/**
	 * 
	 * @param x X position
	 * @param y Y position
	 */
	public void setPosition(double x, double y) {
		this.add(new XPosition(this.getID(), x));
		this.add(new YPosition(this.getID(), y));
		this.setLayoutX(x);
		this.setLayoutY(y);
	}

	/**
	 * 
	 * @param width Width of entity
	 * @param height Height of entity
	 */
	protected void setDimension(double width, double height) {
		this.add(new Width(this.getID(),width));
		this.add(new Height(this.getID(),height));
	}
	
	/**
	 * Set the type of an entity
	 * @param type the type to set the entity to
	 */
	protected void setEntityType(String type) {
		this.add(new EntityType(this.getID(), type));
	}
	
	/**
	 * Add a given component to this entity. 
	 * param component
	 */
	public abstract void add(Component c);
	
	/**
	 * Add all of the components in a given list to this entity by iterating through the list.
	 * @param compsToAdd a collection of the components to add
	 */
	public void addAll(List<Component> compsToAdd) {
		for (Component comp : compsToAdd) {
			this.add(comp);
		}
	}

	/**
	 * Checks (explicitly) by name if the current entity already contains this component.
	 * @param name the name of the component to check
	 * @return true iff the component already belongs to this entity
	 */
	public boolean contains(String name) {
		for (Component existing : this.components) {
			if (existing.getKey().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get a component by name.
	 * @param name the name of the component needed
	 * @return component defined by this string name
	 */
	public Component get(String name) {
		for (Component c : this.components) {
			if (c.getKey().equals(name)) {
				return c;
			}
		}
		return null;
	}	
	
	/**
	 * 
	 * @param type Interact type, either interactable or noninteractable
	 */
	public void setInteractable(boolean bool) {
		this.entityType = bool;
	}
	
	/**
	 * 
	 * @return Interact type, either interactable or noninteractable
	 */
	public boolean getInteractable() {
		return this.entityType;
	}
	
	/**
	 * 
	 * @param type Preset type of the entity, i.e. user, block, etc.
	 */
	public void setPresetType(String type) {
		this.presetType = type;
	}
	
	/**
	 * 
	 * @return Preset type of the entity, i.e. user, block, etc.
	 */
	public String getPresetType() {
		return this.presetType;
	}
	
	/**
	 * 
	 * @param name Name of the entity
	 */
	protected void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return Name of the entity
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the list of components for this entity
	 */
	public List<Component> getComponentList() {
		return this.components;
	}

	/**
	 * @param c Component class to remove
	 */
	public void remove (Component c) {
		components.remove(c);
	}

}
