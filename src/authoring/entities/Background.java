package authoring.entities;

/**
 * Background class that acts as a preset. Makes it easier to users to create an enemy without needing 
 * to manually add components.
 * @author Hemanth Yakkali(hy115)
 */
public class Background extends NonInteractableEntity{

	private static final String TYPE = "Background";

	public Background(int ID, String name) {
		super(ID);
		this.setName(name);
		this.setPresetType(TYPE);
	}

}
