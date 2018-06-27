package engine.components;

/**
 * Data class representing the width of an entity
 * @author fitzj
 *
 */
public class Width extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "Width";

	public Width(int pid, double data) {
		super(pid, data);
	}

	public String getKey() {
		return KEY;
	}
	
}
