package authoring.factories;

import java.util.Properties;

import javafx.scene.control.CheckBox;

/**
 * Handles the creation of a CheckBox through the use of the ElementFactory. 
 * @author Hemanth Yakkali(hy115)
 *
 */
public class CheckBoxElement extends CheckBox implements Element{

	public CheckBoxElement(String text) {
		super();
		this.handleText(text );
		this.setId(text);
		this.getStyleClass().add("checkbox");
	}

	@Override
	public void handleText(String text) {
		this.setText(text);	
	}
	
	@Override
	public void setLanguage(Properties language) {
		this.handleText(language.getProperty(this.getId(),this.getId()));
	}

}
