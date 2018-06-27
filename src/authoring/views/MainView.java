package authoring.views;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.logging.Logger;

import authoring.gamestate.AuthoringStateLoader;
import data.DataGameState;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class MainView {
	
	public static final String LANGUAGE_PROPERTIES_PACKAGE = "src/resources/languages/";
	public static final String DEFAULT_LANGUAGE = "english";
	public static final String PROPERTIES_EXTENSION = ".properties";
	private Properties language;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final double DEFAULT_HEIGHT = 600;
	private static final double DEFAULT_WIDTH = 1200;
	private BorderPane border;
	private GameEditorView gameEditorView;
	private EntityView componentView;

	private double ideHeight = DEFAULT_HEIGHT;
	private double ideWidth = DEFAULT_WIDTH;

	private Consumer setLangConsumer = (langName) -> {setLanguage((String) langName);};
	private List<AuthoringPane> authorPaneList = new ArrayList<>();

	/**
	 * Creates an instance of GameAuthoringEnvironment from scratch
	 * @param name the name of the game to load/create
	 */
	public MainView(String name) {
		// TODO check if name exists in path
		gameEditorView = new GameEditorView(setLangConsumer, name);
		this.build();
	}
	
	/**
	 * Creates an instance of GameAuthoringEnvironment based upon an existing gameState.
	 * @param name the name of the game to load
	 * @param gameState the state object of the game to load in 
	 */
	public MainView(String name, DataGameState gameState) {
		this(name);
		gameEditorView.startLoadingGameStates(gameState);
		// find all existing entities and load them into the entityView
		AuthoringStateLoader asl = new AuthoringStateLoader();
		for (File entityFile : asl.findFilesForGame(name)) {
			componentView.loadEntityFromFile(entityFile);
		}
		System.out.println(gameState);
	}

	/**
	 * creates the ViewComponents and adds them to the borderPane 
	 */
	public Parent build() {
		border = new BorderPane();
		border.setLeft(componentView);
		border.setCenter(gameEditorView);
		componentView =  new EntityView();
		this.setLanguage(DEFAULT_LANGUAGE);
		authorPaneList.addAll(Arrays.asList(new AuthoringPane[] {componentView, gameEditorView}));
		return border;
	}

	/**
	 * Returns the gameEnvironmentView object
	 * @return
	 */
	public GameEditorView getGameEnvironmentView() {
		return gameEditorView;
	}

	/**
	 * Returns the ComponentView object
	 * @return
	 */
	public EntityView getComponentView() {
		return componentView;
	}

	/**
	 * Returns the height of the gameAuthoringEnvironment
	 * @return
	 */
	public double getIDEHeight() {
		return ideHeight;
	}

	/**
	 * Sets the height of the gameAuthoringEnvironment
	 * @param ideHeight
	 */
	public  void setIDEHeight(double ideHeight) {
		this.ideHeight = ideHeight;
	}

	/**
	 * Gets the width of the gameAuthroingEnvironment
	 * @return
	 */
	public double getIDEWidth() {
		return ideWidth;
	}

	/**
	 * Sets the width of the GameAuthoringEnvironment
	 * @param ideWidth
	 */
	public void setIDEWidth(double ideWidth) {
		this.ideWidth = ideWidth;
	}


	/**
	 * Sets the language properties associated with this view
	 * @param langName String of the language name
	 */
	public void setLanguage(String langName) {
		try {
			language = new Properties();
			language.load(new FileInputStream(LANGUAGE_PROPERTIES_PACKAGE + langName + PROPERTIES_EXTENSION));
			for(AuthoringPane authorPane : authorPaneList) {
				authorPane.setLanguage(language);
			}
		} catch (Exception e) {
			LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
		}
	}
}
