package engine.components.presets;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.components.Component;
import engine.components.Player;
import engine.systems.collisions.CollisionDirection;

public class FireballCollision extends Collidable {

	
	
	@SuppressWarnings("unchecked")
	public FireballCollision(int pid) {
		super(pid);
		
		this.setOnDirection(CollisionDirection.Top,  (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>)(e1,e2) -> {
			if(!e1.containsKey(Player.KEY) && !e2.containsKey(Player.KEY)) {
				Actions.damage().accept(e1, e2);
				Actions.remove().accept(e1);
			}
			
		});
		
		this.setOnDirection(CollisionDirection.Bot,  (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>)(e1,e2) -> {
			
			if(!e1.containsKey(Player.KEY) && !e2.containsKey(Player.KEY)) {
				Actions.damage().accept(e1, e2);
				Actions.remove().accept(e1);
			}
		});

		this.setOnDirection(CollisionDirection.Left,  (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>)(e1,e2) -> {
			if(!e1.containsKey(Player.KEY) && !e2.containsKey(Player.KEY)) {
				Actions.damage().accept(e1, e2);
				Actions.remove().accept(e1);
			}
			
		});
		this.setOnDirection(CollisionDirection.Right,  (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>)(e1,e2) -> {
			if(!e1.containsKey(Player.KEY) && !e2.containsKey(Player.KEY)) {
				Actions.damage().accept(e1, e2);
				Actions.remove().accept(e1);
			}
			
		});
		
	}

}
