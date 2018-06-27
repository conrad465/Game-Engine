package gameplayer.labels;
import java.util.Map;

import engine.components.Component;
import engine.components.Health;
import gameplayer.controller.GameManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;

public class HealthLabel extends Label implements IGameStatusLabel{

	private final String HEALTH_LABEL_NAME = "Health: ";
	private SimpleStringProperty healthProperty;
	
	
	public HealthLabel() {
		healthProperty = new SimpleStringProperty();
		this.textProperty().bind(healthProperty);
	}

	@Override
	public double extractGameStateValue(GameManager gameManager) {
		Health health = (Health) gameManager.getPlayerKeys().get(gameManager.getActiveLevel()).get(Health.KEY);
		return health.getData();
	}
//	@Override
//	public double extractGameStateValue(Map<String, Component> playerStatusMap) {
//		Health health = (Health) playerStatusMap.get(Health.KEY);
//		return health.getData();
//	}

	@Override
	public void update(double newValue) {
		String newStringValue = Double.toString(newValue);
		healthProperty.setValue(HEALTH_LABEL_NAME + newStringValue);
	}

	
}
