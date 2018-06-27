package engine.systems;

import engine.components.AI;
import engine.components.Component;

import java.util.Map;

/**
 * System which calls the AI components' actions if they are within rendering dinstance
 *
 * @author cndracos, fitzj
 */
public class ArtificialIntelligence extends AbstractSystem implements  ISystem{

    public ArtificialIntelligence() {
    	super();
    }
    
    @Override
    public void addComponent(int pid, Map<String, Component> components) {
        if (this.checkComponents(components, AI.KEY)) {
        	this.directAddComponent(pid, components);
        }
    }

    @Override
    public void execute(double time) {
        for (int id : this.getActives()) {
            Map<String, Component> components = this.getHandled().get(id);
            AI ai = (AI) components.get(AI.KEY);
            ai.doAction(components);
        }
    }


}
