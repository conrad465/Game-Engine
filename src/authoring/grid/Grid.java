package authoring.grid;

import java.util.ArrayList;
import java.util.List;

import authoring.actions.ActionAdderView;
import authoring.entities.Entity;
import authoring.entities.data.EntityLoader;
import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import authoring.factories.ClickElementType;
import authoring.factories.ElementFactory;
import authoring.gamestate.Level;
import authoring.views.properties.LocalPropertiesView;
import authoring.views.properties.PropertiesView;
import engine.components.Component;
import engine.components.Height;
import engine.components.Width;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

/**
 * A container class for all of the cells in a grid, which each represent entities if they are filled.
 * @author Hemanth Yakkali(hy115)
 * @author Dylan Powers
 */
public class Grid extends GridPane {

	private static final int DEFAULT_WIDTH = 2000;
	private static final int DEFAULT_HEIGHT = 1200;
	public static int GRID_ROW;
	public static int GRID_COL;
	private static final int ADD_FIVE = 5;
	private static final int ADD_ONE = 1;
	private static final String DEFAULT_STYLE = "-fx-background-color: rgba(0, 0, 0, 0); -fx-border-color: black";
	private static final String DRAGGED_OVER_STYLE = "-fx-background-color: #1CFEBA";

	private int numRows;
	private int numCols;
	private List<List<Cell>> cells;
	private int entityID;
	private Level level;
	private ElementFactory eFactory;

	/**
	 * Initializes the grid with a given number of rows and columns
	 * @param width the desired width of the grid
	 * @param height the desired height of the grid
	 */
	public Grid(int width, int height, Level level) {
		this.numRows = height/Entity.ENTITY_HEIGHT;
		this.numCols = width/Entity.ENTITY_WIDTH;
		this.GRID_COL = this.numCols;
		this.GRID_ROW = this.numRows;
		System.out.println("AHHH"+this.numRows);
		System.out.println("AHHH"+GRID_ROW);
		this.cells = new ArrayList<>();
		this.level = level;
		this.eFactory = new ElementFactory();
		this.entityID = 0;
		for (int i = 0; i < this.numRows; i++) {
			cells.add(new ArrayList<>());
			for (int j = 0; j < this.numCols; j++) {
				Cell c = new Cell(this.level);
				setupEntityDrop(c);
				setupContextMenu(c);
				cells.get(i).add(c);
				this.add(c, j, i);
			}
		}
		this.setPrefSize(width, height);
	}

	/**
	 * Empty constructor, use default values
	 */
	public Grid(Level level) {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, level);
	}

	/**
	 * Sets up the drag and drop utility used to add entities to the grid.
	 * @param c Cell to add the mouse click event listener to
	 */
	private void setupEntityDrop(Cell c) {
		c.setOnDragEntered(e -> c.setStyle(DRAGGED_OVER_STYLE));
		c.setOnDragExited(e -> c.setStyle(DEFAULT_STYLE));
		c.setOnDragOver(e -> {
			if (!e.getGestureSource().equals(c) && e.getDragboard().hasString()) {
				e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			e.consume();
		});
		c.setOnDragDropped(e -> {
			Dragboard db = e.getDragboard();
			EntityLoader el = new EntityLoader();
			ImageView img = new ImageView(db.getImage());
			if(!c.containsEntity() || !c.getEntity().getInteractable()) { //can add entity to empty cell or cell with background entity
				try {
					Entity en = el.buildEntity(this.getID(), db.getString(), c.getLayoutX(),c.getLayoutY());
					c.addEntity(en);
					level.addEntity(en);
					img.setFitWidth(Entity.ENTITY_WIDTH);
					img.setFitHeight(Entity.ENTITY_HEIGHT);
					c.getChildren().add(img);
					c.setImage(db.getImage());
				} catch (Exception e1) {
					throw new AuthoringException("Cannot add entity to the cell!", AuthoringAlert.SHOW);
				}
			} else {
				throw new AuthoringException("Cell already contains an entity!", AuthoringAlert.SHOW);
			}
			e.setDropCompleted(true);
			e.consume();
		});
	}

	/**
	 * Sets up mouse click listener that opens up the context menu for that particular cell.
	 * @param c Cell
	 */
	private void setupContextMenu(Cell c) {
		c.setOnMouseClicked(e -> {
			if(e.getButton().equals(MouseButton.SECONDARY)) {
				ContextMenu cMenu = new ContextMenu();
				try {
					MenuItem addCol = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Add Grid Column", e1->this.addCol(ADD_ONE));
					MenuItem addRow = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Add Grid Row", e1->this.addRow(ADD_ONE));
					MenuItem addFiveCol = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Add Five Grid Columns", e1->this.addCol(ADD_FIVE));
					MenuItem addFiveRow = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Add Five Grid Rows", e1->this.addRow(ADD_FIVE));
					cMenu.getItems().addAll(addCol,addRow,addFiveCol,addFiveRow);
					if(c.containsEntity()) {
						MenuItem openLPV = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Edit Entity Components", e1->this.openLPV(c.getEntity()));
						MenuItem actionConfig = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Edit Entity Actions", e1->{
							ActionAdderView a = new ActionAdderView(c.getEntity());});
						MenuItem removeEntity = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Remove Entity", e1->this.clearCell(c));
						cMenu.getItems().addAll(openLPV,removeEntity,actionConfig);
						if(!c.getEntity().getInteractable()) {
							ImageView bgrnd = (ImageView) c.getChildren().get(c.getChildren().size()-1);
							MenuItem addImageCol = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Add Column", e2->this.addImageCol(c, bgrnd, 1));
							MenuItem addImageRow = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Add Row", e2->this.addImageRow(c, bgrnd, 1));
							MenuItem addImageFiveCol = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Add Five Columns", e2->this.addImageCol(c, bgrnd, ADD_FIVE));
							MenuItem addImageFiveRow = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Add Five Rows", e2->this.addImageRow(c, bgrnd, ADD_FIVE));
							MenuItem removeImageCol = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Remove Column", e2->this.addImageCol(c, bgrnd, -1));
							MenuItem removeImageRow = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Remove Row", e2->this.addImageRow(c, bgrnd, -1));
							cMenu.getItems().addAll(addImageCol, addImageRow, addImageFiveCol, addImageFiveRow, removeImageCol, removeImageRow);
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new AuthoringException("Cannot create context menu!", AuthoringAlert.SHOW);
				}
				cMenu.show(c, e.getScreenX(), e.getScreenY());
				cMenu.setAutoHide(true);
			}
			e.consume();
		});
	}

	/**
	 * Opens up the local properties view for the specific entity.
	 * @param en Entity 
	 */
	private void openLPV(Entity en) {
		PropertiesView pv = new LocalPropertiesView(en, componentList ->  {
			for (Component comp : componentList) {
				en.add(comp);
			}
		});
		pv.open();
	}

	/**
	 * Adds a specified number of columns to the entity. Should only be used for non-interactable
	 * entities.
	 * @param c Cell
	 * @param img ImageView of an entity
	 * @param numCols Number of columns to add
	 */
	private void addImageCol(Cell c, ImageView img, int numCols) {
		img.setFitWidth(img.getFitWidth()+numCols*Entity.ENTITY_WIDTH);
		Entity en = c.getEntity();
		en.add(new Width(en.getID(),img.getFitWidth()));
	}

	/**
	 * Adds a specified number of rows to the entity. Should only be used for non-interactable
	 * entities.
	 * @param c Cell
	 * @param img ImageView of an entity
	 * @param numRows Number of rows to add
	 */
	private void addImageRow(Cell c, ImageView img, int numRows) {
		img.setFitHeight(img.getFitHeight()+numRows*Entity.ENTITY_HEIGHT);
		Entity en = c.getEntity();
		en.add(new Height(en.getID(),img.getFitHeight()));
	}

	/**
	 * Removes image from the cell and removes entity from level object
	 * @param c Cell
	 */
	private void clearCell(Cell c) {
		level.removeEntity(c.getEntity());
		c.removeEntity(c.getEntity());
		c.getChildren().remove(c.getChildren().size()-1); //remove last node in children
	}

	/**
	 * Adds new row of cells to the grid
	 * @param numTimes Number of rows to add
	 */
	public void addRow(int numTimes) {
		for(int j = 0; j < numTimes; j++) {
			this.cells.add(new ArrayList<>());
			for (int i = 0; i < this.numCols; i++) {
				Cell c = new Cell(this.level);
				setupEntityDrop(c);
				setupContextMenu(c);
				this.cells.get(this.numRows).add(c);
				this.add(c, i, this.numRows);
			}
			this.setPrefHeight(this.getPrefHeight() + Entity.ENTITY_HEIGHT);
			this.numRows++;
			this.GRID_ROW++;
		}
	}

	/**
	 * Adds a number of new columns to the grid
	 * @param numTimes Number of columns to add 
	 */
	public void addCol(int numTimes) {
		for(int j = 0; j < numTimes; j++) {
			for(int i = 0; i < this.numRows; i++) {
				Cell c = new Cell(this.level);
				setupEntityDrop(c);
				setupContextMenu(c);
				this.cells.get(i).add(c);
				this.add(c, this.numCols, i);
			}
			this.setPrefWidth(this.getPrefWidth() + Entity.ENTITY_WIDTH);
			this.numCols++;
			this.GRID_COL++;
		}
	}
	
	public void addToCell(Entity en, int row, int col) {
		Cell cell = cells.get(row).get(col);
		cell.addEntity(en);
		ImageView img = new ImageView(en.getImage());
		img.setFitWidth(Entity.ENTITY_WIDTH);
		img.setFitHeight(Entity.ENTITY_HEIGHT);
		cell.getChildren().add(img);
		cell.setImage(en.getImage());
		this.entityID++;
	}
	
	public void addBackgroundToCell(Entity en, int row, int col, double width, double height) {
		Cell cell = cells.get(row).get(col);
		cell.addEntity(en);
		ImageView img = new ImageView(en.getImage());
		img.setFitWidth(width);
		img.setFitHeight(height);
		cell.getChildren().add(img);
		cell.setImage(en.getImage());
		this.entityID++;
	}

	/**
	 * Creates the next ID to be used when creating a new entity
	 * @return Next ID for the next entity
	 */
	private int getID() {
		return this.entityID++;
	}

}
