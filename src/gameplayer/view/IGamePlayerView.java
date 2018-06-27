package gameplayer.view;
import java.util.Map;

import authoring.gamestate.Level;
import engine.components.Component;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public interface IGamePlayerView {

	/**
	 * Obtains heads-up display status map for each level in a game.
	 * @return Map<Integer, Map<String, Boolean>> 
	 */
	public Map<Integer, Map<String, Boolean>> getHudPropMap();
	
	/**
	 * Converts Map of levels to its Entities to Integers to Entities to make calling a particular level easier
	 * @return Map<Integer, Map<Integer,Map<String,Component>>> 
	 */
	public Map<Integer, Map<Integer,Map<String,Component>>> levelToInt(Map<Level, Map<Integer,Map<String,Component>>> levelMap);
	
	/**
	 * returns the display panes for each level
	 * @return
	 */
	public Map<Integer, Pane> getGameLevelDisplays();
	
	/**
	 * Connects View to Engine Systems (GameInitializer, InputHandler, RenderManager, SystemManager)
	 */
	public void initializeGameView();
	
	/**
	 * Set which level you want to display on the screen
	 * @param activelevel - level you wish to display
	 */
	public void setActiveLevel(int activelevel);
	
	/**
	 * Delegates actions and components to the systems from the view
	 * @param time
	 */
	public void execute (double time);
	
	/**
	 * Renders all changes to the objects sprite
	 */
	public void render();
	
	/**
	 * Pass user input to the engine
	 * @param code
	 */
	public void setInput(KeyCode code);
	
	/**
	 * When the key is released, remove it from the input handler
	 * @param code
	 */
	public void removeInput (KeyCode code);
	
	/**
	 * Saves the current game state to a new file
	 */
	public void saveGame();
	
	/**
	 * Updates the view of the Pane so that it scrolls with the player's movement. Allows for some free movement without scrolling
	 * @param gameRoot
	 */
	public void updateScroll(Pane gameRoot);
	
}
