package gameplayer.view;

import java.util.Map;

import engine.components.Component;

public interface EntityManager {
	
	public void addEntity(int pid, Map<String,Component> components);
	public void removeEntity(int pid, Map<String,Component> components);
	
}
