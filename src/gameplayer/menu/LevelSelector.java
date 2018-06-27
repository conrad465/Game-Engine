package gameplayer.menu;

import java.util.Map;
import gameplayer.controller.Controller;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

/**
 * Class for Selecting the corresponding level
 * @author Ryan
 *
 */
public class LevelSelector extends Menu {
	private final String MENU_TITLE = "Level";
	private Controller controller;
	private int levelCount;
	
	public LevelSelector(Controller g) {
		controller = g;
		this.setText(MENU_TITLE);
		createLevelMenu();
	}
	
	/**
	 * Method that creates each MenuItem for a given file;
	 * @param
	 */
	public void createLevelMenu() {
		int numOfLevels = controller.getGameManager().getNumOfLevels();
		levelCount = 1;
		for (int i = 1; i<=numOfLevels; i++) {
			MenuItem currentMenu = new MenuItem("Level " + levelCount);
			currentMenu.setOnAction(e->{
			    		int level = obtainLevelInteger(currentMenu.getText());
			    		controller.changeGameLevel(level);		    
			});
			this.getItems().add(currentMenu);
			levelCount++;
		}
	}
	
	/**
	 * Helper method that converts a 2-word string "level + int" and obtains just the level
	 * @param s
	 * @return
	 */
	private int obtainLevelInteger(String s) {
		String[] stringArray = new String[2];
		stringArray = s.split("\\s+"); //splits by whitespace
		int num = Integer.parseInt(stringArray[1]);
		return num;
	}

}
