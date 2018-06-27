package voogasalad_oneclassonemethod;

import java.util.List;

/**
 * This class defines method that only the GAE will use. These are things that we would expect users
 * to want to do from within our module only.
 * @author dylanpowers
 *
 */
public interface GameAuthoringEnvironmentInternal {

	/**
	 * Load an existing game into the editor.
	 * @return a DataGameState object representing an existing game
	 */
	public GameState load();
	
	/**
	 * Look at the state of the current game and save it into a DataGameState object.
	 * @return a DataGameState object representing the current state of the object being edited.
	 */
	public GameState save();
	
	/**
	 * Add a game object to the editing environment. Makes use of the DragAndDropDynamicYoutility.
	 */
	public void addGameObject();
	
	/**
	 * Used to open the PropertiesView editor that allows the user to change the properties of a placed object.
	 */
	public void editObjectComponents();
	
	/**
	 * Add a new level to the existing game project, and update the DataGameState.
	 */
	public void addLevel();
	
	/**
	 * Get the list of the possible components that can be added to an object. This improves upon flexibility.
	 * @return
	 */
	public List<String> getPossibleComponents();
}
