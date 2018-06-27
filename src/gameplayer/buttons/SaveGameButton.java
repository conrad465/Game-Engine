package gameplayer.buttons;
import gameplayer.menu.PauseMenu;
import javafx.scene.control.Button;

/**
 * Button that saves the current state of the game
 * @author Ryan
 *
 */
public class SaveGameButton extends Button implements IGamePlayerButton{
	
	private final String BUTTON_NAME = "Save Game";
	private PauseMenu pauseMenu;
	
	public SaveGameButton(PauseMenu p) {
		pauseMenu = p;
		this.setText(BUTTON_NAME);
		this.setOnAction(e -> setEvent());
	}

	/**
	 * Saves the Current State of the Game
	 */
	public void setEvent() {
		 pauseMenu.saveGame();
	}
}
