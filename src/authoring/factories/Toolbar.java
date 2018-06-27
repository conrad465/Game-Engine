package authoring.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.util.Pair;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class Toolbar extends MenuBar{
	private String toolbarName;
	private Map<String,Consumer> consumerMap;
	private List<Pair<String,Properties>> menuProperties;

	private Properties language = new Properties();

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Creates a Toolbar based on all of the .properties files saved in the directory src/res/~name~
	 * @param name
	 */
	public Toolbar(String name,  Map<String,Consumer> cm) {
		super();
		this.consumerMap = cm;
		this.toolbarName = name;
		this.getStyleClass().add("toolbar");
		addMenus(getMenuProperties());
	}

	/**
	 * Add all of the menus outlined in the resources.menus package to the menuBar
	 * @param menuProperties
	 */
	private void addMenus(List<Pair<String,Properties>> menuProperties) {
		this.menuProperties = new ArrayList<>(menuProperties);
		List<Pair<String,Properties>> tempMenuProperties = this.menuProperties;
		for(Pair<String, Properties> p: tempMenuProperties) {
			this.getMenus().add((createMenu(p.getKey(),p.getValue())));
		}
	}

	/**
	 * Dynamically create a menu
	 * @param name the name of the menu
	 * @param items the items in a given menu
	 * @return a Node with the menu
	 */
	private Menu createMenu(String s, Properties p) {
		Menu m = new Menu();
		m.setText(s);
		m.setId(s);
		this.applyCss();
		for(Entry<Object, Object> entry : p.entrySet()) {
			MenuItem menuItem = new MenuItem();
			menuItem.setText((String) entry.getValue());
			menuItem.setId((String)entry.getKey());
			menuItem.setOnAction(e->consumerMap.get(entry.getKey()).accept(e));
			m.getItems().add(menuItem);
		}
		return m;
	}

	/**
	 * Get all of the properties files located in the resources.menus folder
	 * @return An ArrayList of Pairs that has the name of the menu and the properties of that menu
	 */
	private List<Pair<String,Properties>> getMenuProperties(){
		List<Pair<String,Properties>> menuProperties = new ArrayList<>();
		File menuFolder = new File("src/resources/menus/" + toolbarName);
		File[] menuFiles = menuFolder.listFiles();
		for(File f : Arrays.asList(menuFiles)) {
			Properties p = new Properties();
			try {
				p.load(new FileInputStream(f));
			} catch (IOException e) {
				LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
			}
			menuProperties.add(new Pair(f.getName().replace(".properties", ""),p));
		}
		return menuProperties;	
	}

	/**
	 * Updates all of the text elements to contain the proper language
	 * @param Language
	 */
	public void setLanguage(Properties lang) {
		language = lang;
		for(Menu menu : this.getMenus()) {
			menu.setText(language.getProperty(menu.getId(), menu.getId()));
			for(MenuItem menuItem : menu.getItems()) {
				menuItem.setText(language.getProperty(menuItem.getId(), menuItem.getId()));
			}
		}
	}
}

