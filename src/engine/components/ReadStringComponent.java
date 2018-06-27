package engine.components;

/**
 * Interface for a string component that is read-only. Intended for use in the player to allow it to read data,
 * but not alter it.
 * 
 * @author fitzj
 */
public interface ReadStringComponent {
	public String getData();
	public String getKey();
	public int getPID();
}
