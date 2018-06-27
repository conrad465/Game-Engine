package gameplayer.menu;

import java.util.Map;
import engine.components.Component;
import gameplayer.controller.Controller;
import javafx.scene.control.MenuBar;

/**
 * ComboBox Class that allows user to select the corresponding level
 * @author Ryan
 *
 */
public class MenuGameBar extends MenuBar{
		
	private LevelSelector levelMenu;

	public MenuGameBar(Controller g) {
				levelMenu = new LevelSelector(g);
				//levelList = createLevelList(levelMap); //levelList now is a list of each level as a combobox.
				this.getMenus().add(levelMenu);		
	}
	
}
