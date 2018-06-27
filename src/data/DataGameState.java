package data;

import engine.components.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.entities.Entity;
import authoring.gamestate.GameState;
import authoring.gamestate.Level;

public class DataGameState {
    /*@Author Conrad this class represents the complete information that is contained in a
     * game-- all levels which contain entities which contain all components
     * additionally, this class provides utility methods for converting between authoring and player
     */

    private Map<Level,Map<Integer, Map<String, Component>>> gameState;

    private String gameName = "data";
    private double levelProgress =1;

    public DataGameState(Map<Level,Map<Integer, Map<String, Component>>> gameState, String gName) {
        /*creates a DataGameState from a map sent in from engine or player
         */
        this(gName);
        this.gameState = gameState;
    }

    public DataGameState(GameState gameState, String gName) {
        /*constructor that converts an authoring environment state to a state playable
         * by player and engine
         */
        this(gName);
        for(Level level : gameState.getLevels()) {
            Map<Integer, Map<String, Component>> entityMap = new HashMap<>();
            for (Entity entity : level.getEntityList()) {
                Map<String, Component> componentMap = new HashMap<>();
                for (Component component :entity.getComponentList()) {
                    componentMap.put(component.getKey(), component);
                }
                entityMap.put(entity.getID(),componentMap);
            }
            this.gameState.put(level,entityMap);
        }
    }

    public DataGameState(String gName){
        /*creates an empty gamestate which holds the place of a null values
         * useful for errors when loading gamefiles
         */
        this.gameState = new HashMap<>();
        gameName = gName;
    }

    public Map<Level,Map<Integer, Map<String, Component>>> getGameState() {
        /*returns a map in a form that player and authoring engine can take
         */
        return gameState;
    }

    public  Map<Level, Map<Integer,List<Component>>> getGameStateAuthoring() {
        /*creates a map that represents the form of authoring's
         * game state-- useful for conversion between gamestates
         */
        Map<Level, Map<Integer,List<Component>>> authoringState = new HashMap<>();
        for(Level level : gameState.keySet()) {
            Map<Integer, List<Component>> entityMap = new HashMap<>();
            for (Integer integer : gameState.get(level).keySet()) {
                ArrayList<Component> components = new ArrayList<>(gameState.get(level).get(integer).values());
                entityMap.put(integer, components);
                authoringState.put(level,entityMap);
            }
        }
        return authoringState;
    }

	public String getGameName(){
        //gets name gets .... the name of the file
        return gameName;
    }

    public List<Component> getComponents() {
        //returns the list of all components in the gamestate for data writing
        List<Component> componentList = new ArrayList<>();
        for(Map<Integer, Map<String,Component>> level : gameState.values())
            for(Map<String,Component> entity : level.values())
               componentList.addAll(entity.values());
        return componentList;
    }

    public double getLevelProgress(){
        return levelProgress;
    }


}
