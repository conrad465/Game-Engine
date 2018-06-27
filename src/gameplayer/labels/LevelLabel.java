package gameplayer.labels;
import gameplayer.controller.GameManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;

public class LevelLabel extends Label implements IGameStatusLabel{

	private final String LEVEL_LABEL_NAME = "Level: ";
	private SimpleStringProperty levelProperty;
	
	
	public LevelLabel() {
		levelProperty = new SimpleStringProperty();
		this.textProperty().bind(levelProperty);
	}

	@Override
	public double extractGameStateValue(GameManager gameManager) {
		return gameManager.getActiveLevel();
	}

	@Override
	public void update(double newValue) {
		// TODO Auto-generated method stub
		String newStringValue = Double.toString(newValue);
		levelProperty.setValue(LEVEL_LABEL_NAME+ newStringValue);
	}

	
}
