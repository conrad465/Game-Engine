package authoring.forms;

import authoring.languages.AuthoringLanguage;
import javafx.geometry.Pos;

public interface ComponentForm extends AuthoringLanguage{

	public Object buildComponent();

	public String getName();

	public void setAlignment(Pos center);

	public void setValue(Object currentValue);

}
