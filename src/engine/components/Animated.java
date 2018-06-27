package engine.components;


/**
 * 
 * @author fitzj
 *
 */
public class Animated extends SingleStringComponent implements Component, StringComponent, ReadStringComponent {

	public static final String KEY = "Animated";
	
	public Animated(int pid, String filepath) {
		super(pid, filepath);
	}

	@Override
	public String getKey() {
		return KEY;
	}

}
