package authoring.entities.data;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import engine.components.Type;

/**
 * 
 * @author Collin Brown(Cdb55)
 *
 */
public class SudoEntityLoader {

	private DocumentBuilder documentBuilder;
	private final String ERROR_MESSAGE = "The component %s is invalid.";
	private final String COMPONENT_PREFIX = "engine.components.";
	private final String TYPE = Type.KEY;

	public SudoEntityLoader() {
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			//LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * Get and build an entity that is represented by a given XML File.
	 * @param entityName the name of the entity to pull the xml file for
	 * @param ID the ID of the entity to create
	 * @param type the type of entity to instantiate
	 * @return an Entity object represented by the xml file
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Object[] buildEntity(File entityFile) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, ClassNotFoundException {
		Object[] result = new Object[2];
		Element root = getRootElement(entityFile);
		String type = extractTypeFromRoot(root);
		result[0] = type;
		NodeList nList = root.getChildNodes();
		Map<Class,Object[]> sudoComponents = new HashMap<>();
		for (int i = 0; i < nList.getLength(); i++) {
			sudoComponents.put(this.getClass().forName(COMPONENT_PREFIX + nList.item(i).getNodeName().trim()),
					new Object[] {nList.item(i).getTextContent()});
		}
		result[1] = sudoComponents;
		return result;
	}	

	/**
	 * Get the root element of an xml file to parse
	 * @param XMLFile the xml file to parse
	 * @return the root element of the file
	 */
	private Element getRootElement(File XMLFile) {
		try {
			documentBuilder.reset();
			Document XMLDoc = documentBuilder.parse(XMLFile);
			return XMLDoc.getDocumentElement();
		} catch (Exception e) {
			//LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get the text value contained within an XML element.
	 * @param e the element to be queried
	 * @param name the name of the component to be searched
	 * @return the text value of this certain element
	 */
	private String getTextValue(Element e, String name) {
		NodeList nList = e.getElementsByTagName(name);
		if (nList != null && nList.getLength() > 0) {
			return nList.item(0).getTextContent();
		} else {
			throw new AuthoringException(ERROR_MESSAGE, AuthoringAlert.SHOW, name);
		}
	}

	/**
	 * Given the root of the xml document, extract the type of the entity, which is necessary to instantiate it.
	 * @param root the root of the xml document
	 * @return the type of the entity to be created
	 */
	private String extractTypeFromRoot(Element root) {
		return getTextValue(root, TYPE);
	}
}
