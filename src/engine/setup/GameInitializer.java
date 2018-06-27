package engine.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import engine.actions.Actions;
import engine.components.Component;
import engine.components.XPosition;
import engine.components.YPosition;
import engine.exceptions.EngineException;
import engine.systems.Accelerate;
import engine.systems.Animate;
import engine.systems.ArtificialIntelligence;
import engine.systems.ConditionChecker;
import engine.systems.HealthDamage;
import engine.systems.ISystem;
import engine.systems.InputHandler;
import engine.systems.Motion;
import engine.systems.collisions.Collision;
import gameplayer.view.EntityManager;

/**
 * This is the class which is created when the player first decides to run a game. It creates the managers and loads
 * the components into the appropriate systems, setting up the game to be run smoothly.
 *
 * @author cndracos
 */
public class GameInitializer {

    private List<ISystem> systems = new ArrayList<>();

    private SystemManager systemManager;
    private RenderManager renderManager;
    private InputHandler inputHandler;

    /**
     * Creates the managers and systems, then reads in the entities. Sets up the rendering system and input handler
     *
     * @param entities
     */
    public GameInitializer (Map <Integer, Map<String, Component>> entities, double renderDistance, double renderCenterX, double renderCenterY, EntityManager ec) {

        renderManager = new RenderManager(renderDistance, renderCenterX, renderCenterY);
        systemManager = new SystemManager(renderManager, ec);
        Actions.setSM(systemManager);
        inputHandler = new InputHandler();
        addSystems();

        systemManager.addSystems(systems);

        for (int id : entities.keySet()) {
            Map<String, Component> components = entities.get(id);
            if (components.containsKey(XPosition.KEY) && components.containsKey(YPosition.KEY)) {
                XPosition px = (XPosition) components.get(XPosition.KEY);
                YPosition py = (YPosition) components.get(YPosition.KEY);
                renderManager.add(new Position(px.getPID(), px.getData(), py.getData()));
                
            }
            systemManager.addEntity(id, components);
        }

        systemManager.setActives(renderManager.render(50, 50));
    }

    public void execute (double time) throws EngineException {
        systemManager.execute(time); //runs all functions of the systems
    }


    public InputHandler getInputHandler() {
         return inputHandler;
    }

    public RenderManager getRenderManager() { return renderManager; }

    public SystemManager getSystemManager() { return systemManager; }

    public List<ISystem> getSystems() {		// For testing
    		return systems;
    }
    
    private void addSystems() {
        systems.add(new Accelerate());
        systems.add(new Motion());
        systems.add(new ConditionChecker());
        systems.add((new ArtificialIntelligence()));
        systems.add(new Collision());
        systems.add(new HealthDamage(systemManager));
        systems.add(new Animate());
        systems.add(inputHandler);
    }

}
