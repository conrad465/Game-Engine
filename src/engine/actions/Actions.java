package engine.actions;

import authoring.entities.Entity;
import authoring.gamestate.GameState;
import data.DataGameState;
import engine.components.*;
import engine.components.presets.FireballCollision;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import engine.setup.SystemManager;
import engine.systems.collisions.CollisionDirection;

/**
 * This is the actions class which contains methods that represent any in-game actions done by an entity outside of
 * what is strictly defined by its component's values i.e. these are methods that act upon the components themselves,
 * given by the user. Each action is a consumer which accepts the entity's components such that it can act on them
 * as such, and in some actions that represent collisions, accept both the given entity's and the one it collides with's
 * components.
 *
 * @author cndracos
 * @author Yameng Liu
 */
public class Actions {
    private static SystemManager sm = null;

    public static void setSM(SystemManager sman) {
    	sm = sman;
    }

    public static Consumer<Map<String,Component>> remove() {
    	return e1 -> {
    		if(e1.containsKey(Sprite.KEY)) {
    			Sprite s = (Sprite) e1.get(Sprite.KEY);
    			s.getImage().toBack();
    			sm.removeEntity(s.getPID(), e1);
    		}
    	};
    }
    
    public static Consumer<Map<String,Component>> fireball() {
    	return e1 -> {
    		if(e1.containsKey(XPosition.KEY) && e1.containsKey(YPosition.KEY) && e1.containsKey(XVelocity.KEY) && e1.containsKey(Width.KEY)) {
    			XPosition x = (XPosition) e1.get(XPosition.KEY);
    			YPosition y = (YPosition) e1.get(YPosition.KEY);
    			XVelocity v = (XVelocity) e1.get(XVelocity.KEY);
    			Width w = (Width) e1.get(Width.KEY);
    			Map<String,Component> ne = new HashMap<>();
    			int id = (int) System.currentTimeMillis();
    			Sprite s = null;
    			try {
					s = new Sprite(id, "fireball.png");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
    			double vel = 0;
    			double xf = 0;
    			if(v.getData() > 0) {
    				s.getImage().setScaleX(-1);
    				vel = Math.max(150,3*v.getData());
    				xf = x.getData() + w.getData() + 20;
    			} else {
    				vel = Math.min(-150, 3*v.getData());
    				xf = x.getData() - 60;
    			}
    			s.getImage().setFitHeight(20);
    			s.getImage().setFitWidth(40);
    			s.getImage().setX(xf);
    			s.getImage().setY(y.getData());
    			Component[] newList = {new XPosition(id, x.getData()),
    			new YPosition(id, y.getData()),
    			new XVelocity(id, vel),
    			new YVelocity(id, 0),
    			s,
    			new FireballCollision(id),
    			new DamageValue(id, 50),
    			new DamageLifetime(id, 1),
    			new EntityType(id, "Fire"),
    			new Height(id, 20),
    			new Width(id, 40),
    			new Type(id, "Fire"),
    			new Animated(id, "fireballanimation.properties")};
    			
    			for(Component c : newList) {
    				if(c != null) {
    					ne.put(c.getKey(), c);
    				}
    			}
    			
    			sm.addEntity(id, ne);
    			sm.setActives();
    		}
    	};
    }
    
    
    /**

     * @return left action
     */
	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> moveLeft (double speed) {
    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
    		if(actor != null && (actor instanceof Map<?,?>)) {
    			if(actor.containsKey(XVelocity.KEY)) {
    				XVelocity xv = (XVelocity) actor.get(XVelocity.KEY);
    				xv.setData(-speed);
    			}
    		}
    	};
    }
	
	
	
	
	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> moveRight (double speed) {
    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
    		if(actor != null && (actor instanceof Map<?,?>)) {
    			if(actor.containsKey(XVelocity.KEY)) {
    				XVelocity xv = (XVelocity) actor.get(XVelocity.KEY);
    				xv.setData(speed);
    			}
    		}
    	};
    }
	
	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> moveUp (double speed) {
    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
    		if(actor != null && (actor instanceof Map<?,?>)) {
    			if(actor.containsKey(YVelocity.KEY)) {
    				YVelocity yv = (YVelocity) actor.get(YVelocity.KEY);
    				yv.setData(-speed);
    			}
    		}
    	};
    }
	
	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> moveDown (double speed) {
    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
    		if(actor != null && (actor instanceof Map<?,?>)) {
    			if(actor.containsKey(YVelocity.KEY)) {
    				YVelocity yv = (YVelocity) actor.get(YVelocity.KEY);
    				yv.setData(speed);
    			}
    		}
    	};
    }
	
	
	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> accelerateLeft (double speed) {
    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
    		if(actor != null && (actor instanceof Map<?,?>)) {
    			if(actor.containsKey(XAcceleration.KEY)) {
    				XAcceleration xa = (XAcceleration) actor.get(XAcceleration.KEY);
    				xa.setData(-speed);
    			}
    		}
    	};
    }
	
	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> accelerateRight (double speed) {
    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
    		if(actor != null && (actor instanceof Map<?,?>)) {
    			if(actor.containsKey(XAcceleration.KEY)) {
    				XAcceleration xa = (XAcceleration) actor.get(XAcceleration.KEY);
    				xa.setData(speed);
    			}
    		}
    	};
    }

	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> accelerateUp (double speed) {
    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
    		if(actor != null && (actor instanceof Map<?,?>)) {
    			if(actor.containsKey(YAcceleration.KEY)) {
    				YAcceleration ya = (YAcceleration) actor.get(YAcceleration.KEY);
    				ya.setData(-speed);
    			}
    		}
    	};
    }
	
	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> accelerateDown (double speed) {
    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
    		if(actor != null && (actor instanceof Map<?,?>)) {
    			if(actor.containsKey(YAcceleration.KEY)) {
    				YAcceleration ya = (YAcceleration) actor.get(YAcceleration.KEY);
    				ya.setData(speed);
    			}
    		}
    	};
    }
	
	
	
	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> addScore (double score) {
    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
    		if(actor != null && (actor instanceof Map<?,?>)) {
    			if(actor.containsKey(Score.KEY)) {
    				Score s = (Score) actor.get(Score.KEY);
    				s.setData(s.getData() + score);
    			}
    		}
    	};
    }
	
	
	
	@SuppressWarnings("unchecked")
	public static BiConsumer<Map<String, Component>,Map<String, Component>> transferScore() {
		return (Serializable & BiConsumer<Map<String, Component>,Map<String, Component>>) (actor1, actor2) -> {
    		if(actor1 != null && (actor1 instanceof Map<?,?>) && actor2 != null && (actor2 instanceof Map<?,?>)) {
    			if(actor1.containsKey(Player.KEY) && actor1.containsKey(Score.KEY) && actor2.containsKey(Score.KEY)) {
    				Score s1 = (Score) actor1.get(Score.KEY);
    				Score s2 = (Score) actor2.get(Score.KEY);
    				
    				s1.setData(s1.getData() + s2.getData());
    				s2.setData(0);
    			}
    		}
    	};
	}
	
	
	@SuppressWarnings("unchecked")
	public static BiConsumer<Map<String, Component>,Map<String, Component>> reverseAcceleration() {
		return (Serializable & BiConsumer<Map<String, Component>,Map<String, Component>>) (actor1, actor2) -> {
			if(actor1 != null && (actor1 instanceof Map<?,?>) && actor2 != null && (actor2 instanceof Map<?,?>)) {
    			if(actor2.containsKey(YAcceleration.KEY) && actor2.containsKey(YPosition.KEY)) {
    				YAcceleration ya = (YAcceleration) actor2.get(YAcceleration.KEY);
    				YPosition yp = (YPosition) actor2.get(YPosition.KEY);
    				ya.setData(-ya.getData());
    			}
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public static BiConsumer<Map<String, Component>,Map<String, Component>> xFriction(double stickiness) {
		return (Serializable & BiConsumer<Map<String, Component>,Map<String, Component>>) (actor1, actor2) -> {
    		if(actor1 != null && (actor1 instanceof Map<?,?>)) {
    			if(actor1.containsKey(XVelocity.KEY)) {
    				XVelocity xv = (XVelocity) actor1.get(XVelocity.KEY);
    				XAcceleration xa = (XAcceleration) actor1.get(XAcceleration.KEY);
    				
    				if(xv.getData() > 0) {
    					xa.setData(-stickiness);
    				} else if(xv.getData() < 0) {
    					xa.setData(stickiness);
    				} else {
    					xa.setData(0);
    				}
    			}
    		}
    	};
	}


	@SuppressWarnings("unchecked")
	public static Consumer<Map<String, Component>> yGravity(double force){
		return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
			if (actor != null && actor.containsKey(YAcceleration.KEY)) {
				YAcceleration YAcc = (YAcceleration) actor.get(YAcceleration.KEY);
				YAcc.setData(force);
			}
		};
	}
    
	
    /**
	 *
	 * This is the main action which deals with components damaging one another. This method will be given
	 * to an entity, and defines ONLY HOW IT DAMAGES THE OTHER ENTITY, if that entity has health. In order for
	 * the original entity to be damaged by the other, the other must have this action as well, hence this
	 * represents a one-way transfer of damage.
	 *
     * @return Damage action
     */
    @SuppressWarnings("unchecked")
	public static BiConsumer<Map<String, Component>, Map<String, Component>> damage(){
        return (Serializable & BiConsumer<Map<String, Component>,Map<String, Component>>) (actor1, actor2) -> {
			if (actor1.containsKey(EntityType.KEY) && actor2.containsKey(EntityType.KEY)) {
				EntityType e1 = (EntityType) actor1.get(EntityType.KEY);
				EntityType e2 = (EntityType) actor1.get(EntityType.KEY);
				if (!e1.getData().equals(e2.getData())) { //cannot harm same entity type
					giveDamage(actor1, actor2);
				}
			}
        	else giveDamage(actor1, actor2);
//			System.out.println(((Health)actor2.get(Health.KEY)).getData());
//			System.out.println("11"+((DamageValue)actor1.get(DamageValue.KEY)).getData());
		};
    }
    
    private static void giveDamage(Map<String, Component> player, Map<String, Component> collider) {
		if (player.containsKey(DamageValue.KEY) &&
				player.containsKey(DamageLifetime.KEY) &&
				collider.containsKey(Health.KEY)) {

			int playerID = player.get(DamageValue.KEY).getPID();
			int colliderID = collider.get(Health.KEY).getPID();

			DamageValue dlv = (DamageValue) player.get(DamageValue.KEY);
			DamageLifetime dll = (DamageLifetime) player.get(DamageLifetime.KEY);

			Map<String, Component> newDamageComponents = new HashMap<>();
			newDamageComponents.put(DamageValue.KEY, new DamageValue(playerID, dlv.getData()));
			newDamageComponents.put(DamageLifetime.KEY, new DamageLifetime(playerID, dll.getData()));
			if(sm != null) {
				sm.addEntity(colliderID, newDamageComponents); //passes new damage to the HealthDamage system
			}
		}
	}

    /**
     * @return two new entity maps
     */
    
    // Behavior already supported in sprite class
    
    /*@SuppressWarnings("unchecked")
	public Consumer<Map<String, Component>> changeSprite(Sprite alternative){
        return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
            if(actor != null && actor.containsKey(Sprite.KEY)){
                actor.put(Sprite.KEY, alternative);
            }
        };
    }*/

    public static BiConsumer<Map <String, Component>, Map<String, Component>> bounce (CollisionDirection cd, double speed) {
		//System.out.println("bouncing");
    	switch (cd) {
			case Left:
				return horizontalBounce(speed);
			case Right:
				return horizontalBounce(-speed);
			case Top:
				return verticalBounce(speed);
			case Bot:
				return verticalBounce(-speed);
		}
		System.out.println("returned null");
		return null;
	}

	private static BiConsumer<Map<String, Component>, Map<String, Component>> verticalBounce(double v) {
		return (Serializable & BiConsumer<Map<String, Component>, Map<String, Component>>) (entity1, entity2) -> {
            YVelocity yv = (YVelocity) entity2.get(YVelocity.KEY);
            yv.setData(v);
            if (entity2.containsKey(Collidable.KEY)) {
                Collidable c = (Collidable) entity2.get(Collidable.KEY);
                c.suppress();
            }
        };
	}

	private static BiConsumer<Map<String, Component>, Map<String, Component>> horizontalBounce(double v) {
		return (Serializable & BiConsumer<Map<String, Component>, Map<String, Component>>) (entity1, entity2) -> {
			XVelocity xv = (XVelocity) entity2.get(XVelocity.KEY);
			xv.setData(v);
			if (entity2.containsKey(Collidable.KEY)) {
				Collidable c = (Collidable) entity2.get(Collidable.KEY);
				c.suppress();
			}
		};
	}


    /**
     * This would be an AI component that has an enemy follow you
     * @param fInt Player/entity being followed
     * @return action which result in the tracker moving towards the followed
     */

    @SuppressWarnings("unchecked")
	public static Consumer<Map <String, Component>> followsYou (int fInt, double speed) {
		Entity  followed = null;
		try {
			followed = GameState.entity(fInt);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		XPosition px = (XPosition) followed.get(XPosition.KEY);
        YPosition py = (YPosition) followed.get(YPosition.KEY);

        return (Serializable & Consumer<Map <String, Component>>) (tracker) -> {
			XPosition tx = (XPosition) tracker.get(XPosition.KEY);
			YPosition ty = (YPosition) tracker.get(YPosition.KEY);
			XVelocity vx = (XVelocity) tracker.get(XVelocity.KEY);
			YVelocity vy = (YVelocity) tracker.get(YVelocity.KEY);

            vx.setData((px.getData() - tx.getData())* speed);
            vy.setData((py.getData() - ty.getData()) * speed);
        };
    }

    public static Consumer<Map <String, Component>> teleport(List<Point> toward){
		return (Serializable & Consumer<Map<String, Component>>) (actor) -> {

			int x = toward.get(1).x;
			int y = toward.get(1).y;
			if(actor != null && (actor instanceof Map<?,?>)) {
				if((actor.containsKey(XPosition.KEY) && actor.containsKey(YPosition.KEY))) {
					((XPosition)actor.get(XPosition.KEY)).setData(x);
					((YPosition)actor.get(YPosition.KEY)).setData(y);
				}
			}
			if(toward.size()>2)
				toward.remove(toward.size()-1);
		};
	}

    /**
     * A patrol action to be given to an AI component for enemies, blocks, etc. Goes to the given coordinates in
     * order then repeats.
     *
     * @param coordinates The positions the entity will visit
     * @return the actions which performs this method
     */

    @SuppressWarnings("unchecked")
	public static Consumer<Map <String, Component>> patrol(List<Point> coordinates, double speed) {
        AtomicReference<Point> destination = new AtomicReference<>(coordinates.get(0));
        AtomicInteger current = new AtomicInteger();

        return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
			XVelocity xv = (XVelocity) actor.get(XVelocity.KEY);
			YVelocity yv = (YVelocity) actor.get(YVelocity.KEY);
			XPosition xp = (XPosition) actor.get(XPosition.KEY);
			YPosition yp = (YPosition) actor.get(YPosition.KEY);

            xv.setData((destination.get().getX()-xp.getData())/
					(distance(xp.getData(), yp.getData(), destination.get().getX(), destination.get().getY())) * speed);
            yv.setData((destination.get().getY()-yp.getData())/
					(distance(xp.getData(), yp.getData(), destination.get().getX(), destination.get().getY())) * speed);
			setNewDestination(coordinates, destination, current, xp, yp);
		};
    }

	/**
	 * The patrol method that uses the user's position as a starting point, so any list of points can define
	 * an entity-specific patrol route.
	 *
	 * @param coordinates Destination points
	 * @param speed which you move at
	 * @return action of patrolling
	 */
	private static Consumer<Map <String, Component>> patrolRelative(List<Point> coordinates, double speed) {
    	AtomicReference<Point> destination = new AtomicReference<>(coordinates.get(0));
    	AtomicInteger current = new AtomicInteger();
		final boolean[] shifted = {false};

    	return (Serializable & Consumer<Map<String, Component>>) (actor) -> {
			XVelocity xv = (XVelocity) actor.get(XVelocity.KEY);
			YVelocity yv = (YVelocity) actor.get(YVelocity.KEY);
			XPosition xp = (XPosition) actor.get(XPosition.KEY);
			YPosition yp = (YPosition) actor.get(YPosition.KEY);

			if (!shifted[0]) {
				double xDiff =  destination.get().getX() - xp.getData();
				double yDiff = destination.get().getY() - yp.getData();
				for (Point p : coordinates) {
					p.setLocation(p.getX() - xDiff, p.getY() - yDiff);
				}
				shifted[0] = true;
			}

			xv.setData((destination.get().getX()-xp.getData())/
					(distance(xp.getData(), yp.getData(), destination.get().getX(), destination.get().getY())) * speed);
			yv.setData((destination.get().getY()-yp.getData())/
					(distance(xp.getData(), yp.getData(), destination.get().getX(), destination.get().getY())) * speed);
			setNewDestination(coordinates, destination, current, xp, yp);
		};
	}

	private static void setNewDestination(List<Point> coordinates, AtomicReference<Point> destination, AtomicInteger current, XPosition xp, YPosition yp) {
		if ((distance(xp.getData(), yp.getData(), destination.get().getX(), destination.get().getY())) < 10) {
            if (current.get() == coordinates.size() - 1) current.set(0);
            else current.getAndIncrement();
            destination.set(coordinates.get(current.get()));
        }
	}


	private static double distance (double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y1-y2, 2) + Math.pow(x1 - x2, 2)); //distance between two positions/points
    }



}
