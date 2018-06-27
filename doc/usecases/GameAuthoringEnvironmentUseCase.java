
/**
 * @author Hemanth Yakkali
 * The use case presented below is to add a game object (entity) to the current game state
 * and then save that game state to a file that can then be loaded by the authoring environment
 * or the game player.
 */
public class GameAuthoringEnvironmentUseCase {
	
	Block block = new Block(); //create a new generic block
	addGameObject(block); //add block to the current game state
	save(); //saves current game state into a loadable file

}
