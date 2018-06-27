package engine.components;

/**
 * @author Yameng Liu
 */
public class EntityType extends SingleStringComponent  implements Component, StringComponent, ReadStringComponent {

	public static final String KEY = "EntityType";
	
	public EntityType(int pid, String data) {
		super(pid, data);
	}

	public String getKey() {
		return "EntityType";
	}

}
