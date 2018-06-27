package authoring.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Logger;

import authoring.entities.data.PackageExplorer;
import authoring.factories.ClickElementType;
import authoring.factories.ElementFactory;
import authoring.languages.AuthoringLanguage;
import authoring.views.popups.SelectionBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public abstract class AbstractComponentFormCollection extends GridPane implements AuthoringLanguage{
	private final static String PROPERTIES_PACKAGE = "resources.menus.Entity/";
	private final static String ENTITIES_PACKAGE = "engine.components.";
	protected Properties language = new Properties();

	ElementFactory eFactory = new ElementFactory();

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private List<ComponentForm> activeForms = new ArrayList<>();
	private List<String> exceptions;

	
	protected int currentRow;
	protected int entityID;
	
	protected Button addComponentButton;
	protected Button saveButton;
	
	private Class<?> componentFormType;
	
	protected Consumer onSave;
	protected Consumer deleteConsumer = e -> {delete((ComponentForm) e);};
	

	public AbstractComponentFormCollection(String[] newExceptions, Consumer c, Class cType) {
		this(cType);
		getExceptions().addAll(Arrays.asList(newExceptions));
		onSave = c;
		
	}

	private void deleteComponent(Object componentForm) {
		this.activeForms.remove(componentForm);
		this.getChildren().remove(componentForm);
		
	}

	public AbstractComponentFormCollection(Class cType) {
		componentFormType = cType;
		this.getStyleClass().add("component-form");
		setExceptions(new ArrayList<>());
	}

	protected void createAddComponentButton(int row) {
		try {
			addComponentButton = (Button) eFactory.buildClickElement(ClickElementType.Button,"AddComponent", onClick->{
				String[] options = PackageExplorer.getElementsInPackage(ENTITIES_PACKAGE, ".class","Component");
				String[] exceptions = new String[] {"Component","Conditional","DataComponent", "ReadDataComponent", "ReadStringComponent","StringComponent", "SingleDataComponent", "SingleStringComponent"};
				SelectionBox  selectionBox = new SelectionBox(options, exceptions, us -> {update(us);});
				selectionBox.setLanguage(language);
			});
			this.add(addComponentButton, 0, row);
			this.setAlignment(Pos.CENTER);
			this.getParent().getScene().getWindow().sizeToScene();
		} catch (Exception e) {
			LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
		}
	}
	
	protected void createSaveButton(int row) {
		try {
			saveButton = (Button) eFactory.buildClickElement(ClickElementType.Button, "Save", onClick->{
				save(onSave);
			});
			this.add(saveButton, 0, row);
			this.setAlignment(Pos.CENTER);
			this.getParent().getScene().getWindow().sizeToScene();
		} catch (Exception e) {
			LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
		}
	}

	public List<ComponentForm> getActiveForms() {
		return activeForms;
	}

	public void setActiveForms(List<ComponentForm> activeForms) {
		this.activeForms = activeForms;
	}

	public static String getPropertiesPackage() {
		return PROPERTIES_PACKAGE;
	}

	protected List<String> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}

	@Override
	public void setLanguage(Properties lang) {
		this.language = lang;
		for(ComponentForm componentForm : activeForms) {
			componentForm.setLanguage(this.language);
		}
	}

	
	public void setSaveConsumer(Consumer onSave) {
		saveButton.setOnMouseClicked(e->{
			onSave.accept(this.getActiveForms());
		});
	}
	
	public void fill(List<String> entityProperties) {
		try {
		currentRow = 0;
		this.getChildren().clear();
		for (String property : entityProperties) {
			if(!getExceptions().contains(property)) {
				this.addComponent(property);
			}
		}
		this.createAddComponentButton(currentRow);
		currentRow++;
		this.createSaveButton(currentRow);
		currentRow++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void update(Object componentName) {
		this.getChildren().remove(addComponentButton);
		this.getChildren().remove(saveButton);
		this.addComponent((String) componentName);
		this.createAddComponentButton(currentRow);
		currentRow++;
		this.createSaveButton(currentRow);
		currentRow++;
		
		
	}
	
	/**
	 * Adds a new component form to the active set of forms
	 * @param componentName
	 */
	private void addComponent(String componentName) {
		try {
			ArrayList<ComponentForm> newActiveForms = new ArrayList<>();
			newActiveForms.addAll(this.getActiveForms());
			ComponentForm cf = null;
			if(componentFormType.equals(PropertiesComponentForm.class)) {
				System.out.println(componentName.toString());
				cf = (ComponentForm) componentFormType.getConstructors()[0].newInstance(entityID, (componentName), deleteConsumer);
			} else {
				cf = (ComponentForm) componentFormType.getConstructors()[0].newInstance((componentName), deleteConsumer);
			}
			if(hasCurrentValue(componentName)) {
				cf.setValue(getCurrentValue(componentName));
			}
			cf.setAlignment(Pos.CENTER);
			this.add((Node) cf, 0, currentRow);
			cf.setLanguage(language);
			newActiveForms.add(cf);
			this.setActiveForms(newActiveForms);
			currentRow++;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected abstract boolean hasCurrentValue(String componentName);
	protected abstract Object getCurrentValue(String componentName);
	
	protected abstract void save(Consumer c);
	protected abstract void delete(ComponentForm cf);



}
