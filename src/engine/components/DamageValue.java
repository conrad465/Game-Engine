package engine.components;

/**
 * Damage value to apply to an entity. If the PID is the same as the containing entity's, it is not applied, but rather
 * "launched" to another entity on a collision or interaction. 
 * 
 * @author fitzj
 */
public class DamageValue extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "DamageValue";

	public DamageValue(int pid, double data) {
		super(pid, data);
	}

	public String getKey() {
		return KEY;
	}
}
