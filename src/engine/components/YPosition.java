package engine.components;

/**
 * Data component representing y position of entity
 * @author fitzj
 * @author Yameng Liu
 */
public class YPosition extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "YPosition";

	
	public YPosition(int pid, double data) {
		super(pid, data);
	}


	public String getKey() {
		return KEY;
	}
	
}
