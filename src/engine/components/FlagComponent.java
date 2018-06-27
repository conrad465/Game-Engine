package engine.components;

/**
 * Component class with no data: instead, it just marks an entity that may have special interactions. 
 * Somewhat goes against typical ECS design, but necessary for pre-established systems.
 * @author fitzj
 *
 */
public abstract class FlagComponent implements Component {

	private int pid;
	
	public FlagComponent(int pid) {
		this.pid = pid;
	}
	
	public int getPID() {
		return pid;
	}
	
	public void setPID(int pid) {
		this.pid = pid;
	}
	
}
