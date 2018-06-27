package authoring.gamestate;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;

/**
 * Class to load in the state of an authoring environment (presets plus states)
 * @author Dylan Powers
 *
 */
public class AuthoringStateLoader {

	private DocumentBuilder documentBuilder;
	private final String ENTITY_FOLDER = "data/entities/";
	private final String ATTRIBUTE_NAME = "game";
	private static final String GAME_FOLDER = "games/";
	private static final String ERROR_MESSAGE = "The game saved under that name does not contain a playable file.";
	
	/**
	 * Create this loader with a given game name to load. 
	 * @param gameName the name of the game to load
	 */
	public AuthoringStateLoader() {
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new AuthoringException(e, AuthoringAlert.NO_SHOW);
		}
	}
	
	
	/**
	 * Finds all of the files that pertain to a specific game.
	 * @param gameName the name of the game to search for
	 * @param filesToSearch the files to search through for the given game type
	 * @return a list of files that pertain to a given game
	 */
	public List<File> findFilesForGame(String gameName) {
		List<File> gameEntities = new ArrayList<>();
		Document d;
		File[] filesToSearch = new File(ENTITY_FOLDER).listFiles();
		for (File poss : filesToSearch) {
			try {
				d = documentBuilder.parse(poss);
				Element e = d.getDocumentElement();
				if (e.getAttribute(ATTRIBUTE_NAME).equals(gameName)) {
					gameEntities.add(poss);
				}
			} catch (SAXException | IOException e) {
				throw new AuthoringException(e, AuthoringAlert.NO_SHOW);
			}
		}
		return gameEntities;
	}
	
	/**
	 * Finds the correct Player.xml file to load based upon the name of the game.
	 * @param gameName the name of the game to load
	 */
	public static File findStateFile(String gameName) {
		File gameDirectory = new File(GAME_FOLDER + gameName);
		File[] savedFiles = gameDirectory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("Player");
			}
		});
		if (savedFiles.length == 0) {
			throw new AuthoringException(ERROR_MESSAGE, AuthoringAlert.SHOW);
		} else {
			return savedFiles[0];
		}
	}
}
