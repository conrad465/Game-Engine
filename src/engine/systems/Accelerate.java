package engine.systems;

import java.util.Map;

import engine.components.Component;
import engine.components.XAcceleration;
import engine.components.XVelocity;
import engine.components.YAcceleration;
import engine.components.YVelocity;

/**
 * **
 * System to apply changes in velocities
 * Required component: Velocity
 *
 * @author Yameng, fitzj
 */

public class Accelerate extends AbstractSystem implements ISystem{
	
	public Accelerate() {
		super();
	}

	/**
	 * Adds acceleration and velocity components from <String, Component> Map
	 * 
	 * @param pid	Parent ID of components
	 * @param components	Map of components for given parent
	 */
    public void addComponent(int pid, Map<String, Component> components) {
		if(this.checkComponents(components, XAcceleration.KEY, YAcceleration.KEY, XVelocity.KEY, YVelocity.KEY)) {
			this.directAddComponent(pid, components);
		}
    }

	/**
     * Updates velocity values based on Acceleration component
     * 
     *  @param time	Update time for game loop
     */
	public void execute(double time) {
		for (int pid : this.getActives()) {
			Map<String,Component> activeComponents = this.getHandled().get(pid);

			XAcceleration ax = (XAcceleration) activeComponents.get(XAcceleration.KEY);
			YAcceleration ay = (YAcceleration) activeComponents.get(YAcceleration.KEY);
			XVelocity vx = (XVelocity) activeComponents.get(XVelocity.KEY);
			YVelocity vy = (YVelocity) activeComponents.get(YVelocity.KEY);

			vx.setData(vx.getData() + ax.getData()*time);
			vy.setData(vy.getData() + ay.getData()*time);

			if(vx.getData() < 0) ax.setData(Math.max(ax.getData(), 0));
			if(vx.getData() > 0) ax.setData(Math.min(ax.getData(), 0));
			
		}
	}


}

