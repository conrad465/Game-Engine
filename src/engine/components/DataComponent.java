package engine.components;

/**
 * Interface for a component that contains a double. This interface allows both reading and writing of the element's data.
 * Meant for authoring environment, which has complete control over component data.
 * @author fitzj
 *
 */
public interface DataComponent {
	
	/**
	 * Gets value from the component
	 * @return	Data value
	 */
	public double getData();
	
	/**
	 * Sets data of component
	 * @param data	New value of data
	 */
	public void setData(double data);
	
	/**
	 * Returns component's unique key
	 * @return	Key
	 */
	public String getKey();
	
	/**
	 * Returns component's parent ID
	 * @return	PID
	 */
	public int getPID();
	
	/**
	 * Sets component's parent ID
	 * @param pid	New PID
	 */
	public void setPID(int pid);	
}
