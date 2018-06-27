package gameplayer.menu;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class GameOverMenu extends Popup implements IMenu {

	
	
	/**
	 * Constructor for the GameOver Menu Popup
	 */
	public GameOverMenu() {
		VBox pane = new VBox();
		pane.getChildren().addAll(new Button("Play Again"));
		this.getContent().add(pane);
		
	}
	
	@Override
	public void toggleMenu() {
		// TODO Auto-generated method stub
		
	}

}
