package authoring.views;

import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import authoring.factories.ElementFactory;
import authoring.factories.ElementType;
import authoring.languages.AuthoringLanguage;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseButton;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;

/**
 * 
 * @author Collin Brown(Cdb55)
 *
 */
public class EntityTab extends Tab implements AuthoringLanguage {
	Properties language = new Properties();
	public static final double SCROLLBAR_WIDTH = 20;
	public static final double VIEW_WIDTH = 0;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private ElementFactory eFactory;

	FlowPane pane;
	ScrollPane externalPane;

	double myEntityViewWidth;

	public EntityTab(String name, double entityViewWidth) {
		super(name);
		this.setId(name);
		myEntityViewWidth = entityViewWidth;	
		myEntityViewWidth = entityViewWidth;		
		this.setClosable(false);
		this.getStyleClass().add("entity-tab");
		assemble();
	}

	/**
	 * builds the ScrollPane and the FlowPane within it
	 */
	private void assemble() {
		externalPane = new ScrollPane();
		externalPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		externalPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		pane = new FlowPane();
		pane.setPrefWidth(myEntityViewWidth);
		externalPane.setContent(pane);
		this.setContent(externalPane);
	}

	/**
	 * Adds a new entity to the entityView
	 * @param type the type of entity being created
	 * @param componentAttributes the attributes of a component
	 */
	public void addNewEntity(String type, Map<Class, Object[]> componentAttributes) {
		EntityBox eb = new EntityBox(type, componentAttributes);
		eb.setOnMouseClicked(mouseEvent -> {
			if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
				ContextMenu cMenu = new ContextMenu();
				try {
					MenuItem delete = (MenuItem) eFactory.buildElement(ElementType.MenuItem, "Delete");
					delete.setOnAction(e -> {
						removeEntity(eb);
					});
					cMenu.getItems().add(delete);

					cMenu.setAutoHide(true);
				} catch (Exception e1) {
					LOGGER.log(java.util.logging.Level.SEVERE, e1.toString(), e1);
				}
				cMenu.show(pane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
			}
		});
		pane.getChildren().add(eb);
	}

	/**
	 * Returns the graphic representation of the ComponentTab
	 */
	public Node getNode() {
		return pane;
	}

	/**
	 * Removes and entity from the entityView
	 * @param eb the entitBox to be removed
	 */
	private void removeEntity(EntityBox eb) {
		pane.getChildren().remove(eb);
	}

	@Override
	public void setLanguage(Properties lang) {
		this.language = lang;
		this.setText(language.getProperty(this.getId()));
	}


}
