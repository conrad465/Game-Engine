package engine.components;

/**
 * Flag component to delineate a player character
 * @author fitzj
 *
 */
public class Player extends FlagComponent implements Component {

	public static final String KEY = "Player";
	
	public Player(int pid) {
		super(pid);
	}

	public String getKey() {
		return KEY;
	}


}
