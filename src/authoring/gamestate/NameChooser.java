package authoring.gamestate;

import java.util.function.Consumer;
/**
 * Interface to represent choosing a name, either entering a new one or picking an existing one.
 * @author Dylan Powers
 *
 */
public interface NameChooser {

	/**
	 * Tells what to do when the window is closed.
	 * @param onClose the consumer representing what to do when the window is closed.
	 */
	public void showAndWait(Consumer<String> onClose);
}
