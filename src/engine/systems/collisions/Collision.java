package engine.systems.collisions;
/**
 * System that checks if the collision happens, and determines where it happens
 * in relation to dimensions
 * author jcf44, sv116
 */
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.components.Collidable;
import engine.components.Component;
import engine.components.EntityType;
import engine.components.Height;
import engine.components.Player;
import engine.components.Type;
import engine.components.Width;
import engine.components.XPosition;
import engine.components.XVelocity;
import engine.components.YPosition;
import engine.components.YVelocity;
import engine.systems.AbstractSystem;
import engine.systems.ISystem;


public class Collision extends AbstractSystem implements ISystem {
	private Map<Integer, Object> colliders;
	
	public Collision () {
		super();
		colliders = new ConcurrentHashMap<>();
	}

	public void execute(double time) {
		colliders.keySet().forEach(key1 -> {
			this.getHandled().forEach((key2, map2) -> {
				if(key1 != key2) {
					Map<String,Component> map1 = this.getHandled().get(key1);
					checkCollision(map1, map2);
				}
			});
		});
	}

	private void checkCollision(Map<String, Component> map1, Map<String, Component> map2) {
		if(map1 == null || map2 == null) return;
		if(map2.containsKey(EntityType.KEY) && ((EntityType) map2.get(EntityType.KEY)).getData().equals("Fire")) return;
		double w1,w2,h1,h2 = 0;
		
		if(!map1.containsKey(Width.KEY)) {
			w1 = 50;
			h1 = 50;
		} else {
			w1 = ((Width) map1.get(Width.KEY)).getData();
			h1 = ((Height) map1.get(Height.KEY)).getData();
		}
		if(!map2.containsKey(Width.KEY)) {
			w2 = 50;
			h2 = 50;
		} else {
			w2 = ((Width) map2.get(Width.KEY)).getData();
			h2 = ((Height) map2.get(Height.KEY)).getData();
		}
		
		XPosition x1 = (XPosition) map1.get(XPosition.KEY);
		YPosition y1 = (YPosition) map1.get(YPosition.KEY);
		XPosition x2 = (XPosition) map2.get(XPosition.KEY);
		YPosition y2 = (YPosition) map2.get(YPosition.KEY);

		

		CollisionDirection cd = this.getOverlaps(x1.getData(), y1.getData(), w1, h1,
												x2.getData(), y2.getData(), w2, h2);
		
		if (cd != null) {

			this.evaluateCollidable(cd, map1, map2);
			
			switch (cd) {

			case Top:
				y1.setData(y2.getData() - h1);
				break;
				
			case Bot:
				y1.setData(y2.getData() + h2);
				break;
				
			case Left:
				x1.setData(x2.getData() - w1);
				break;
				
			case Right:
				x1.setData(x2.getData() + w2);
				break;

			}
		}
	}


	private void evaluateCollidable(CollisionDirection cd, Map<String, Component> map1, Map<String, Component> map2) {
		if(this.checkComponents(map1, Collidable.KEY)) {
			Collidable cdb = (Collidable) map1.get(Collidable.KEY);
			cdb.action(cd, map1, map2);
		}
		
		if(this.checkComponents(map2, Collidable.KEY)) {
			Collidable cdb = (Collidable) map2.get(Collidable.KEY);
			CollisionDirection cd2 = null;
			if(cd == CollisionDirection.Top) {
				cd2 = CollisionDirection.Bot;
			}
			else if(cd == CollisionDirection.Bot) {
				cd2 = CollisionDirection.Top;
			}
			else if(cd == CollisionDirection.Left) {
				cd2 = CollisionDirection.Right;
			}
			else {
				cd2 = CollisionDirection.Left;
			}
			cdb.action(cd2, map2, map1);
		}
		
	}


	private CollisionDirection getOverlaps(double x1, double y1, double w1, double h1,
										double x2, double y2, double w2, double h2) {
		
		double topOverlap = y1 + h1 - y2;
		double botOverlap = y2 + h2 - y1;
		double leftOverlap = x1 + w1 - x2;
		double rightOverlap = x2 + w2 - x1;

		boolean to = topOverlap >= 0 && topOverlap <= h1;
		boolean bo = botOverlap >= 0 && botOverlap <= h2;
		boolean lo = leftOverlap >= 0 && leftOverlap <= w1;
		boolean ro = rightOverlap >= 0 && rightOverlap <= w2;
		
		List<Double> overlaps = new ArrayList<>();
		
		if (bo && (lo || ro)) {
			overlaps.add(botOverlap);
		}
		if (to && (lo || ro)) {
			overlaps.add(topOverlap);
		}
		if (lo && (to || bo)) {
			overlaps.add(leftOverlap);
		}
		if (ro && (to || bo)) {
			overlaps.add(rightOverlap);
		}
		
		Collections.sort(overlaps);

		if (overlaps.size() > 0) {
			if (overlaps.get(0) == topOverlap) {
				return CollisionDirection.Top;
			}
			else if (overlaps.get(0) == botOverlap) {
				return CollisionDirection.Bot;
			}
			else if (overlaps.get(0) == rightOverlap) {
				return CollisionDirection.Right;
			}
			else if (overlaps.get(0) == leftOverlap) {
				return CollisionDirection.Left;
			}
		}
		
		return null;
	}


	public void removeComponent(int pid) {
		Map<Integer, Map<String,Component>> handled = this.getHandled();
		if(handled.containsKey(pid)) {
			handled.remove(pid);
		}
		if(colliders.keySet().contains(pid)) {
			colliders.remove(pid);
		}
	}

	public void addComponent(int pid, Map<String, Component> components) {
		if(this.checkComponents(components, XPosition.KEY, YPosition.KEY, Width.KEY, Height.KEY, Collidable.KEY)) {
			this.directAddComponent(pid, components);
			if(this.checkComponents(components, XVelocity.KEY, YVelocity.KEY)) {
				colliders.put(pid,components);
			}
		}
	}
}
