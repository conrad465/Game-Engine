package authoring.factories;

import java.util.Properties;

import javafx.scene.control.Tab;

/**
 * Handles the creation of a Tab through the use of the ElementFactory. 
 * @author Hemanth Yakkali(hy115)
 *
 */
public class TabElement extends Tab implements Element{

	public TabElement(String text) {
		super();
		this.handleText(text);
		this.setId(text);
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
