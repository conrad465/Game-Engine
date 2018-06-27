package authoring.views.properties;

import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import authoring.factories.ElementType;
import authoring.factories.NumberField;
import authoring.gamestate.Level;
import javafx.scene.control.TextField;

/**
 * Creates properties view form that allows user to update the properties for the specific level.
 * @author Hemanth Yakkali (hy115)
 */
public class LevelPropertiesView extends PropertiesView{

	private int levelNum;
	private Level level;

	public LevelPropertiesView(Level level, int levelNum) {
		super();
		this.levelNum = levelNum;
		this.level = level;
	}

	@Override
	protected String title() {
		String[] title = this.getFormBundle().getString("Level").split(" ");
		return title[0]+" "+levelNum+" "+title[1]; //need to split to add level number in the middle
	}

	@Override
	protected void fill() {
		try {
			this.createLabels(this.getFormBundle().getString("Level").replaceAll(" ", ""));
			//text argument is empty, no text needed for these fields
			TextField infoText = (TextField) this.getElementFactory().buildElement(ElementType.TextField,"");
			NumberField timeNumber = (NumberField) this.getElementFactory().buildElement(ElementType.NumberField,"");
			this.getRoot().addColumn(1,timeNumber,infoText);
			this.makeSubmitButton(e->{
				level.setLevelInfo(infoText.getText());
				level.setLevelTime(Double.parseDouble(timeNumber.getText()));
				this.makeSubmitAlert();
				this.close();
			});
		} catch (Exception e) {
			throw new AuthoringException(this.getFormBundle().getString("FormError"), AuthoringAlert.SHOW);
		}
	}

}
