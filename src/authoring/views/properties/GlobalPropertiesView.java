package authoring.views.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import authoring.factories.ElementType;
import authoring.factories.NumberField;
import authoring.gamestate.Level;
import data.DataUtils;
import javafx.scene.control.TextField;

/**
 * Creates properties view form that allows user to update the global properties of the game. 
 * @author Hemanth Yakkali(hy115)
 */
public class GlobalPropertiesView extends PropertiesView {

	private List<Level> levels;

	public GlobalPropertiesView(List<Level> levels){
		super();
		this.levels = new ArrayList<>(levels);
	}

	@Override
	protected String title() {
		return this.getFormBundle().getString("Global");
	}

	@Override
	protected void fill() {
		ResourceBundle globalProps = this.getResourcesBundle(this.title().replace(" ", ""));
		try {
			this.createLabels(this.title().replace(" ", ""));
			//text argument is empty, no text needed for these fields
			TextField titleInput = (TextField) this.getElementFactory().buildElement(ElementType.TextField,""); 
			NumberField livesInput = (NumberField) this.getElementFactory().buildElement(ElementType.NumberField,"");
			TextField pathInput = (TextField) this.getElementFactory().buildElement(ElementType.TextField,"");
			this.getRoot().addColumn(1,livesInput,titleInput,pathInput);
			this.makeSubmitButton(e->{
				for(Level level : levels) {
					DataUtils.setGame(titleInput.getText());
					level.addGProp(globalProps.getString("Title"), titleInput.getText());
					level.addGProp(globalProps.getString("Lives"), livesInput.getText());
					level.addGProp(globalProps.getString("Filepath"), pathInput.getText());
				}
				this.makeSubmitAlert();
				this.close();
			});
		} catch (Exception e1) {
			throw new AuthoringException(this.getFormBundle().getString("FormError"),AuthoringAlert.SHOW);
		}
	}

}
