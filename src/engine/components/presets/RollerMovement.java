package engine.components.presets;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

import engine.components.Component;
import engine.components.Jumps;
import engine.components.KeyInput;
import engine.components.Sprite;
import engine.components.YAcceleration;
import javafx.scene.input.KeyCode;

public class RollerMovement extends KeyInput {

	private long timing;
	
	@SuppressWarnings("unchecked")
	public RollerMovement(int pid) {
		super(pid);
		timing = System.currentTimeMillis();
		this.addCode(KeyCode.SPACE, (Serializable & Consumer<Map<String,Component>>) (map) -> {
			if(map.containsKey(YAcceleration.KEY) && map.containsKey(Sprite.KEY)) {
				YAcceleration ya = (YAcceleration) map.get(YAcceleration.KEY);
				Sprite s = (Sprite) map.get(Sprite.KEY);
				long time = System.currentTimeMillis();
				if(map.containsKey(Jumps.KEY) && time - timing > 200) {
					Jumps j = (Jumps) map.get(Jumps.KEY);
					if(j.getData() > 0) {
						ya.setData(-ya.getData());
						j.addData(-1);
						time = timing;
						s.getImage().setScaleY(-s.getImage().getScaleY());
					}
				}
			}
		});
	
	}



}
