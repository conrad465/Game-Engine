package voogasalad_oneclassonemethod;

/**
 * This is an interface to represent how other modules will interact with our part of the program.
 * Since our functionality is really centered on the editing itself, rather than interactions, this
 * module only defines how we will pass the {@code DataGameState} object to the Data team.
 * @author dylanpowers
 *
 */
public interface GameAuthoringEnvironmentExternal {

	/**
	 * Get the current state of the game to send to the data team.
	 * @return a DataGameState object that represents the current state of the game
	 */
	public GameState getState();
}
