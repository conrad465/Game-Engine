package engine.components.presets;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

import engine.actions.Actions;
import engine.components.Component;
import engine.components.Height;
import engine.components.Jumps;
import engine.components.KeyInput;
import engine.components.Sprite;
import engine.components.YPosition;
import javafx.scene.input.KeyCode;

/**
 * Presets made for final demo/authoring.
 * Contains more advanced game logic that can be expanded on, but is not included in systems.
 * In this way, more "advanced" game makers can add their own code.
 * @author fitzj
 *
 */
public class PlayerMovement extends KeyInput {

	private static final int JUMP_SPEED = 100;
	private static final int MOVE_SPEED = 60;
	
	private boolean crouched;
	private double timing;
	@SuppressWarnings("unchecked")
	public PlayerMovement(int pid, KeyCode left, KeyCode right, KeyCode up, KeyCode down) {
		super(pid);
		
		crouched = false;
		timing = System.currentTimeMillis();
		
		this.addCode(left, (Serializable & Consumer<Map<String,Component>>) map -> {
			Actions.moveLeft(MOVE_SPEED).accept(map);
			if(map.containsKey(Sprite.KEY)) {
				Sprite s = (Sprite) map.get(Sprite.KEY);
				Actions.xFriction(100).accept(map, map);
				s.getImage().setScaleX(-1);
			}
		});
		
		this.addCode(right, (Serializable & Consumer<Map<String,Component>>) map -> {
			Actions.moveRight(MOVE_SPEED).accept(map);
			if(map.containsKey(Sprite.KEY)) {
				Sprite s = (Sprite) map.get(Sprite.KEY);
				Actions.xFriction(100).accept(map, map);
				s.getImage().setScaleX(1);
			}
		});
		
		this.addCode(up, (Serializable & Consumer<Map<String,Component>>) map -> {
			long time = System.currentTimeMillis();
			Actions.xFriction(0).accept(map, null);
			if(map.containsKey(Jumps.KEY) && time - timing > 200) {
				
				Jumps s = (Jumps) map.get(Jumps.KEY);
				if(s.getData() > 0) {
					Actions.moveUp(JUMP_SPEED).accept(map);
					//s.setData(s.getData() - 1);
					timing = time;

				} 
			}
			
			if(map.containsKey(Sprite.KEY)) {
				Sprite s = (Sprite) map.get(Sprite.KEY);
				if(s.isPlaying()) {
					s.pauseAnimation();
				}
			}
			
			
			if(map.containsKey(Height.KEY)) {
				Height s = (Height) map.get(Height.KEY);
				if(crouched) {
					s.setData(s.getData() * 2);
					if(map.containsKey(YPosition.KEY)) {
						YPosition y = (YPosition) map.get(YPosition.KEY);
						y.setData(y.getData() - s.getData() / 2);
					}
					crouched = false;
				}
			}
			
		});
		
		this.addCode(down, (Serializable & Consumer<Map<String,Component>>) map -> {
			Actions.moveDown(MOVE_SPEED).accept(map);

			if(map.containsKey(Height.KEY)) {
				Height s = (Height) map.get(Height.KEY);
				if(!crouched) {
					s.setData(s.getData()/2);
					if(map.containsKey(YPosition.KEY)) {
						YPosition y = (YPosition) map.get(YPosition.KEY);
						y.setData(y.getData() + s.getData());
					}
					crouched = true;
				}
			}
		});
		
		this.addCode(KeyCode.SPACE, (Serializable & Consumer<Map<String,Component>>) e1 -> {
			long time = System.currentTimeMillis();
			if(time - timing > 200) {
				Actions.fireball().accept(e1);
				timing = time;
			}
			
		});
	}
	
}
