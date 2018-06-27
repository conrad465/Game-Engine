package gameplayer.labels;
import java.text.MessageFormat;

import gameplayer.hud.IHUD;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

public class ScoreLabel extends Label implements IHUD{

	private double currentScore;
	private final String SCORE_LABEL_NAME = "Score: ";
	private SimpleDoubleProperty scoreProperty;
	
	
	public ScoreLabel(double score) {
		currentScore = score;
		scoreProperty = new SimpleDoubleProperty(currentScore);
		StringBinding binding = Bindings.createStringBinding(
	            () -> MessageFormat.format(SCORE_LABEL_NAME+"{0}", scoreProperty.getValue()));
		this.textProperty().bind(binding);
	}

	/**
	 * Method for changing between levels
	 */
	@Override
	public void updateHUD() {
		// TODO Auto-generated method stub
		
	}

	
}
