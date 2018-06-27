package authoring.views.popups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.logging.Logger;

import authoring.MainApplication;
import authoring.factories.Element;
import authoring.factories.ElementFactory;
import authoring.factories.ElementType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * 
 * @author Collin Brown(Cdb55)
 *
 */
public class SelectionBox extends VBox implements PopUp {

	private Properties language = new Properties();
	private ArrayList<Element> elements = new ArrayList<>();
	private String selection = "";
	private Stage stage;
	private ElementFactory eFactory = new ElementFactory();
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final static int WIDTH = 200;

	public SelectionBox(String[] options, String[] exceptions, Consumer consumer){
		this.fill(options, consumer, exceptions);
		this.show();
	}
	
	/**
	 * Builds the list of options
	 * @param options
	 */
	private void fill(String[] options, Consumer consumer, String[] exceptions) {
		for(String o: options) {
			if (!Arrays.asList(exceptions).contains(o)) {
				try {
					Label label = (Label) eFactory.buildElement(ElementType.Label, o);
					label.getStyleClass().add("selection-label");
					label.setAlignment(Pos.CENTER);
					label.setPrefWidth(WIDTH);
					label.setOnMouseClicked(e->{
						selection = label.getId();
						this.onClose(consumer);
						stage.close();
					});
					this.getChildren().add(label);
					elements.add((Element) label);
				} catch (Exception e) {
					LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
				}
			}
		}
	}

	@Override
	public void show() {
		stage = new Stage();
		this.getStyleClass().add("selection-box");
		stage.setScene(new Scene(this));
		stage.getScene().getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());
		stage.show();
		stage.sizeToScene();
	}
	
	public void onClose(Consumer consumer) {
		consumer.accept(selection);
	}

	public void setLanguage(Properties lang) {
		this.language = lang;
		for(Element e: elements) {
			e.setLanguage(language);
		}
	}
}
