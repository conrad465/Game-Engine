package authoring.factories;

import java.util.Properties;

import javafx.scene.control.Label;

/**
 * Handles the creation of a Label through the use of the ElementFactory. 
 * @author Hemanth Yakkali(hy115)
 *
 */
public class LabelElement extends Label implements Element{
	public LabelElement(String text) {
		this.setId(text);
		this.handleText(text);
		this.getStyleClass().add("label");
	}

	@Override
	public void handleText(String text) {
		this.setText(text);
	}

	@Override
	public void setLanguage(Properties language) {
		this.handleText(language.getProperty(this.getId(), this.getId()));
	}

}
