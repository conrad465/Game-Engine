package authoring.actions;

import authoring.entities.Entity;
import javafx.scene.control.TextArea;



public class DoubleSupplier extends Supplier {
/* @Author Conrad prompts user for double to help make actions

 */
    private double dub;
    private TextArea keyInput;
    private static final String KEY_PROMPT = "Input a double and press enter";
    private static final int XSIZE = 350;
    private static final int YSIZE =100; //75
    private static final String INVALID = "Invalid Double";
    private static final String VALID = "Accepted Double";

    DoubleSupplier(Entity e){
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
            dub = Double.valueOf(keyInput.getText()+num);
            prompt.setText(VALID);
        }
        catch(NumberFormatException e){
            prompt.setText(INVALID);
        }
    }
}
