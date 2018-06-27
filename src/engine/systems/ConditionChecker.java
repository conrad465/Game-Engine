package engine.systems;

import engine.components.Component;
import engine.components.Conditional;

import java.util.Map;

/**
 * System which checks conditionals.
 *
 *  ### THIS CLASS HAS BEEN REPLACED WITH COLLISIONS AND COLLIDABLE COMPONENTS##
 *
 *  @author cndracos
 *
 */
public class ConditionChecker extends AbstractSystem implements ISystem {

    public ConditionChecker() {
    	super();
    }

    @Override
    public void addComponent(int pid, Map<String, Component> components) {
        if (this.checkComponents(components, Conditional.KEY)) {
            this.directAddComponent(pid, components);
        }
    }

    @Override
    public void execute(double time) {
        for (int id : this.getActives()) {
            Map<String, Component> components = this.getHandled().get(id);
            Conditional c = (Conditional) components.get(Conditional.KEY);
            c.evaluate();
        }
    }


}
