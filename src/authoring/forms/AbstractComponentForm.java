package authoring.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.logging.Logger;

import authoring.entities.data.PackageExplorer;
import authoring.factories.ClickElementType;
import authoring.factories.Element;
import authoring.factories.ElementFactory;
import authoring.factories.ElementType;
import engine.components.BehaviorComponent;
import engine.components.DataComponent;
import engine.components.FlagComponent;
import engine.components.StringComponent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * 
 * @author Collin Brown
 *
 */
public abstract class AbstractComponentForm extends GridPane {
	protected static final String COMPONENT_PREFIX = "engine.components.";
	protected String name;
	protected Button deleteButton;
	protected int numFields;
	protected List<TextField> fields = new ArrayList<>();
	protected List<Label> labels = new ArrayList<>();
	protected List<String> oneParams = new ArrayList<String>() {{
		this.add("FlagComponent");
		this.add("Win");
		this.add("AI");
		this.add("Collidable");
		}};
	protected Consumer<ComponentForm> deleteComponent;

	protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	protected Properties language = new Properties();
	
	protected ElementFactory eFactory = new ElementFactory();
	protected ArrayList<Element> elements = new ArrayList<>();
	protected TextField field;

	protected abstract Object buildComponent();
	
	public AbstractComponentForm(String name, Consumer<ComponentForm> onDelete) throws Exception {
		this.name = name;
		this.deleteComponent = onDelete;
		int col = 0;
		Label label = (Label) eFactory.buildElement(ElementType.Label, name);
		label.getStyleClass().add("component-form-label");
		col++;
		this.add(label, col, 0);
		this.numFields = PackageExplorer.getNumFields(name);
		elements.add((Element) label);
		if(FlagComponent.class.isAssignableFrom(Class.forName(COMPONENT_PREFIX + name)) || BehaviorComponent.class.isAssignableFrom(Class.forName(COMPONENT_PREFIX + name))) {
			this.field = (TextField) eFactory.buildElement(ElementType.TextField, "True");
			this.field.setEditable(false);
		}
		if(oneParams.contains(this.name)) {
			this.field = (TextField) eFactory.buildElement(ElementType.NumberField, "1");
		}
		for (int i = 0; i < (numFields-1); i++) {
			TextField tf = null;
			if(DataComponent.class.isAssignableFrom(Class.forName(COMPONENT_PREFIX + name))) {
				tf = (TextField) eFactory.buildElement(ElementType.NumberField, "Enter a Number");
			} else {
				tf = (TextField) eFactory.buildElement(ElementType.TextField, "Enter Text");
			}
			tf.getStyleClass().add("component-text-field");
			field = tf;
			elements.add((Element) tf);
			fields.add(tf);
			col++;
			this.add(tf, col, 0);
		}
		col++;
		this.deleteButton = (Button) eFactory.buildClickElement(ClickElementType.Button,"X", e -> {
			this.deleteComponent.accept((ComponentForm) this);
		});
		this.add(deleteButton, col, 0);
	}

	/**
	 * Check if the user fully entered something in this form. If the form has multiple text boxes, then
	 * the user should have input two separate things. If not, the program should alert them of this.
	 * @return true if the user fully entered everything that they should enter
	 */
	protected boolean validComponent() {
		TextField tf = this.field;
		System.out.println(this.name);
			if (tf.getText().isEmpty() && !oneParams.contains(this.name) ) {
				return false;
			}
		System.out.println("yooo" + this.name);
		return true;
	}

	/**
	 * Casts the {@code String} in the {@code TextField} to the appropriate class based upon the types
	 * declared in the constructor.
	 * @param desiredType the type that the {@code String} should be cast to
	 * @param text the text to be cast
	 * @return an Object representing the casted class
	 */
	protected Object cast(Class<?> desiredType, String text) {
		Object reflectValue;
		if (desiredType.equals(double.class)) {
			reflectValue = Double.parseDouble(text);
		}
		else {
			reflectValue = text;
		}
		return reflectValue;
	}

}
