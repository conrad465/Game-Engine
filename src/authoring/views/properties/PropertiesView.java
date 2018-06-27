package authoring.views.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import authoring.MainApplication;
import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import authoring.factories.ClickElementType;
import authoring.factories.Element;
import authoring.factories.ElementFactory;
import authoring.factories.ElementType;
import authoring.languages.AuthoringLanguage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Abstract properties view class which contains methods to creating and opening a 
 * properties view window, in which the user can edit the properties of a certain aspect
 * of the game.
 * @author Dylan Powers
 * @author Hemanth Yakkali
 */
public abstract class PropertiesView implements AuthoringLanguage {
	
	private static final String RESOURCES = "resources.views.Properties/";
	private static final String FORM_RESOURCES = "resources.views.Properties/FormProperties";
	private static final int PANE_SEPARATION = 10;
	private static final int PANE_HEIGHT = 400;
	private static final int PANE_WIDTH = 400;
	private GridPane root;
	private Stage stage;
	private ElementFactory eFactory = new ElementFactory();
	private ArrayList<Element> elements = new ArrayList<>();
	private Properties language = new Properties();

	/**
	 * Initialize the root of this window as a {@code GridPane}.
	 */
	public PropertiesView() {
		this.root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(PANE_SEPARATION);
		root.setVgap(PANE_SEPARATION);
		root.setMinSize(PANE_WIDTH, PANE_HEIGHT);
	}

	/**
	 * Opens the Property Editor window.
	 */
	public void open() {
		stage = new Stage();
		stage.setTitle(this.title());
		stage.setScene(new Scene(root));
		stage.getScene().getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());
		root.getStyleClass().add("properties-view");
		stage.show();
		this.fill();
		stage.sizeToScene();
		stage.getScene().getWindow().sizeToScene();
	}
	
	/**
	 * Fills the window with the appropriate names and fields.
	 * @param fields a map with component names that map {@code true} if the box should be strictly numeric, and {@code false} if not.
	 */
	protected abstract void fill();

	/**
	 * Get the title that this window should display.
	 * @return the title of the window.
	 */
	protected abstract String title();
	
	/**
	 * Creates labels for the specified properties view
	 * @param propsName Name of the Properties view
	 */
	protected void createLabels(String propsName) {
		int currentRow = 0;
		ResourceBundle props = this.getResourcesBundle(propsName);
		for (String property : props.keySet()) {
			try {
				Label label = (Label) this.getElementFactory().buildElement(ElementType.Label,props.getString(property));
				this.getRoot().add(label, 0, currentRow);
				this.getElementList().add((Element) label);
				currentRow++;
			} catch (Exception e) {
				throw new AuthoringException(this.getFormBundle().getString("LabelError"),AuthoringAlert.SHOW);
			}
		}
	}
	
	/**
	 * Closes the stage
	 */
	public void close() {
		stage.close();
	}

	/**
	 * Creates a confirmation alert
	 * @param content Content to be shown in the confirmation alert window.
	 * @return Confirmation Alert that contains the specified content.
	 */
	protected Alert makeSubmitAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(this.title()+ " " +this.getFormBundle().getString("Save"));
		alert.show();
		return alert;
	}
	
	/**
	 * Makes a submit button that performs an action on click. Submit button is then added
	 * to the root and to the element list.
	 * @param event Action that the button will perform on click.
	 */
	protected Button makeSubmitButton(Consumer<Void> event) {
		try {
			Button submit = (Button) this.getElementFactory().buildClickElement(ClickElementType.Button,this.getFormBundle().getString("Submit"), e->event.accept(null));
			this.elements.add((Element) submit);
			this.root.addColumn(0, submit);
			return submit;
		} catch (Exception e) {
			throw new AuthoringException(this.getFormBundle().getString("SubmitError"), AuthoringAlert.SHOW);
		}
	}

	/**
	 * Gets and returns the JavaFX-related root of the {@code PropertiesView}.
	 * @return the {@code GridPane} root.
	 */
	protected GridPane getRoot() {
		return this.root;
	}

	/**
	 * 
	 * @param propsName Name of the properties view
	 * @return Resourcebundle for a specified filepath
	 */
	protected ResourceBundle getResourcesBundle(String propsName) {
		return ResourceBundle.getBundle(PropertiesView.RESOURCES+propsName);
	}

	/**
	 * 
	 * @return ResourceBundle with names for buttons
	 */
	protected ResourceBundle getFormBundle() {
		return ResourceBundle.getBundle(PropertiesView.FORM_RESOURCES);
	}

	/**
	 * 
	 * @return ElementFactory
	 */
	protected ElementFactory getElementFactory() {
		return this.eFactory;
	}
	
	/**
	 * 
	 * @return List of form elements such as textfields, numberfields, etc. 
	 */
	protected List<Element> getElementList(){
		return this.elements;
	}

	@Override
	public void setLanguage(Properties lang) {
		language = lang;
		System.out.println(elements.size());
		for(Element e : elements) {
			e.setLanguage(language);
		}
	}

}
