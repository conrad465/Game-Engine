package authoring.actions;

import authoring.entities.Entity;
import javafx.scene.control.TextArea;



public class IntSupplier extends Supplier {
    /* @Author Conrad prompts user for double to help make actions

     */
    private int dub;
    private TextArea keyInput;
    private static final String KEY_PROMPT = "Input an int Corresponding to an entity";
    private static final int XSIZE = 200;
    private static final int YSIZE =70;
    private static final String INVALID = "Invalid Int";
    private static final String VALID = "Accepted int";

    IntSupplier(Entity e){
        super(KEY_PROMPT, XSIZE, YSIZE, e);
    }

    protected void configureMenu(){
        keyInput = new TextArea();
        keyInput.setPrefHeight(10);
        keyInput.setOnKeyPressed(e->checkCode(e.getText()));
        menu.getChildren().add(0,keyInput);
        menu.setPrefHeight(YSIZE);
        prompt.setText(INVALID);

    }

    @Override
    protected Object getData() {
        return dub;
    }

    private void checkCode(String num){
        try{
            dub = Integer.valueOf(keyInput.getText()+num);
            prompt.setText(VALID);
        }
        catch(NumberFormatException e){
            prompt.setText(INVALID);
        }
    }
}
