package engine.components;

/**
 * Data component representing x position of entity
 * @author fitzj
 * @author Yameng Liu
 */
public class XPosition extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "XPosition";

	public XPosition(int pid, double data) {
		super(pid, data);
	}

	public String getKey() {
		return KEY;
	}
	
}
