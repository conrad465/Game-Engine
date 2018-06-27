package authoring.factories;

import authoring.languages.AuthoringLanguage;

/**
 * Interface to be implemented by any non-clickable Element class.
 * @author Hemanth Yakkali(hy115)
 *
 */
public interface Element extends AuthoringLanguage{

	public void handleText(String text);
	
}
