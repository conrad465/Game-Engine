package data;

import gameplayer.view.Person;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import authoring.gamestate.Level;
import engine.components.Component;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import static data.DataUtils.*;

/*
    @Author Conrad defines methods for reading in various information from 
    the xml files reresenting gameStates
 */


public class DataRead  {
    private static final String EMPTY_GAME = "Empty";
    private static final String IMAGE_PATH = "data/images/";
    private static final String HIGHSCORE_PATH = "highscores.xml";
    private static final String EMPTY_IMAGE ="File:data/images/picture-placeholder.png";
    private static final String ENTITY_PATH = "data/entities/";
    private static final String PLAYER_TARGET = "Player.xml";
    private static final String ANIMATION_PATH = "data/animations";
    private static final String AIMAGE = "";

    public static DataGameState loadPlayerFile(File xml) {
        /* receives a gamestate and loads it to the player
         * from buildState
         */
        try {
            return buildState(xml);
        } catch (IllegalStateException e) {
            ErrorStatement(FAIL_MESSAGE);
            return new DataGameState(EMPTY_GAME);
        }
    }

    public static DataGameState copyGame() {
        /* receives a copy of a  gamestate and loads it to the player
         * from buildState
         */
        try {
            return buildState(loadFile(GAME_PATH+gameName+FRONTSLASH+PLAYER_TARGET));
        } catch (IllegalStateException e) {
            ErrorStatement(FAIL_MESSAGE);
            return new DataGameState(EMPTY_GAME);
        }
    }

    public static Map<Level, Map<Integer, List<Component>>> loadAuthorFile(File xml) {
        /*return a map for the gamestate to be sent to authoring that can be built into their
         *version of game state
         */
        try {
            DataGameState tempState = loadPlayerFile(xml);
            return tempState.getGameStateAuthoring();
        } 
        catch (IllegalStateException e) {
            ErrorStatement(FAIL_MESSAGE + e.getMessage());
            return new HashMap<>();
        }
    }

    public static Image loadImage(String name)throws RuntimeException {
        /*used to load all iamges in player
         */
        File imageFile = loadFile(GAME_PATH+gameName +FRONTSLASH+ IMAGE_PATH +name);
        return loadImage(imageFile);
    }

    public static Image addImage(File file) {
        /*called by authoring to add images t]o datapath when importing
         */
        DataWrite.writeImage(file);
        return loadImage(file);
    }

    public static Image loadImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            return SwingFXUtils.toFXImage(image, null);
        } catch (IOException e) {
            System.out.print(file.getAbsolutePath());
            ErrorStatement(FAIL_MESSAGE);
            return new Image(EMPTY_IMAGE);
        }
    }

    public static Map<String,List<Person>> loadHighscore(){
        /*loads the highscore
         */
        File hs = loadFile(HIGHSCORE_PATH);
        return ((Map<String,List<Person>>)deserialize(hs));

    }

    public static Map<Image, DataGameState> getAllGames() {
        /*loads all games so the user can select one they want
         */
        Map<Image, DataGameState> games =new HashMap<>();
        File file = loadFile(GAME_PATH);
        for(File game : file.listFiles()){
          DataGameState playable = loadPlayerFile(findInDirectory(game,PLAYER_TARGET));
          Image icon = null;
          try {
          icon = getIcons().get(0);
          }catch(Exception e) {e.printStackTrace();}
          games.put(icon, playable);
        }
        return games;
    }

    public static List<File> getAllEntities(){
        /* loads all entities saved in the gameauthpring
         */
        File entityDit = loadFile(ENTITY_PATH);
        return Arrays.asList(entityDit.listFiles());
    }

    public static List<DataGameState> getSaves(){
        //loads all saves
        List<DataGameState> saves = new ArrayList<DataGameState>();
        File file = loadFile(gameName + FRONTSLASH + SAVE_PATH);
        for(File save : file.listFiles()){
            saves.add((DataGameState)deserialize(save));
        }
        return saves;
    }

    public static List<Image> getIcons(){
        List<Image> icons = new ArrayList<>();
        File imageRepo = loadFile(GAME_PATH +gameName+FRONTSLASH+IMAGE_PATH);
        for(File image : imageRepo.listFiles()) {
            icons.add(loadImage(image));
        }
        return icons;
    }

    private static DataGameState buildState(File xml) {
        /*loads a game file and return it to the public method if
         * the game file doesnt exist in the right form it returns an empty state
         */
        try {
            DataGameState gameState = (DataGameState)deserialize(xml);
            gameName = gameState.getGameName();
            return gameState;
        }
        catch(Exception e){
            ErrorStatement(FAIL_MESSAGE);
            return new DataGameState(EMPTY_GAME);
        }
    }

    private static Object deserialize(File xml) {
        XStream xstream = new XStream(new DomDriver());
        return xstream.fromXML(xml);
    }

//    private static  Animaate getAnimation(String name){
//        File animateDir = loadFile(ANIMATION_PATH);
//        File animateFolder = findInDirectory(animateDir,name);
//
//    }




}
