
/**
 * This interface allows for all types of heads-up displays to be created
 * based on the type of game that is being created. The method signature 
 * updateHUD() allows for the values of the HUD to be accessed and changed,
 * when new values are changed in the game state data.
 * @author Ryan Fu & Scott Pledger
 *
 */

public interface IHud {
	
	/**
	 * When this method is called, it changes the values that are currently
	 * displayed on the heads up menu by reading values from the game state data.
	 * This API will allow for event listeners to update the values displayed on all types
	 * of HUD's which differ based on the game selected to be played.
	 */
	public void updateHUD();
	
}
