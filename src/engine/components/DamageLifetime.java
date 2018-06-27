package engine.components;

/**
 * Damage Lifetime component for damage over time (that is, not instant). Value should be a double representing the amount of time 
 * over which the damage should be applied.
 * 
 * @author fitzj
 */
public class DamageLifetime extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "DamageLifetime";
	
	public DamageLifetime(int pid, double data) {
		super(pid, data);
	}

	public String getKey() {
		return KEY;
	}
	
}
