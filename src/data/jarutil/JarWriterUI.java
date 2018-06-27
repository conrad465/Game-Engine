package data.jarutil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class JarWriterUI {
    private VBox options;
    private Group root;
    private Stage myStage;
    private Scene myScene;
    private TextArea name;
    private static final int SPACING = 10;
    private static final String OUTPUT = "Select Output";
    private static final String MODULE_PROMPT = "Do you have external modules?";
    private static final String IGNORE_PROMPT = "Do you have files you don't want to add to the jar?";
    private static final String DIR_FILE_PROMPT = "Is your game a file or a directory";
    private static final String CREATE = "Create Jar";
    private static final String MAIN = "Select Main";
    private static final String NAME_PROMPT = "Name your game";
    private static final int  XSIZE = 320;
    private static final int YSIZE = 190;
    private JarWriter jWrite;

    public JarWriterUI(){
        root = new Group();
        myStage = new Stage();
        myScene = new Scene(root, XSIZE, YSIZE);
        myStage.setScene(myScene);
        myStage.setResizable(false);
        myStage.show();
        jWrite = new JarWriter(myStage);
        configureMenu();
    }

    private void configureMenu(){
        VBox options = getMenuOptions();
        options.setAlignment(Pos.CENTER);
        root.getChildren().add(options);
    }

    private VBox getMenuOptions(){
        VBox options = new VBox();
        options.setSpacing(SPACING);
        Map<String, Consumer<Void>> ignoreoptions = new HashMap<String, Consumer<Void>>(){{
          put("Yes",e->jWrite.getIgnore());
          put("No",null);
        }};

        options.getChildren().add(buildCombo(IGNORE_PROMPT, ignoreoptions));

        Map<String, Consumer<Void>> gameOptions = new HashMap<String, Consumer<Void>>(){{
            put("Directory",e->jWrite.getGameDir());
            put("File",e->jWrite.getGameFile());
        }};

        options.getChildren().add(buildCombo(DIR_FILE_PROMPT, gameOptions));

        Map<String, Consumer<Void>> moduleOptions = new HashMap<String, Consumer<Void>>(){{
            put("Yes",e->jWrite.configureModules());
            put("No",null);
        }};
        name = new TextArea(NAME_PROMPT);
        name.setPrefHeight(10);
        name.setPrefWidth(50);
        options.getChildren().add(buildCombo(MODULE_PROMPT, moduleOptions));
        options.getChildren().addAll(name,getButtons());
        return options;
    }

    private HBox getButtons(){
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        Button create = new Button(CREATE);
        create.setOnAction(e->jWrite.buildJar(name.getText()));
        Button outPath = new Button(OUTPUT);
        outPath.setOnAction(e->jWrite.getOutput());
        Button main = new Button(MAIN);
        main.setOnAction(e->jWrite.selectMain());
        buttons.getChildren().addAll(outPath,main,create);
        return buttons;
    }

    public  ComboBox buildCombo(String prompt,  Map<String,Consumer<Void>> event)
    {
        ComboBox<String> combobox = new ComboBox<>();
        for (String option : event.keySet()) {
            combobox.getItems().add(option);
        }
        combobox.setOnAction(e->event.get((String)combobox.getValue()).accept(null));
        combobox.setPromptText(prompt);
        return combobox;
    }

}
