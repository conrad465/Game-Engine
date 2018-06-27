package engine.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.systems.ISystem;
import gameplayer.view.EntityManager;
import engine.components.Component;
import engine.exceptions.EngineException;

/**
 * This is the class which delegates actions and components to the systems, allowing an efficient distribution of
 * tasks through this manager such that other classes do not need to hold instances of the systems. Makes adding
 * components (and entities) a smooth process along with running the execute methods.
 *
 * @author cndracos
 * @author Yameng Liu
 */
public class SystemManager {

    private List<ISystem> systems;
    private RenderManager renderManager;
    private EntityManager ec;
    
    public SystemManager (RenderManager renderManager, EntityManager ec) {
        this.renderManager = renderManager;
        this.ec = ec;
    }

    public void addSystems(List<ISystem> systems) {
    		this.systems = systems;
    }

    /**
     * Method run when setting up the system. Passes the entity and all its components so that the
     * systems can pick from the option the relevant components, if any exist
     * @param pid the entity's ID
     * @param components its components
     */
    public void addEntity(int pid, Map<String, Component> components) {
        for (ISystem s : systems) {
            s.addComponent(pid, components);
        }
        ec.addEntity(pid, components);
    }

    public void removeEntity (int pid, Map<String,Component> components) {
        for (ISystem s : systems) {
            s.removeComponent(pid);
        }
        setActives(renderManager.render());
        ec.removeEntity(pid, components);
    }


    public void addComponent(int pid, Component c) {
        Map<String, Component> newComponent = new HashMap<>();
        newComponent.put(c.getKey(), c);
        for(ISystem s : systems) {
			s.addComponent(pid, newComponent);
		}
    }

   /* public void removeComponent(int pid) {
        removeEntity(pid);
    }*/

    /**
     * Sets the active components for the systems to act upon based on the rendering
     * @param actives set of active components within rendering distance
     */
    public void setActives (Set<Integer> actives) {
        for (ISystem s : systems) {
            s.setActives(actives);
        }
    }

    public void execute (double time) throws EngineException {
        for (ISystem s: systems) {
            s.execute(time);
        }
    }

	public void setActives() {
		for(ISystem s : systems) {
			s.setActives();
		}
	}

}
