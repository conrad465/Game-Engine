package engine.components;


/**
 * Component interface. Components must provided both a parent ID (PID) and a unique identifying key.
 * 
 * @author fitzj
 */
public interface Component {
	public int getPID();
	public String getKey();
}
