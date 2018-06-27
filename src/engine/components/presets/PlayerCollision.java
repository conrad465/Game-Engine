package engine.components.presets;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.components.Component;
import engine.components.Jumps;
import engine.systems.collisions.CollisionDirection;

/**
 * Presets made for final demo/authoring.
 * Contains more advanced game logic that can be expanded on, but is not included in systems.
 * In this way, more "advanced" game makers can add their own code.
 * @author fitzj
 *
 */
public class PlayerCollision extends Collidable {

	private static final int FRICTION = 20;
	
	@SuppressWarnings("unchecked")
	public PlayerCollision(int pid) {
		super(pid);
		
		this.setOnDirection(CollisionDirection.Top, (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>) (e1, e2) -> {
			
			Actions.damage().accept(e1, e2);
			Actions.moveUp(0).accept(e1);
			Actions.transferScore().accept(e1, e2);
			//Actions.xFriction(FRICTION).accept(e1, e2);
			if(e1.containsKey(Jumps.KEY)) {
				Jumps s = (Jumps) e1.get(Jumps.KEY);
				s.reset();
			}
			
		});
		
		this.setOnDirection(CollisionDirection.Bot, (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>) (e1, e2) -> {
			Actions.moveDown(50).accept(e1);
		});
		
		this.setOnDirection(CollisionDirection.Left, (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>) (e1, e2) -> {
			Actions.moveLeft(0).accept(e1);
			
		});
		
		this.setOnDirection(CollisionDirection.Right, (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>) (e1, e2) -> {
			Actions.moveRight(0).accept(e1);
		});
		
	}

}
