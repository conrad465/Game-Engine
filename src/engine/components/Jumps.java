package engine.components;

/**
 * Data component tracking number of jumps left for an entity.
 * Stores max number of jumps so it can be reset on a condition.
 * Example: Every jump, data is decremented, on top collision with block, data is reset.
 * @author fitzj
 *
 */
public class Jumps extends SingleDataComponent implements Component, DataComponent, ReadDataComponent {

	public static final String KEY = "Jumps";
	
	private int max;
	
	public Jumps(int pid, int max) {
		super(pid, max);
		this.max = max;
	}

	/**
	 * Resets data to initial (max) value
	 */
	public void reset() {
		setData(max);
	}
	
	/**
	 * Sets max jump value, and resets data
	 * @param m	New max value
	 */
	public void setMax(int m) {
		max = m;
		reset();
	}
	
	@Override
	public String getKey() {
		return KEY;
	}

	
	
}
