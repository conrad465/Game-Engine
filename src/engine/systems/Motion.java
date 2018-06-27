package engine.systems;

import java.util.Map;

import engine.components.Component;
import engine.components.XPosition;
import engine.components.XVelocity;
import engine.components.YPosition;
import engine.components.YVelocity;


/**
 * ISystem to apply changes in positions to an object based on changes in velocities
 * Required component: Position, Velocity
 *
 * @author Yameng, fitzj
 */

public class Motion extends AbstractSystem implements ISystem {

    public Motion() {
    	super();
    }

    @Override
    public void addComponent(int pid, Map<String, Component> components) {
        if(this.checkComponents(components, XVelocity.KEY, YVelocity.KEY, XPosition.KEY, YPosition.KEY)) {
        	this.directAddComponent(pid, components);
        }
    }
    

    /**
     * Apply changes in velocities to positions
     */
    @Override
    public void execute(double time) {
        for (int pid : this.getActives()) {
            Map<String, Component> components = this.getHandled().get(pid);

            XVelocity vx = (XVelocity) components.get(XVelocity.KEY);
            YVelocity vy = (YVelocity) components.get(YVelocity.KEY);
            XPosition px = (XPosition) components.get(XPosition.KEY);
            YPosition py = (YPosition) components.get(YPosition.KEY);
            px.setData(px.getData() + vx.getData()*time);
            py.setData(py.getData() + vy.getData()*time);
        }
    }


}
