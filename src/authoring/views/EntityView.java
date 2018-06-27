package authoring.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import authoring.entities.data.PackageExplorer;
import authoring.entities.data.SudoEntityLoader;
import authoring.factories.Toolbar;
import data.DataRead;
import engine.components.Sprite;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class EntityView extends BorderPane implements AuthoringPane {
	public final static String XML_EXTENSION = ".XML";
	public final static String DEFAULT_LIST = "src/resources/defaults.properties";
	public final static String ENTITIES_PACKAGE_NAME = "authoring/entities";
	public final static  int ENITITY_VIEW_WIDTH = 300;
	private static final String DEFAULTS_PACKAGE = "data/entities/";
	private ArrayList<String> entityTypes = new ArrayList<>();
	private TabPane tabPane = new TabPane();
	private Properties lang = new Properties();


	public EntityView() {
		super();
		this.setPrefWidth(ENITITY_VIEW_WIDTH);
		this.getStyleClass().add("entity-view");
		this.setTop(new Toolbar("Entities", buildToolbarConsumerMap()));
		this.setCenter(tabPane);
		entityTypes.addAll(Arrays.asList(PackageExplorer.getElementsInPackage(ENTITIES_PACKAGE_NAME, ".class", "Entity")));
		this.addDefaults();
	}

	private void addDefaults() {
		try {
			Properties defaults = new Properties();
			defaults.load(new FileInputStream(DEFAULT_LIST));
			for(Object entityName : defaults.keySet()) {
				this.loadEntityFromFile(new File(DEFAULTS_PACKAGE + entityName.toString() + XML_EXTENSION ));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Builds the consumers for the toolbar
	 * @return Map<String, Consumer> 
	 */
	private Map<String, Consumer> buildToolbarConsumerMap() {
		Map<String, Consumer> consumerMap = new HashMap<>();
		BiConsumer<String, Map<Class, Object[]>> onClose = (entityType,componentAttributes) -> {saveEntity(entityType, componentAttributes);};
		Consumer<?> newEntity = e -> {
			EntityBuilderView entityBuilderView = new EntityBuilderView(entityTypes, onClose, lang);
		};
		Consumer<?> loadEntity = e -> {loadEntity();};
		consumerMap.put("newEntity", newEntity);
		consumerMap.put("loadEntity", loadEntity);
		return consumerMap;
	}

	/**
	 * Adds a new tab
	 * @param type
	 */
	private void addTab(String type) {
		EntityTab temp = new EntityTab(type, ENITITY_VIEW_WIDTH);
		temp.setLanguage(lang);
		tabPane.getTabs().add(temp);
	}

	/**
	 * Called when a EntityBuilderView is closed
	 * @param entityType String representing the type of entity that this object represents
	 * @param componentAttributes Map<Class, Object[]> representing the class of a component and an object[] representing all the arguments the component takes in
	 */
	public void saveEntity(String entityType, Map<Class, Object[]> componentAttributes) {

		Image image = DataRead.loadImage((String) componentAttributes.get(Sprite.class)[0]);

		if(tabPane.getTabs().isEmpty() || !hasTab(entityType) ) { 
			addTab(entityType);
		}   
		for(Tab tab : tabPane.getTabs()) {
			if(tab.getId().equals(entityType)) {
				((EntityTab) tab).addNewEntity(entityType, componentAttributes);
			}
		}
	}

	private boolean hasTab(String entityType) {
		for(Tab t : tabPane.getTabs()) {
			if(t.getId().equals(entityType)) {
				return true;
			}
		}
		return false;
	}

	public void loadEntity() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Entity File");
		File entityFile = fileChooser.showOpenDialog(this.getScene().getWindow());	
		loadEntityFromFile(entityFile);
	}

	public void loadEntityFromFile(File entityFile) {
		SudoEntityLoader entityLoader = new SudoEntityLoader();
		try {
			Object[] sudoEntity = entityLoader.buildEntity(entityFile);
			saveEntity(sudoEntity[0].toString(),(Map<Class, Object[]>) sudoEntity[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setLanguage(Properties language) {
		this.lang = language;
		((Toolbar) this.getTop()).setLanguage(language);
		for(Tab t : tabPane.getTabs()) {
			t.setText(language.getProperty(t.getId(),t.getId()));
		}
	}

}
