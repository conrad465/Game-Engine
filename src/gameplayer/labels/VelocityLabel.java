
package gameplayer.labels;
import java.text.MessageFormat;
import java.util.Map;

import engine.components.Component;
import engine.components.XVelocity;
import gameplayer.controller.GameManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;

public class VelocityLabel extends Label implements IGameStatusLabel {

	private double currentVelocity;
	private final String VELOCITY_LABEL_NAME = "Velocity";
	private SimpleDoubleProperty velocityProperty;
	private Map<String, Component> playerStatusMap;
	
	
	public VelocityLabel(double velocity) {
		currentVelocity = velocity;
		velocityProperty = new SimpleDoubleProperty(currentVelocity);
		this.textProperty().bind(velocityProperty.asString());
	}
	
	//Interface method to extract the data given a playerStatus Map.
	public double extractGameStateValue(Map<String, Component> playerStatusMap) {
		XVelocity velocity = (XVelocity) playerStatusMap.get(XVelocity.KEY);
		return velocity.getData();
	}
	
	public void update(double newValue) {
		velocityProperty.setValue(newValue);
	}

	@Override
	public double extractGameStateValue(GameManager gameManager) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}

