package engine.components;

/**
 * Data component representing y velocity of entity
 * @author fitzj
 * @author Yameng Liu
 */
public class YVelocity extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {
	
	public static final String KEY = "YVelocity";

	
	public YVelocity(int pid, double data) {
		super(pid, data);
	}


	public String getKey() {
		return KEY;
	}
	
}
