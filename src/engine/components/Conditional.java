package engine.components;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Runs a consumer or biconsumer using a supplier as its first argument, with an optional second argument
 * @author cndracos
 *
 */
public class Conditional implements Component, BehaviorComponent {

    public static final String KEY = "Conditional";
    private Supplier<Object> conditional;
    private BiConsumer<Object, Object> action;
    private int pid;

    public Conditional (int pid) {
        this.pid = pid;
    }

    /**
     * Sets supplier, which will be action's first argument
     * @param conditional	Supplier returning conditional as first argument
     */
    public void setCondition (Supplier<Object> conditional) {
        this.conditional = conditional;
    }

    /**
     * Defines action to be run if condition is met
     * @param action	Biconsumer to be run if condition is met
     */
    public void setAction (BiConsumer<Object, Object> action) {
        this.action = action;
    }

    /**
     * Runs action on supplied condition
     */
    public void evaluate() {
        Object o = conditional.get();
        if (o!=null) {
            action.accept(o, null);
        }
    }
    
    /**
     * Adds optional second argument to biconsumer
     * @param o2
     */
     public void evaluate(Object o2) {
        Object o = conditional.get();
        if (o!=null) {
            action.accept(o, o2);
        }
    }

	public String getKey() {
		return KEY;
	}

	@Override
	public int getPID() {
		return pid;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addBehavior(Object identifier, Consumer con) {
		setAction((e1, e2) -> con.accept(e1));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addBehavior(Object identifier, BiConsumer bic) {
		setAction(bic);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void appendBehavior(Object identifier, Consumer<Map<String, Component>> con) {
		setAction((e1,e2) -> {
			evaluate();
			con.accept((Map<String, Component>) e1);
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void appendBehavior(Object identifier, BiConsumer<Map<String, Component>, Map<String, Component>> bic) {
		setAction((e1,e2) -> {
			evaluate();
			bic.accept((Map<String, Component>) e1,(Map<String, Component>) e2);
		});
	}
	
	
	

    

}
