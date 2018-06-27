import javafx.scene.input.KeyCode;

/**
 * Use Case for when the escape key is pressed to bring up or close the Pause Menu on the Game Player scene.
 * @author Ryan Fu
 */
public class GamePlayerUseCase {

	/**
	 * The method below is an example method that handles how the pause menu appears 
	 * by calling the toggleDisplay() API to change the visibility of a gamePlayer menu
	 * @param Keycode code
	 */
	private void handleKeyInput (KeyCode code) {
		if ((code == KeyCode.ESCAPE) {   //"Escape" Key is pressed
			pauseMenu.toggleDisplay();  //toggleDisplay() API that changes the visibility of the pauseMenu
		}
	}
}