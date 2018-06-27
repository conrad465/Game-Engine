package authoring.views;

import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

import authoring.entities.BlankEntity;
import authoring.entities.Entity;
import authoring.gamestate.Level;
import authoring.grid.Grid;
import authoring.languages.AuthoringLanguage;
import data.DataGameState;
import engine.components.Component;
import engine.components.Height;
import engine.components.Sprite;
import engine.components.Type;
import engine.components.Width;
import engine.components.XPosition;
import engine.components.YPosition;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * @author Hemanth Yakkali((hy115)
 * @author Collin Brown(cdb55)
 */
public class LevelView extends ScrollPane implements AuthoringLanguage{

	private Grid content;
	private Level level;
	Consumer<MouseEvent> addEntity;
	boolean drag = false; 

	private Map<Level,Map<Integer,Map<String,Component>>> levels;

	Properties language = new Properties();

	public LevelView(Level level, int levelNum) {
		this.getStyleClass().add("level-view");
		this.level = level;
		this.content = new Grid(level);
		this.content.getStyleClass().add("level-view-content");
		this.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		this.setContent(content);
		this.setupMouseDrag();
	}

	public LevelView(Level level, int levelNum, Consumer<MouseEvent> aE) {
		this(level,levelNum);
		this.addEntity = aE;
	}

	public void loadGameState(Map<Integer,Map<String,Component>> levelMap) {

		Map<String, Component> entityComponents;
		for(Integer i:levelMap.keySet()) {
			entityComponents = levelMap.get(i);
			Entity entity = new BlankEntity(i);
			for(Component c: entityComponents.values()) {
				if(c.getKey().equals("Type")) {
					Type type = (Type) entityComponents.get(Type.KEY);
					if(type.getData().equals("Background")) {
						entity.setInteractable(false);
					}else {
						entity.setInteractable(true);
					}
					entity.setPresetType(type.getData());
				}
				entity.add(c);
			}
			if(entityComponents.containsKey(Sprite.KEY)) {
				Sprite spriteComponent = (Sprite) entityComponents.get(Sprite.KEY);
				Image image = spriteComponent.getImage().getImage();
				entity.setImage(image);
				if (entityComponents.containsKey(XPosition.KEY) && entityComponents.containsKey(YPosition.KEY)) {
					XPosition xComp = (XPosition) entityComponents.get(XPosition.KEY);
					YPosition yComp = (YPosition) entityComponents.get(YPosition.KEY);
					double col = (xComp.getData()/Entity.ENTITY_WIDTH);
					double row = (yComp.getData()/Entity.ENTITY_HEIGHT);
					System.out.println("what"+col);
					while((int)col>(Grid.GRID_COL-1)) {
						this.content.addCol(1);
					}
					while((int)row>(Grid.GRID_ROW-1)) {
						this.content.addRow(1);
					}
					if(!entity.getInteractable()) {
						Width width = (Width) entityComponents.get(Width.KEY);
						Height height = (Height) entityComponents.get(Height.KEY);
						this.content.addBackgroundToCell(entity,(int) row, (int) col,width.getData(),height.getData());
					} else {
						this.content.addToCell(entity, (int) row, (int) col);
					}
					this.level.addEntity(entity);
				}
			}
		}
	}

	/**
	 * Sets the onMouseReleased method for the content to handle dragging.
	 */
	private void setupMouseDrag() {
		content.setOnDragDetected(e -> this.drag = true);
	}

	/**
	 * Adds entity to the level view both to be seen graphically and to the specific 
	 * level object
	 * @param e Entity to be added to the LevelView
	 */
	public void addEntity(Entity e) {
		this.content.getChildren().add(e);
		level.addEntity(e);
	}

	/**
	 * Retrieves the level attributed to this levelView
	 * @return the level in this levelView
	 */
	public Level getLevel() {
		return this.level;
	}

	/**
	 * Sets the language of the levelview
	 */
	@Override
	public void setLanguage(Properties lang) {
		language = lang;
	}
}
