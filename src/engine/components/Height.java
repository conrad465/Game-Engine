package engine.components;

/**
 * Data component representing an entity's height.
 * @author fitzj
 *
 */
public class Height extends SingleDataComponent  implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "Height";
	
	public Height(int pid, double data) {
		super(pid, data);
	}

	public String getKey() {
		return KEY;
	}
	
}

