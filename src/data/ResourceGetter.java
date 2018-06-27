package data;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;


public class ResourceGetter {
    /*@Author Conrad builds the prompt and browser that allows the user to import images from the internet

     */
    private static final int HEIGHT = 615;
    private static final int WIDTH = 800;


    public ResourceGetter() {}

    public void selectImage() {
        /*build form*/
        Stage stage = new Stage();
        stage.setResizable(false);
        Browser b = new Browser(e->stage.close());
        Scene scene = new Scene(b, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
class Browser extends Region {
    /*creates the broeser and prompts
     */

    private static final String START_URL ="http://www.google.com";
    private static final String IMAGE_PROMPT = "Navigate to an image url (Hint: 'open image in new window')";
    private static final String NAMING_ERROR = "Sorry, name was not accepted. Try again. (Hint: use '.jpg', '.png', '.gif'";
    private static final String RENAME_IMAGE= "Give a name to this image";
    private static final String BACK_BUTTON = "Back";
    private static final String LOAD_BUTTON = "Load Image";
    private static final String FORWARD_BUTTON= "Forward";
    private static final String SUCCESS ="Success";


    private  WebView browser = new WebView();
    private  WebEngine webEngine = browser.getEngine();
    private Messager messager;
    private URL imageURL;private Consumer<Void> close;
    private Button select;


    Browser(Consumer<Void> closeStage) {
        webEngine.load(START_URL);
        getChildren().add(loadMenu());
        setOnMouseClicked(e -> { if( select.isDisabled())messager.setText(IMAGE_PROMPT);  });
        webEngine.setOnStatusChanged(e->selectImage());
    }

    private VBox loadMenu() {
        VBox view = new VBox();
        HBox menu = new HBox();

        Button back = new Button(BACK_BUTTON); back.setOnAction( e->{back();});
        select =new Button(LOAD_BUTTON); select.setOnAction(e->{setImageName(messager.getText());});
        select.setDisable(true);
        Button forward= new Button(FORWARD_BUTTON); forward.setOnAction(e->{forward();});

        messager = new Messager();

        menu.getChildren().addAll( back,forward, select, messager);
        view.getChildren().addAll(browser, menu);
        return view;
    }

    private void selectImage() {
        try {
            imageURL = new URL(browser.getEngine().getLocation());
            BufferedImage image = image = ImageIO.read(imageURL);
            Image loadedImage  = SwingFXUtils.toFXImage(image, null);
            messager.rename();
            select.setDisable(false);
        } catch (Exception e) {
            select.setDisable(true);
            messager.abort();
        }
    }

    private void back() {
        try{
            webEngine.getHistory().go(-1);
            select.setDisable(true);
            messager.abort();
        }
        catch(Exception e)
        {}
    }

    private void forward() {
        try{
            webEngine.getHistory().go(1);
            select.setDisable(true);
            messager.abort();

        }
        catch(Exception e)
        {}
    }

    private void setImageName(String name){
        try {
            DataWrite.writeImage(imageURL,name);
            messager.setText(SUCCESS);
        } catch (IOException e) {
            messager.setText(NAMING_ERROR);
        }
    }


    public class Messager extends TextArea {
        Consumer<String> namer;
        Messager() {
            setText(IMAGE_PROMPT);
            setEditable(false);
        }

        void rename() {
            setEditable(true);
            clear();
            setPromptText(RENAME_IMAGE);
        }

        void abort(){
            setEditable(false);
            setText(IMAGE_PROMPT);
        }
    }





}

