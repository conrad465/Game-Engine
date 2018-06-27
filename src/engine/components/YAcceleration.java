package engine.components;

/**
 * Data component representing y acceleration of entity
 * @author fitzj
 * @author Yameng Liu
 */
public class YAcceleration extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "YAcceleration";

	
	public YAcceleration(int pid, double data) {
		super(pid, data);
	}


	public String getKey() {
		return KEY;
	}
	
}
