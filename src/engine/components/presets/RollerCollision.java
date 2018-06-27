package engine.components.presets;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import engine.components.Collidable;
import engine.components.Component;
import engine.components.Health;
import engine.components.Jumps;
import engine.components.YVelocity;
import engine.systems.collisions.CollisionDirection;

public class RollerCollision extends Collidable {

	@SuppressWarnings("unchecked")
	public RollerCollision(int pid) {
		super(pid);

		this.setOnDirection(CollisionDirection.Left, (Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>)(e1,e2) -> {
			if(e1.containsKey(Health.KEY)) {
				((Health) e1.get(Health.KEY)).setData(0);
			}
		});
	

		this.setOnDirection(CollisionDirection.Top,(Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>) (e1,e2) -> {
			if(e1.containsKey(Jumps.KEY)) {
				((Jumps) e1.get(Jumps.KEY)).reset();
			}
			if(e1.containsKey(YVelocity.KEY)) {
				((YVelocity) e1.get(YVelocity.KEY)).setData(0);
			}
		});
		
		this.setOnDirection(CollisionDirection.Bot,(Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>) (e1,e2) -> {
			if(e1.containsKey(Jumps.KEY)) {
				((Jumps) e1.get(Jumps.KEY)).reset();
			}
			if(e1.containsKey(YVelocity.KEY)) {
				((YVelocity) e1.get(YVelocity.KEY)).setData(0);
			}
		});
		
		this.setOnDirection(CollisionDirection.Right,(Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>) (e1,e2) -> {
		});
		
	}

}
