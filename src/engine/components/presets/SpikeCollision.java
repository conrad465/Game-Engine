package engine.components.presets;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.components.Component;
import engine.systems.collisions.CollisionDirection;

public class SpikeCollision extends Collidable{

	@SuppressWarnings("unchecked")
	public SpikeCollision(int pid) {
		super(pid);
		
		//this.setOnDirection(CollisionDirection.Top,  (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>)Actions.damage());
		this.setOnDirection(CollisionDirection.Bot, (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>) (e1,e2) -> {
			
			Actions.damage().accept(e1, e2);
			Actions.moveUp(200).accept(e2);
			System.out.println("Spiked");
			Actions.accelerateRight(0).accept(e2);
		});

		//this.setOnDirection(CollisionDirection.Left,  (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>) Actions.damage());
		//this.setOnDirection(CollisionDirection.Right, (Serializable & BiConsumer<Map<String,Component>, Map<String,Component>>) Actions.damage());

		
	}
	
}
