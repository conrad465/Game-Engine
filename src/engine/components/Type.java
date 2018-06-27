package engine.components;


/**
 * Class to represent the type of an entity. Refer to the package {@code authoring.entities} for more information.
 * @author Dylan Powers
 *
 */
public class Type extends SingleStringComponent implements Component, StringComponent, ReadStringComponent {

	public static final String KEY = "Type";

	public Type(int pid, String type) {
		super(pid, type);
	}

	public String getKey() {
		return KEY;
	}


}
