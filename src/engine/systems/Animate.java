package engine.systems;

import engine.components.Animated;
import engine.components.Component;
import engine.components.Height;
import engine.components.Sprite;
import engine.components.Width;
import engine.components.XPosition;
import engine.components.XVelocity;
import engine.components.YPosition;
import engine.exceptions.EngineException;
import javafx.scene.image.ImageView;
import java.util.Map;

/**
 * System which updates the entity's sprite depending on its position component which may (or may not) have been
 * moved in earlier systems, so that what the user sees moving on the screen mimics the values of that entity's
 * position, velocity, and acceleration components
 *
 * @author cndracos, fitzj
 */
public class Animate extends AbstractSystem implements ISystem {

    public Animate() {
    	super();
    }
    
    /**
     * Adds components which it can act upon, choosing only the entities which have both a position AND
     * a Sprite component
     * @param pid entity's ID
     * @param components all of the entity's components
     */
    @Override
    public void addComponent(int pid, Map<String, Component> components) {
        if(this.checkComponents(components, XPosition.KEY, YPosition.KEY, Sprite.KEY)) {
        	this.directAddComponent(pid, components);
        }
    }

    /**
     * Updates sprite positions, resizes them, and animates if necessary
     * @param time	Amount of time passed since last execution
     */
    @Override
    public void execute(double time) throws EngineException {
        for (int pid : this.getActives()) {

            Map<String, Component> components = this.getHandled().get(pid);
            Sprite s = (Sprite) components.get(Sprite.KEY);
                        
            XPosition px = (XPosition) components.get(XPosition.KEY);
            YPosition py = (YPosition) components.get(YPosition.KEY);
            
            if(this.checkComponents(components, Animated.KEY)) {
                this.animateComponents(components);
            }
            
            ImageView im = s.getImage();
            im.setX(px.getData());
            im.setY(py.getData());
            
            if(this.checkComponents(components, Width.KEY, Height.KEY)) {
            	Width w = (Width) components.get(Width.KEY);
            	Height h = (Height) components.get(Height.KEY);
            	im.setFitWidth(w.getData());
            	im.setFitHeight(h.getData());
            }
            
        }
    }

    @Override
    public void removeComponent(int pid) {
        if (this.getHandled().containsKey(pid)) {
            Sprite s = (Sprite) this.getHandled().get(pid).get(Sprite.KEY);
            s.getImage().setX(10000);
        }
        this.getHandled().remove(pid);
    }

	private void animateComponents(Map<String, Component> components) throws EngineException {
        Sprite s = (Sprite) components.get(Sprite.KEY);
        
    	Animated an = (Animated) components.get(Animated.KEY);
		if(!s.isPlaying()) {
			s.animate(an.getData());
		}
    	if(this.checkComponents(components, XVelocity.KEY)) {
    		if(Math.abs(((XVelocity) components.get(XVelocity.KEY)).getData()) < 1) {
    			if(s.isPlaying()) {
    				s.pauseAnimation();
    			}
    		} else {
    			s.playAnimation();
    		}
        }
	}

}
