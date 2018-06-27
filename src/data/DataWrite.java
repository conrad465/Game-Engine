package data;

import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;

import gameplayer.view.Person;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import authoring.entities.Entity;
import authoring.gamestate.GameState;
import engine.components.Component;
import engine.components.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

import static data.DataUtils.*;

public class DataWrite {

    /*  Author @ Conrad methods for saving data from either the authoring environment
     *  or the player
     */
    private static final String XML_FILETYPE = ".xml";
    private static final String IMAGE_DATAPATH = "images/";
    private static final String DATA_DATAPTH = "data";
    private static final String PERIOD = ".";
    private static final String PLAYER = "/Player";
    private static final String IMAGE_GETTER = "getImageFile";
    private static final String DEFAULT_IMAGEPATH = DATA_DATAPTH + FRONTSLASH + IMAGE_DATAPATH;
    private static final Set<Object> DATA_COMPONENTS = new HashSet<>(Collections.singletonList(Sprite.class));
    private static final Set<String> ACCEPTED_IMAGE_FILES = new HashSet<>(Arrays.asList("jpg", "png", "gif"));
    private static final String SAVE_PATH = "saves/";
    private static final String ENTITY_PATH = "entity/";
    private static final String HIGHSCORE_FILE = "src/highscores.xml";

    
    public static void saveFile(GameState gameState, String fileName) throws Exception {
        //creates an xml file from an authoiring environment this method converts authoring gamestate to player
        // gamestate then writes to xml
        DataGameState dataGameState = new DataGameState(gameState, fileName);
        createFile(dataGameState);
    }

    public static void saveFile(DataGameState dataGameState) throws Exception {
        //creates an xml file from an authoiring environment
        createFile(dataGameState);
    }

    public static void saveGame(DataGameState dataGameState, String saveName) {
        String name = dataGameState.getGameName();
        File game = new File(GAME_PATH + name + FRONTSLASH + SAVE_PATH + saveName + XML_FILETYPE);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(game);
        } catch (FileNotFoundException e) {
            ErrorStatement(WRITE_ERROR);
        }
        serialize(dataGameState, fos);
    }

    //Horrible method refactor
    public static void saveHighscore(Person person){
        /* writes an individual person to a highscore table based on the game being played
         */
        File hs;
        Map<String, List<Person>> people;
        try {
            people = DataRead.loadHighscore();
            people.get(gameName).add(person);
            hs = loadFile( HIGHSCORE_FILE);
        } catch (Exception e) {
            people = new HashMap<>();
            if(!people.containsKey(gameName)){
                List<Person> thepeeps = new ArrayList<>();
                thepeeps.add(person);
                people.put(gameName,thepeeps);
            }
            else
                people.get(gameName).add(person);
            hs = new File(HIGHSCORE_FILE);
        }
        try {
            FileOutputStream fos = new FileOutputStream(hs);
            serialize(people, fos);
        } catch (FileNotFoundException e) {
            ErrorStatement(WRITE_ERROR);
        }
    }

    public static void writeImage(File file) {
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            //Image noWhite = ImageUtil.convert();
            File fileDest = new File(DEFAULT_IMAGEPATH + file.getName());
            ImageIO.write(image, getFileType(file), fileDest);
        } catch (IOException e) {
            throw new AuthoringException("couldnt load", AuthoringAlert.NO_SHOW);
        }

    }

    public static void writeImage(URL imageURL, String name) throws IOException {
        BufferedImage image = ImageIO.read(imageURL);
        File fileDest = new File(DEFAULT_IMAGEPATH + name);
        if (!ACCEPTED_IMAGE_FILES.contains(getFileType(fileDest).toLowerCase())) {
            throw new IOException();
        }
        ImageIO.write(image, getFileType(fileDest), fileDest);
    }

    public static void saveEntity(Entity entity) {
        File entityFolder = new File(ENTITY_PATH);
        if (!entityFolder.exists()) {
            entityFolder.mkdir();
        }
        try {

            FileOutputStream fos = new FileOutputStream(ENTITY_PATH + entity.getName());
            serialize(entity, fos);
        } catch (FileNotFoundException e) {
            System.out.print("Cannot load");
        }
    }

    public static void webImport() {
        ResourceGetter imageGetter = new ResourceGetter();
        imageGetter.selectImage();
    }

    private static void createFile(DataGameState dataGameState) {
        // does the backend work to create new files in the game directory
        makeFolders(dataGameState.getGameName());
        writeGame(dataGameState);
        writeResources(dataGameState);
    }

    private static String writeImage(String gameName, String imageName) throws IOException {
        // Utility for adding imaged into a specified directory instead of a random directory in the user's computer
        File imageFile = new File(DEFAULT_IMAGEPATH + imageName);
        try {
            BufferedImage image = ImageIO.read(imageFile);
            String filePath = GAME_PATH + gameName + FRONTSLASH + DEFAULT_IMAGEPATH + imageName;
            File fileDest = new File(filePath);
            ImageIO.write(image, getFileType(imageFile), fileDest);
            return filePath;
        } catch (Exception e) {
            throw new IOException();
        }

    }

    private static void makeFolders(String name) {
        //makes a folder system or cleans one out for data to be written
        String gameDir = GAME_PATH + name;
        String saveDir = gameDir + FRONTSLASH + SAVE_PATH;
        String dataDir = gameDir + FRONTSLASH + DATA_DATAPTH;
        String imageDir = dataDir + FRONTSLASH + IMAGE_DATAPATH;

        File gameFolder = new File(gameDir);
        if (!gameFolder.exists()) {
            System.out.println("made a directory ");
            File dataFolder = new File(dataDir);
            gameFolder.mkdir();

            File saveFolder = new File(saveDir);
            saveFolder.mkdir();
            dataFolder.mkdir();

            File imageFolder = new File(imageDir);
            imageFolder.mkdir();
        }
        else {
            deleteDir(gameFolder);
            makeFolders(name);
        }
    }

    private static void writeGame(DataGameState dataGameState) {
        //writes the xml to the folder created above
        String name = dataGameState.getGameName();
       // System.out.println(dataGameState.getComponents().size()+"sdkfjhsdkf;jhsdkjfskjdf");
        File game = new File(GAME_PATH + name + PLAYER + XML_FILETYPE);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(game);
        } catch (FileNotFoundException e) {
            ErrorStatement(WRITE_ERROR);
        }
        serialize(dataGameState, fos);
    }

    private static void writeResources(DataGameState dataGameState) {
        /* pulls all images out of data and puts them in the correct data directory */
        String name = dataGameState.getGameName();
        List<Component> componentList = dataGameState.getComponents();
        for (Component component : componentList) {
            if (DATA_COMPONENTS.contains(component.getClass())) {
                for (Method method : component.getClass().getMethods())
                    if (method.getName().equals(IMAGE_GETTER))
                        try {
                            writeImage(name, (String) method.invoke(component, null));
                        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
                            ErrorStatement(WRITE_ERROR);
                        }
            }
        }
    }

    private static String getFileType(File file) {
        // util file for finding filetype
        int fIndex = file.getName().indexOf(PERIOD);
        return (fIndex == -1) ? "" : file.getName().substring(fIndex + 1);
    }

    private static void serialize(Object o, FileOutputStream fos) {
        XStream xstream = new XStream(new DomDriver());
        xstream.toXML(o, fos);
    }

    //destroy a directory TO BE KEPT PRIVATE!!!!!!!!!
    //DO NOT FUCK WITH THIS METHOD IT WILL FUCK YOUR SHIT UP
    //SERIOUSLY IT WILL DELETE YOUR COMPUTER...
    private static void deleteDir(File dir) {
        if (!dir.exists())
            return;
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File file : children) {
                deleteDir(file);
            }
        }
        dir.delete();
    }

}
