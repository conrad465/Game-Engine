package gameplayer.labels;
import java.util.Map;

import engine.components.Component;
import engine.components.Lives;
import engine.components.Player;
import gameplayer.controller.GameManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;

public class LivesLabel extends Label implements IGameStatusLabel{

	private final String LIVES_LABEL_NAME = "Lives: ";
	private SimpleStringProperty livesProperty;
	
	
	public LivesLabel() {
		livesProperty = new SimpleStringProperty();
		this.textProperty().bind(livesProperty);
	}


	@Override
	public double extractGameStateValue(GameManager gameManager) {
		return gameManager.getLives();
	}


	@Override
	public void update(double newValue) {
		// TODO Auto-generated method stub
		String newStringValue = Double.toString(newValue);
		livesProperty.set(LIVES_LABEL_NAME+newStringValue);
	}
	
	
}
