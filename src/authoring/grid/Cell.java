package authoring.grid;

import java.util.ArrayList;
import java.util.List;

import authoring.entities.Entity;
import authoring.gamestate.Level;
import engine.components.StringComponent;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

/**
 * Defines a cell in the grid. The cell will hold nothing when an entity has not been
 * placed inside of it, but will hold an entity when the user has placed an entity inside of it.
 * @author Dylan Powers
 * @author Hemanth Yakkali
 *
 */
public class Cell extends Pane {

	private final static String DEFAULT_STYLE = "-fx-background-color: rgba(0, 0, 0, 0); -fx-border-color: black";
	private List<Entity> entityList;
	private Image image;
	private Level level;

	/**
	 * To initialize a blank cell
	 */
	public Cell(Level level) {
		this.level = level;
		this.entityList = new ArrayList<>();
		this.setPrefWidth(Entity.ENTITY_WIDTH);
		this.setPrefHeight(Entity.ENTITY_HEIGHT);
		this.setStyle(DEFAULT_STYLE);
		this.setUpDrag();
	}

	/**
	 * Sets up drag and drop utility which allows user to move entity between cells
	 */
	private void setUpDrag() {
		this.setOnDragDetected(e -> {
			Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent cc = new ClipboardContent();
			cc.putImage(image);
			cc.putString(((StringComponent)this.getEntity().get("Name")).getData());
			db.setContent(cc);
			e.consume();
		});
		this.setOnDragDone(e -> {
			if (e.getGestureTarget() != this) {
                	this.level.removeEntity(this.getEntity());
                	this.removeEntity(this.getEntity());
                	this.getChildren().clear();
			}
		});
	}

	/**
	 * @return Last entity that was added to the list of entities
	 */
	public Entity getEntity() {
		return entityList.get(entityList.size()-1);
	}

	/**
	 * Adds an entity to list of entities
	 * @param entity the entity to be placed in this cell
	 */
	public void addEntity(Entity entity) {
		this.entityList.add(entity);
	}

	/**
	 * Remove entity from the list of entities in this cell
	 * @param entity Entity to be removed
	 */
	public void removeEntity(Entity entity) {
		this.entityList.remove(entity);
	}

	/**
	 * Check if this cell contains an entity.
	 * @return true if the cell contains an entity
	 */
	public boolean containsEntity() {
		return !this.entityList.isEmpty();
	}

	/**
	 * Sets image for the entity
	 * @param i Image used to represent the entity
	 */
	public void setImage(Image i) {
		image = i;
	}

}
