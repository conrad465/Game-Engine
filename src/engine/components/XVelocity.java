package engine.components;

/**
 * Data component representing x velocity of entity
 * @author fitzj
 * @author Yameng Liu
 */
public class XVelocity extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "XVelocity";

	public XVelocity(int pid, double data) {
		super(pid, data);
	}


	public String getKey() {
		return KEY;
	}
	
}
