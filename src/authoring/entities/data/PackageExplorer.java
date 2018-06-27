package authoring.entities.data;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.scene.control.Alert;

public class PackageExplorer {
	private static final String COMPONENT_PREFIX = "engine.components.";
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public PackageExplorer() {
		//
	}

	public static String[] getElementsInPackage(String packageName, String fileType, String superClass) {
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		if (cld == null) {
			throw new IllegalStateException("Can't get class loader.");
		}

		URL resource = cld.getResource(packageName.replace('.', '/'));
		if (resource == null) {
			throw new RuntimeException("Package " + packageName + " not found on classpath.");
		}
		File directory = new File(resource.getFile());
		if (!directory.exists()) {
			throw new IllegalArgumentException("Could not get directory resource for package " + packageName + ".");
		}
		List<String> classes = new ArrayList<>();
		for (String filename : directory.list()) {
			if (filename.endsWith(fileType) && !filename.contains(superClass)) { //Check to make sure its a class file and not the superclass
				String classname = buildClassname(packageName, filename);
				String clazz = classname.replace(".java", "");
				// Strip everything except for the word following the last period (the actual class name)
				classes.add(clazz.substring(clazz.lastIndexOf(".") + 1));
			}
		}
		return classes.toArray(new String[classes.size()]);

	}

	/**
	 * Gets all of the class names from a given package. Useful when determining which properties can be changed.
	 * @return a String array of classes from a given package
	 */
	public static String[] getElementsInPackage(String packageName) {
		return getElementsInPackage(packageName, "", "!");
	}

	/**
	 * Builds the class name to fully represent a given class
	 * @param pckgName the package to look for the class ine
	 * @param fileName the name of the class file
	 * @return a String representing the fully-qualified class name
	 */
	private static String buildClassname(String pckgName, String fileName) {
		return pckgName + '.' + fileName.replace(".class", "");
	}

	/**
	 * Given a name of a component class in the engine, find the number of fields that it takes and their types.
	 * This is required for generating the appropriate amount of text boxes
	 * @param component the String name of the component in need of identification
	 */
	public static int getNumFields(String component) {
		// strip dashes which may have been there due to user-friendliness
		String fullName = COMPONENT_PREFIX + component.replace("-",  "");
		try {
			Class clazz = Class.forName(fullName);
			Constructor cons = clazz.getDeclaredConstructors()[0];
			// subtract one because the first parameter is ALWAYS the parent ID of the entity
			int prop = cons.getParameterCount() - 1;
			return cons.getParameterCount();
		} catch (ClassNotFoundException e) {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Class " + component + " does not exist.");
			LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
			a.showAndWait();
		}
		return 0;
	}



}
