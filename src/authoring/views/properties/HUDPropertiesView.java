package authoring.views.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import authoring.factories.Element;
import authoring.factories.ElementType;
import authoring.gamestate.Level;
import javafx.scene.control.CheckBox;

/**
 * Creates properties view form that allows user to update the HUD properties of the game.
 * @author Hemanth Yakkali(hy115)
 *
 */
public class HUDPropertiesView extends PropertiesView{

	private List<Level> levels;

	public HUDPropertiesView(List<Level> levels) {
		super();
		this.levels = new ArrayList<>(levels);
	}
	
	@Override
	protected String title() {
		return this.getFormBundle().getString("HUD");
	}

	@Override
	protected void fill() {
		ResourceBundle HUDProps = this.getResourcesBundle(this.title().replace(" ", ""));
		try {
			CheckBox livesBox = (CheckBox) this.getElementFactory().buildElement(ElementType.CheckBox, HUDProps.getString("Lives"));
			CheckBox healthBox = (CheckBox) this.getElementFactory().buildElement(ElementType.CheckBox, HUDProps.getString("Health"));
			CheckBox levelBox = (CheckBox) this.getElementFactory().buildElement(ElementType.CheckBox, HUDProps.getString("Levels"));
			CheckBox scoreBox = (CheckBox) this.getElementFactory().buildElement(ElementType.CheckBox, HUDProps.getString("Score"));
			this.getRoot().addColumn(0, healthBox,livesBox,levelBox,scoreBox);
			this.makeSubmitButton(e->{
				for(Level level : levels) {
					level.addHUDProp(HUDProps.getString("Lives"), livesBox.isSelected());
					level.addHUDProp(HUDProps.getString("Health"), healthBox.isSelected());
					level.addHUDProp(HUDProps.getString("Levels"), levelBox.isSelected());
					level.addHUDProp(HUDProps.getString("Score"), levelBox.isSelected());
				}
				this.makeSubmitAlert();
				this.close();
			});
			this.getElementList().addAll(Arrays.asList(new Element[] {(Element) livesBox, (Element) healthBox, (Element) levelBox}));
		} catch (Exception e2) {
			throw new AuthoringException(this.getFormBundle().getString("FormError"),AuthoringAlert.SHOW);
		}	
	}

}
