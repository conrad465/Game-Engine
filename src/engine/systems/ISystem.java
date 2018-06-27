package engine.systems;

import engine.components.Component;
import engine.exceptions.EngineException;

import java.util.Map;
import java.util.Set;
/*
 * interface used by all systems
 * author sv116
 * @author Yameng Liu
 */
public interface ISystem {
	void addComponent (int pid, Map<String, Component> components);
    void removeComponent (int pid);
    void setActives(Set<Integer> actives);
    void execute(double time) throws EngineException;
	void setActives();
}
