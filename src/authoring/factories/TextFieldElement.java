package authoring.factories;

import java.util.Properties;

import javafx.scene.control.TextField;

/**
 * Handles the creation of a TextField through the use of the ElementFactory. 
 * @author Hemanth Yakkali(hy115)
 *
 */
public class TextFieldElement extends TextField implements Element{

	public TextFieldElement(String text) {
		super();
		this.handleText(text);
		this.setId(text);
		this.getStyleClass().add("field");
	}

	@Override
	public void handleText(String text) {
		this.setPromptText(text);
	}

	@Override
	public void setLanguage(Properties language) {
		this.handleText(language.getProperty(this.getId(),this.getId()));
	}

}
