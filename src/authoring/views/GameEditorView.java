package authoring.views;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.logging.Logger;

import data.DataGameState;
import data.DataRead;
import data.ResourceGetter;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import authoring.entities.Entity;
import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import authoring.factories.ElementFactory;
import authoring.factories.ElementType;
import authoring.factories.Toolbar;
import authoring.gamestate.GameNameChooser;
import authoring.gamestate.GameState;
import authoring.gamestate.Level;
import authoring.views.popups.SelectionBox;
import authoring.views.properties.GlobalPropertiesView;
import authoring.views.properties.HUDPropertiesView;
import authoring.views.properties.LevelPropertiesView;
import engine.components.Component;
import engine.components.EntityType;
import engine.components.Sprite;
import engine.components.XPosition;
import engine.components.YPosition;
import gameplayer.Main;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class GameEditorView extends BorderPane implements AuthoringPane{

	private ArrayList<Tab> levelTabsList;
	private GameState state;
	private TabPane tabPane;
	private Toolbar toolbar;
	private ElementFactory eFactory;
	private int nextEntityID  = 0;
	private final String SAVE_ERROR = "Game could not be saved.";
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Consumer<List<Tab>> updateTabs = tabList -> { updateTabsMethod(tabList); };
	private static final int BLOCK_DEFAULT_WIDTH = 50;
	private Consumer<String> setMainViewLang;
	private String name;
	private List<Level> levels;
	private DataGameState gameState;

	Properties language = new Properties();

	/**
	 * Default Constructor creates a Borderpane with a toolbar in the top, tabPane in the center, and a gamestate object
	 * @param name the name of the game being created
	 */
	public GameEditorView(Consumer<String> setLanguage, String name) {
		super();
		setMainViewLang = setLanguage;
		this.toolbar = new Toolbar("GameEditor", buildToolbarFunctionMap());
		this.setTop(toolbar);
		this.tabPane = new TabPane();
		this.levelTabsList = new ArrayList<>();
		this.levels = new ArrayList<>();
		this.state = new GameState();
		state.setName(name);
		this.setCenter(tabPane);
		this.eFactory = new ElementFactory();
		addLevel();
	}

	/**
	 * 
	 * @return Map<String, Consumer> names and consumers to be added to the toolbar
	 */
	private Map<String,Consumer> buildToolbarFunctionMap(){

		Map<String, Consumer> consumerMap = new HashMap<>();
		consumerMap.put("newGame", e->{newGameMethod(); addLevel();});
		consumerMap.put("loadGame",  e->{ loadGameMethod();});
		consumerMap.put("saveGame", e-> { saveGameMethod(); });
		consumerMap.put("showSettings", e->{showSettingsMethod();});
		consumerMap.put("hudSettings",e -> { hudSettingsMethod();});
		consumerMap.put("play", e->{playMethod();});
		consumerMap.put("addLevel", e->{addLevel();});
		consumerMap.put("levelSettings", e->{showLevelSettings();});
		consumerMap.put("changeLanguage", e->{setMainViewLanguage();});
		consumerMap.put("Import", e->{
			ResourceGetter rg = new ResourceGetter();
		rg.selectImage();});
		return consumerMap;
	}

	private void setMainViewLanguage() {
		SelectionBox selectionBox = new SelectionBox(new String[] {"cebuano","english"},new String[] {}, selection -> { //TODO: Fix this hardcoding
			setMainViewLang.accept((String) selection);
		});
	}

	/**
	 * Update the numbers of the level tabs
	 * @param tabList
	 */
	private void updateTabsMethod(List<Tab> tabList) {
		for(Tab t : tabList) {
			t.setText(language.getProperty("Level") + (tabList.indexOf(t)+1));
		}
	}

	/**
	 * Creates a new LevelView
	 */
	public void addLevel(){
		try {
			Tab t = (Tab) this.eFactory.buildElement(ElementType.Tab, "Level " + (levelTabsList.size()+1));
			levelTabsList.add(t);
			Level level = new Level(levelTabsList.indexOf(t)+1);
			levels.add(level);
			state.addLevel(level);
			LevelView levelView = new LevelView(level, levelTabsList.indexOf(t)+1, e -> {addEntityMethod(e);});
			t.setContent(levelView);
			t.setOnClosed(e -> {
				levelTabsList.remove(t);
				updateTabs.accept(levelTabsList);
			});
			//((LevelView) t.getContent()).setLanguage(language);
			tabPane.getTabs().add(t);
		} catch (Exception e) {
			throw new AuthoringException(Level.ERROR_MESSAGE, AuthoringAlert.SHOW);
		}
	}
	
	private void addLoadLevel(Level l) {
		try {
			Tab t = (Tab) this.eFactory.buildElement(ElementType.Tab, "Level " + (levelTabsList.size()+1));
			levelTabsList.add(t);
			Level level = new Level(levelTabsList.indexOf(t)+1);
			levels.add(level);
			state.addLevel(level);
			LevelView levelView = new LevelView(level, levelTabsList.indexOf(t)+1, e -> {addEntityMethod(e);});
			levelView.loadGameState(gameState.getGameState().get(l));
			t.setContent(levelView);
			t.setOnClosed(e -> {
				levelTabsList.remove(t);
				updateTabs.accept(levelTabsList);
			});
			//((LevelView) t.getContent()).setLanguage(language);
			tabPane.getTabs().add(t);
		} catch (Exception e) {
			throw new AuthoringException(Level.ERROR_MESSAGE, AuthoringAlert.SHOW);
		}
	}
	
	private void removeLevels() {
		levels.clear();
		levelTabsList.clear();
		tabPane.getTabs().clear();
	}
	
	private void reloadState() { 
		state.getLevels().clear();
		for(Level level: levels) {
			state.addLevel(level);
		}
	}
	
	public void startLoadingGameStates(DataGameState state) {
		this.gameState = state;
		removeLevels();
		for(Level level: state.getGameState().keySet()) {
			this.addLoadLevel(level);
		}
		reloadState();
	}

	/**
	 * Creates a new LevelView based on an existing LevelView
	 * @param l Level to be added to current view
	 * @return LevelView object just instantiated
	 */
	public LevelView loadLevel(Level l){
		levelTabsList.add(new Tab());
		Tab t = levelTabsList.get(levelTabsList.size()-1);
		t.setText(language.getProperty("Level") + (levelTabsList.indexOf(t)+1));

		Level level = l;
		state.addLevel(level);
		LevelView levelView = new LevelView(level, levelTabsList.indexOf(t)+1, e -> {addEntityMethod(e);});

		t.setContent(levelView);
		t.setOnClosed(e -> {
			levelTabsList.remove(t);
			updateTabs.accept(levelTabsList);
		});

		tabPane.getTabs().add(t);
		return levelView;
	}

	private void newGameMethod() {
		state = new GameState();
		GameNameChooser gnc = new GameNameChooser();
		gnc.showAndWait(name -> state.setName(name));
		levelTabsList = new ArrayList<>();
		tabPane.getTabs().clear();
	}

	/**
	 * Opens a new thread to play the game on
	 */
	private void playMethod() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try { 
					new Main().start(new Stage());
				} catch (Exception e) { 
					throw new AuthoringException(e, AuthoringAlert.NO_SHOW);
				}
			}	
		});	
	}

	/**
	 * Shows the HUD Settings Menu
	 */
	private void hudSettingsMethod() {
		List<Level> levelArray = new ArrayList<>();
		for(Tab t: levelTabsList) {
			levelArray.add(((LevelView) t.getContent()).getLevel());
		}
		HUDPropertiesView HPV = new HUDPropertiesView(levelArray);
		HPV.open();	
		HPV.setLanguage(language);
	}

	/**
	 * Shows the GlobalPropertiesView Panel
	 */
	private void showSettingsMethod() {
		List<Level> levelArray = new ArrayList<>();
		for(Tab t: levelTabsList) {
			levelArray.add(((LevelView) t.getContent()).getLevel());
		}
		GlobalPropertiesView GPV = new GlobalPropertiesView(levelArray);
		GPV.open();
		GPV.setLanguage(language);
	}

	/**
	 * Saves the Current instance of the game
	 */
	private void saveGameMethod() {
		try {
			this.state.save();
		} catch (Exception ex) {
			throw new AuthoringException(SAVE_ERROR, AuthoringAlert.SHOW);
		}
	}

	/**
	 * Opens a file chooser and loads the chosen file to the gameAuthoringEnvironment
	 */
	private void loadGameMethod() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image File");
		File gameFile = fileChooser.showOpenDialog(new Stage());
		Map<Level, Map<Integer, List<Component>>> authorData = DataRead.loadAuthorFile(gameFile);
		newGameMethod();
		for(Level level : authorData.keySet()) {
			LevelView levelView = loadLevel(level);
			Map<Integer, List<Component>> entities = authorData.get(level);
			for(Integer entityID : entities.keySet()) {
				List<Component> componentList = entities.get(entityID);
				try {
					Entity entity = createEntityFromComponentList(entityID, componentList);
					levelView.addEntity(entity);
				} catch (Exception e) {
					throw new AuthoringException(Entity.ERROR_MESSAGE, AuthoringAlert.SHOW);
				}
			}
		}
	}

	/**
	 * Opens the levelPropertiesView
	 */
	private void showLevelSettings() {
		Level level = ((LevelView) this.tabPane.getSelectionModel().getSelectedItem().getContent()).getLevel();
		LevelPropertiesView lView = new LevelPropertiesView(level, level.getLevelNum());
		lView.open();
		lView.setLanguage(language);
	}


	/**
	 * Creates a component with the ID and a List of components
	 * @return entity 
	 */
	private Entity createEntityFromComponentList(Integer entityID, List<Component> componentList) throws Exception{
		Entity entity = null;
		for(Component c : componentList) {
			if(c.getKey().equals("EntityType")) {
				String entityType = ((EntityType)c).getData();
				Class entityTypeClass = Class.forName("authoring.entities." + entityType);

				entity = createEntityFromType(entityTypeClass, entityID);	
			}
			entity.add(c);
			if(c.getKey().equals("Sprite")) {
				Image image = DataRead.loadImage(((Sprite) c).getData());	
				entity.setImage(image);
			} else if(c.getKey().equals("XPosition")) {
				XPosition p = (XPosition) c;
				entity.setX(p.getData());

			} else if(c.getKey().equals("YPosition")) {
				YPosition p = (YPosition) c;
				entity.setY(p.getData());
			}
		}
		return entity;
	}

	/**
	 * Adds an entity to the LevelView
	 * @param mouseEvent
	 */
	private void addEntityMethod(MouseEvent mouseEvent) {
		Entity entity = null;
		try {
			entity.setPosition(mouseEvent.getX() - entity.getFitWidth() / 2, mouseEvent.getY() - this.tabPane.getTabMaxHeight() - entity.getFitHeight() / 2);
			((LevelView) tabPane.getSelectionModel().getSelectedItem().getContent()).addEntity(entity);
			nextEntityID++;
		} catch (Exception e) {
			throw new AuthoringException(Entity.ERROR_MESSAGE, AuthoringAlert.SHOW);
		}
	}

	/**
	 * Creates an Entity object from the Class representing its type and an ID
	 * @param entityType class representing the type of entity to make
	 * @param ID int representing the ID of an entity
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private Entity createEntityFromType(Class<?> entityType, int ID) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<?> entityConstructor = entityType.getConstructor(int.class);
		Entity entity = (Entity) entityConstructor.newInstance(nextEntityID); 
		entity.setFitWidth(BLOCK_DEFAULT_WIDTH);
		entity.setFitHeight(BLOCK_DEFAULT_WIDTH);
		return entity;
	}


	@Override
	public void setLanguage(Properties lang) {
		language = lang;
		toolbar.setLanguage(language);
		for(Tab t : tabPane.getTabs()) {
			t.setText(language.getProperty("Level")+ " " + (tabPane.getTabs().indexOf(t)+1)) ;
			// ((LevelView) t.getContent()).setLanguage(language);
		}

	}

}
