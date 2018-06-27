package authoring.entities.componentbuilders;

import engine.components.Component;
import engine.components.SingleDataComponent;
import engine.components.SingleStringComponent;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

import org.w3c.dom.Element;

import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
/**
 * Class to build a sprite from a given image path
 * @author dylanpowers
 *
 */
public class ComponentBuilder {

	private final String ERROR_MESSAGE = "The component %s could not be built. Please refer to documentation and check that the component exists in the engine.components package.";

	public Component build(int ID, Element e) {
		try {
			Class<?> clazz = Class.forName("engine.components." + e.getNodeName());
			Constructor<?> cons = clazz.getDeclaredConstructors()[0];
			Component component;
			System.out.println(clazz.getSuperclass());
			if (SingleDataComponent.class.isAssignableFrom(clazz)) {
				component = (Component) cons.newInstance(ID, Double.valueOf(e.getTextContent()));
			} else if (SingleStringComponent.class.isAssignableFrom(clazz)) {
				component = (Component) cons.newInstance(ID, e.getTextContent());
			} else {
				component = (Component) cons.newInstance(ID);
			}
			System.out.println("Component " + component + " created successfully.");
			return component;
		} catch (Exception e2) {
			throw new AuthoringException(ERROR_MESSAGE, AuthoringAlert.SHOW, e.getNodeName());
		}
	}

}
