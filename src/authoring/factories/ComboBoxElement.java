package authoring.factories;

import java.util.Properties;
import java.util.function.Consumer;

import javafx.scene.control.ComboBox;

/**
 * Handles the creation of a ComboBox through the use of the ElementFactory. 
 * @author Hemanth Yakkali
 *
 */
public class ComboBoxElement extends ComboBox implements ClickableElement {
	
	public ComboBoxElement(String text) {
		super();
		this.setId(text);
		handleText(text);
	}

	@Override
	public void handleText(String text) {
		this.setPromptText(text);
	}

	@Override
	public void handleConsumer(Consumer<Void> event) {
		this.setOnAction(e->event.accept(null));
	}

	@Override
	public void setLanguage(Properties language) {
		this.handleText(language.getProperty(this.getId(),this.getId()));
		
	}
	

}
