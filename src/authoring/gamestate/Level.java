package authoring.gamestate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import authoring.entities.Entity;

/**
 * Class that holds level properties such as level number, level information, etc. Also holds 
 * list of entities and maps for HUD properties and Global properties.
 * @author Hemanth Yakkali(hy115)
 *
 */
public class Level {

	private int levelNum;
	private String levelInfo;
	private double levelTime;
	
	public final static String ERROR_MESSAGE = "Error creating level. Please try again or consult documentation.";
	
	@XStreamOmitField
	private transient List<Entity> entityList;
	private Map<String,Boolean> HUDprops;
	private Map<String,String> GProps;

	public Level(int levelNum) {
		this.levelNum = levelNum;
		this.entityList = new ArrayList<>();
		this.HUDprops = new HashMap<>();
		this.GProps = new HashMap<>();
	}

	/**
	 * 
	 * @param entity Entity to be added to the list of entities
	 */
	public void addEntity(Entity entity) {
		for (Entity other : entityList) {
			if (entity.getID() == other.getID()) {
				return;
			}
		}
		this.entityList.add(entity);
	}

	/**
	 * 
	 * @param entity Entity to be removed from the list of entities
	 */
	public void removeEntity(Entity entity) {
		this.entityList.remove(entity);
	}

	/**
	 * 
	 * @return List of entities
	 */
	public List<Entity> getEntityList(){
		return entityList;
	}

	/**
	 * 
	 * @return Information about the level
	 */
	public String getLevelInfo() {
		return this.levelInfo;
	}

	/**
	 * 
	 * @param levelInfo Information about the level
	 */
	public void setLevelInfo(String levelInfo) {
		this.levelInfo = levelInfo;
	}

	/**
	 * 
	 * @return Level number
	 */
	public int getLevelNum() {
		return this.levelNum;
	}

	/**
	 * 
	 * @param levelNum Level number
	 */
	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}

	/**
	 * 
	 * @return Time for the level
	 */
	public double getLevelTime() {
		return levelTime;
	}

	/**
	 * 
	 * @param levelTime Time for the level
	 */
	public void setLevelTime(double levelTime) {
		this.levelTime = levelTime;
	}

	/**
	 * 
	 * @return Map of HUD properties
	 */
	public Map<String,Boolean> getHUDprops() {
		return this.HUDprops;
	}

	/**
	 * 
	 * @param prop HUD property
	 * @param bool {@code Boolean} if property should be displayed in game player
	 */
	public void addHUDProp(String prop, Boolean bool) {
		this.HUDprops.put(prop, bool);
	}

	/**
	 * 
	 * @param prop Global property
	 * @param value Value of the global property
	 */
	public void addGProp(String prop, String value) {
		this.GProps.put(prop, value);
	}

	/**
	 * 
	 * @param prop HUD property to be removed from HUD properties map
	 */
	public void removeHUDProp(String prop) {
		this.HUDprops.remove(prop);
	}

	/**
	 * 
	 * @param prop Global property to be removed from Global properties map
	 */
	public void removeGProp(String prop) {
		this.GProps.remove(prop);
	}

}
