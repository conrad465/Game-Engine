package engine.test;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.entities.Roller;
import authoring.entities.User;
import authoring.gamestate.Level;
import data.DataGameState;
import data.DataWrite;
import engine.actions.Actions;
import engine.components.AI;
import engine.components.Animated;
import engine.components.Collidable;
import engine.components.Component;
import engine.components.DamageLifetime;
import engine.components.DamageValue;
import engine.components.EntityType;
import engine.components.Health;
import engine.components.Height;
import engine.components.KeyInput;
import engine.components.Lives;
import engine.components.Player;
import engine.components.Sprite;
import engine.components.Width;
import engine.components.Win;
import engine.components.XAcceleration;
import engine.components.XPosition;
import engine.components.XVelocity;
import engine.components.YAcceleration;
import engine.components.YPosition;
import engine.components.YVelocity;
import engine.systems.collisions.CollisionDirection;

import engine.components.presets.PlayerMovement;

import engine.setup.GameInitializer;
import engine.systems.InputHandler;
import javafx.scene.input.KeyCode;

public class TestGameState {

	public TestGameState() throws FileNotFoundException {
		Map<Integer,Map<String,Component>>entities = new HashMap<>();
		
		Map<String,Component> play = new HashMap<>();
		User u = new User(1, "jack");
		
		play.put(XPosition.KEY, new XPosition(1,0));
		play.put(YPosition.KEY, new YPosition(1,100));
		play.put(XVelocity.KEY, new XVelocity(1,0));
		play.put(YVelocity.KEY, new YVelocity(1,0));
		play.put(XAcceleration.KEY, new XAcceleration(1,0));
		play.put(YAcceleration.KEY, new YAcceleration(1,0));
		play.put(Sprite.KEY, new Sprite(1,"sonicsprite.png"));
		u.getComponentList().forEach(comp -> {
			play.put(comp.getKey(), comp);
		});
		play.put(Animated.KEY, new Animated(1,"sonicanimation.properties"));
		entities.put(1, play);
		
		for(int k = 2; k < 100; k++) {
			XPosition xp = new XPosition(k,(k-1)*50);
			YPosition yp = new YPosition(k,0);
			EntityType et = new EntityType(k,"Block");
			Height h = new Height(k,50);
			Width w = new Width(k,50);
			Sprite s = new Sprite(k, "8Bit.png");
			Collidable cdb = new Collidable(k);
			
			Map<String,Component> ent = new HashMap<>();
			ent.put(XPosition.KEY,xp);
			ent.put(YPosition.KEY,yp);
			ent.put(EntityType.KEY,et);
			ent.put(Height.KEY,h);
			ent.put(Width.KEY,w);
			ent.put(Sprite.KEY,s);
			ent.put(Collidable.KEY, cdb);
			entities.put(k, ent);
		}
		
		for(int k = 101; k < 200; k++) {
			XPosition xp = new XPosition(k,(k-101)*50);
			YPosition yp = new YPosition(k,200);
			EntityType et = new EntityType(k,"Block");
			Height h = new Height(k,50);
			Width w = new Width(k,50);
			Sprite s = new Sprite(k, "8Bit.png");
			Collidable cdb = new Collidable(k);

			Map<String,Component> ent = new HashMap<>();
			ent.put(XPosition.KEY,xp);
			ent.put(YPosition.KEY,yp);
			ent.put(EntityType.KEY,et);
			ent.put(Height.KEY,h);
			ent.put(Width.KEY,w);
			ent.put(Sprite.KEY,s);
			ent.put(Collidable.KEY, cdb);

			entities.put(k, ent);
		}



		Map<Level, Map<Integer,Map<String,Component>>> state = new HashMap<>();
		Level l = new Level(1);
		state.put(l,entities);

		DataGameState dState = new DataGameState(state, "JackDemo");
		try {
			DataWrite.saveFile(dState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
