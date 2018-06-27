package authoring.gamestate;

import java.util.List;

import authoring.entities.Entity;
import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import data.DataWrite;
import engine.components.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * Keeps track of the current state of the authoring environment, so that the author 
 * can save/load games dynamically. 
 * @author Dylan Powers
 * @author Hemanth Yakkali (hy115)
 */
public class GameState {

	/**
	 * This object should only be constructed once, upon initialization of the authoring environment.
	 * It will then continue to keep track of the current state of the game by using the update method below.
	 */
	private static List<Level> state;
	private static String name;

	public GameState() {
		state = new ArrayList<>();
	}

	/**
	 * Saves the GameState into an XML file
	 */
	public void save() {
		try {
			DataWrite.saveFile(this, name);
		} catch (Exception e) {
			throw new AuthoringException("Error with saving game file!",AuthoringAlert.SHOW);
		}
	}

	/**
	 * Updates the current state by adding a new level object to the list of levels.
	 * @param level The level object to add
	 */
	public void addLevel(Level level) {
		if(!state.contains(level)) {
			state.add(level);
		}
	}

	/**
	 * Updates the current state my removing a level object.
	 * param level The level object to remove
	 */
	public void removeLevel(Level level) {
		if(state.contains(level)) {
			state.remove(level);
		}
	}

	/**
	 * @return GameState, which is a list of levels
	 */
	public List<Level> getLevels() {
		return state;
	}

	/**
	 * Set the name of the current game.
	 * @param name the name of the game to set
	 */
	public void setName(String name) {
		GameState.name = name;
	}

	/**
	 * Get the current name of the game. Useful for finding certain objects in directories.
	 * @return the name of the current game represented by this state object
	 */
	public static String getName() {
		return GameState.name;
	}


	public static Entity entity(int a) throws NullPointerException{
		try{
			for(Level lev : state){
				for(Entity e :lev.getEntityList())
					if (e.getID()==a)
						return e;
			}
		}
		catch(NullPointerException e){
			throw new NullPointerException();
		}
		return null;
	}
}
