package gameplayer.labels;

import java.util.Map;

import engine.components.Component;
import gameplayer.controller.GameManager;

public interface IGameStatusLabel{

	public double extractGameStateValue(GameManager gameManager);
	
	public void update(double newValue);
	
	
}
