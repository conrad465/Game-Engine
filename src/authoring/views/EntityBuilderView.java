package authoring.views;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import authoring.MainApplication;
import authoring.entities.data.EntityBuilderData;
import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import authoring.factories.ClickElementType;
import authoring.factories.ElementFactory;
import authoring.forms.EntityComponentFormCollection;
import data.DataRead;
import engine.components.Sprite;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Collin Brown(cdb55)
 *
 */
public class EntityBuilderView extends Stage {
	private final static int LEFT_PANEL_WIDTH = 200;
	private final static String PROPERTIES_PACKAGE = "resources.menus.Entity/";

	private Properties tooltipProperties;
	private Properties language = new Properties();
	private VBox root;
	private List<String> entityTypes;
	private ImageView entityPreview;
	private ElementFactory eFactory;
	private EntityComponentFormCollection componentFormCollection;
	private boolean hasImage = false;

	private EntityBuilderData data;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private List<String> imageExtensions = Arrays.asList(new String[] {".jpg",".png",".jpeg"});

	private BiConsumer<String, Map<Class, Object[]>> onClose;

	/**
	 * The constructor of the popup window for creating new entities
	 * @param eTypes All of the possible types of entities
	 * @param oC A BiConsumer that will handle the closing of an EntityBuilderView window that requires a string of the type of entity and a Map of Component Classes and an object[] of their argumetns
	 */
	public EntityBuilderView (List<String> eTypes, BiConsumer<String, Map<Class,Object[]>> oC, Properties lang) {
		this.onClose = oC;
		language = lang;
		this.entityTypes = (ArrayList<String>) eTypes;
		this.eFactory = new ElementFactory();
		String[] exceptions = new String[] {"Sprite", "XPosition", "YPosition"};
		this.componentFormCollection = new EntityComponentFormCollection(exceptions, e->{save(e);});
		this.componentFormCollection.setLanguage(language);
		this.tooltipProperties = new Properties();
		this.data = new EntityBuilderData();
		this.build();
		this.open();
	}

	/**
	 * Builds the view to be displayed
	 */
	private void build() {
		HBox addImageMenu = new HBox();
		try {
			tooltipProperties.load(new FileInputStream("src/resources/tooltips/EntityBuilderViewTooltips.properties"));
			this.root = new VBox();
			this.root.setAlignment(Pos.CENTER);
			ComboBox<String> typeComboBox = buildTypeComboBox();
			addImageMenu = buildSingleButtonMenu("addImage", e -> {addImage();});
			this.entityPreview = new ImageView();

			File imageFile = new File("data/images/Collin.png");
			updateEntityPreview(SwingFXUtils.toFXImage(ImageIO.read(imageFile), null));
			this.root.getChildren().addAll(entityPreview, typeComboBox, addImageMenu, componentFormCollection);
			this.root.getStyleClass().add("entity-builder-view");
		}  catch (Exception e) {
			LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
		}

	}

	/**
	 * Opens the Property Editor window.
	 */
	private void open() {
		Scene s = new Scene(root);
		this.setTitle("Entity Builder");
		s.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());
		this.setScene(s);
		this.show();
	}

	/**
	 * Updates the image preview for the entity
	 * @param i image to add
	 */
	private void updateEntityPreview(Image i) {
		entityPreview.setImage(i);
		entityPreview.setFitHeight(LEFT_PANEL_WIDTH);
		entityPreview.setFitWidth(LEFT_PANEL_WIDTH);
		//TODO: Make this handle things other than squares
	}

	/**
	 * Builds the menu to select the Type of Entity
	 * @return Menu typeMenu
	 * @throws Exception
	 */
	private ComboBox<String> buildTypeComboBox() throws Exception {
		ComboBox<String>comboBox = (ComboBox<String>) eFactory.buildClickElement(ClickElementType.ComboBox, "Select Object Type", null);
		comboBox.setPromptText(language.getProperty("selectObjectType"));
		comboBox.setOnAction(e -> {
			data.setComponent(engine.components.Type.class, getRealName(comboBox.getSelectionModel().getSelectedItem()));
			List<String> componentsToAdd = new ArrayList<>(ResourceBundle.getBundle(PROPERTIES_PACKAGE + data.getType()).keySet());

			componentFormCollection.fill(componentsToAdd);
			componentFormCollection.setLanguage(language);
			this.sizeToScene();
		});
		comboBox.getStyleClass().add("entity-builder-combo-box");
		for(String et : entityTypes) {
			comboBox.getItems().add(language.getProperty(et));
		}
		return comboBox;
	}

	private String getRealName(String selectedItem) {
		String typeName = null;
		for(Object t : language.keySet()) {
			if(language.get(t).equals(selectedItem)){
				typeName = (String) t;
			}
		}
		return typeName;
	}

	/**
	 * Builds the menu on the buttom of the screen containing the save button
	 * @return HBox bottomMenu
	 * @throws Exception
	 */
	private HBox buildSingleButtonMenu(String name, Consumer onClick) throws Exception {
		HBox hBox = new HBox();
		Button b = (Button) eFactory.buildClickElement(ClickElementType.Button, name, onClick);
		b.setText(language.getProperty(name));
		b.setTooltip(new Tooltip(tooltipProperties.getProperty(name)));
		b.getStyleClass().addAll("entity-builder-view-button",name);
		hBox.setAlignment(Pos.CENTER);
		hBox.getStyleClass().add("toolbar");
		hBox.getChildren().add(b);
		return hBox;
	}


	/**
	 * Saves the current entity
	 */
	private void save(Object e){
		if(!hasImage) {
			AuthoringException authorException = new AuthoringException("Please Add an Image", AuthoringAlert.SHOW);
		} else {
			try {
				data.save((List<Object[]>) e);
				onClose.accept(data.getType(), data.getComponentAttributes());
				this.close();
			}
			catch (Exception e1) {
				LOGGER.log(java.util.logging.Level.SEVERE, e1.toString(), e1);
			}
		}
	}

	/**
	 * adds an image to the preview
	 */
	private void addImage() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image File");
		fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Image Filter", imageExtensions));
		File imageFile = fileChooser.showOpenDialog(this);
		try {
			data.setComponent(Sprite.class, imageFile.getName());
			DataRead.addImage(imageFile);
			Image image = SwingFXUtils.toFXImage(ImageIO.read(imageFile), null);
			updateEntityPreview(image);
			hasImage = true;
		} catch (Exception e1){
			LOGGER.log(java.util.logging.Level.SEVERE, e1.toString(), e1);
		}
	}

}
