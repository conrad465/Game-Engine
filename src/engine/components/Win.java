package engine.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Win extends FlagComponent implements Component {
	public static final String KEY = "Win";
	
	private BooleanProperty winStatus = new SimpleBooleanProperty();
	
	public Win(int pid) {
		super(pid);
		winStatus.setValue(false);
	}

	public String getKey() {
		return KEY;
	}

	public void win() {
		winStatus.setValue(true);

	}

	public BooleanProperty getWinStatus () {
		return winStatus;
	}

}
