package engine.components;

/**
 * Data component representing acceleration in x direction of an entity
 * @author fitzj
 * @author Yameng Liu
 */
public class XAcceleration extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "XAcceleration";

	public XAcceleration(int pid, double data) {
		super(pid, data);
	}


	public String getKey() {
		return KEY;
	}
	
}
