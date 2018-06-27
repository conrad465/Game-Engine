package authoring.factories;

import java.util.function.Consumer;

/**
 * Factory class that is used to build a frontend element that is defined in this package. Example
 * elements include buttons and menuitems. A new frontend element can be added by adding a new field
 * to either the Enum ElementType or the Enum ClickElementType.
 * @author Hemanth Yakkali(hy115)
 */
public class ElementFactory {

	private final static String PACKAGE_NAME = "authoring.factories.";
	private final static String ELEMENT = "Element";

	/**
	 * Empty constructor, makes it easier to be instantiated anywhere in the project
	 */
	public ElementFactory() {}

	/**
	 * Builds a non-clickable element of a specified type and contains the specified text.
	 * @param elementName Type of Element to be created
	 * @param text {@code String} text to be placed in the new Element
	 * @return Desired element
	 * @throws Exception
	 */
	public Element buildElement(ElementType elementName, String text) throws Exception {
		String builderName = PACKAGE_NAME+elementName+ELEMENT;
		Class<?> elementClass = Class.forName(builderName);
		return (Element) elementClass.getConstructor(text.getClass()).newInstance(text);		
	}
	
	/**
	 * Builds a clickable element of a particular type containing a specific action and text.
	 * @param elementName Type of Element to be created
	 * @param text {@code String} text to be placed in the new Element
	 * @param event {@code Consumer} event that the element will perform on action
	 * @return Desired clickable element
	 * @throws Exception
	 */
	public Element buildClickElement(ClickElementType elementName, String text, Consumer<Void> event) throws Exception {
		String builderName = PACKAGE_NAME+elementName+ELEMENT;
		Class<?> elementClass = Class.forName(builderName);
		ClickableElement element = (ClickableElement) elementClass.getConstructor(text.getClass()).newInstance(text);		
		element.handleConsumer(event);
		return element;
	}

}
