package engine.components;

/**
 * Interface for a data component that is read-only. Intended for use in the player to allow it to read data,
 * but not alter it.
 * 
 * @author fitzj
 */
public interface ReadDataComponent {
	public double getData();
	public int getPID();
	public String getKey();
}
