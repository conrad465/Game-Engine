package gameplayer.labels;

import javafx.scene.control.Label;

public class TimeLabel extends Label{
	
	private final String TIME_LABEL_NAME = "time: ";
	private int currentTime;
	/**
	 * constructor that specifies how much 
	 * @param time
	 * if time == 0, then timer counts up from 0, else, timer counts down 
	 */
	public TimeLabel(int time) {
		currentTime = time;
		this.setText(TIME_LABEL_NAME + currentTime);
		//bind property value to the time of the game
	}
	
}
