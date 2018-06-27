package engine.components;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Interface for adding behavior in the form of consumers to a component. 
 * Done to help authoring environment.
 * @author fitzj
 *
 */
public interface BehaviorComponent {
	
	/**
	 * Adds a consumer to the component. 
	 * If the component usually takes a BiConsumer, it will be passed the consumer's argument for the first argument, and null for the second.
	 * All passed consumers should check for null condition regardless.
	 * @param identifier	The object used for a conditional
	 * @param con			The consumer to be run
	 */
	public void addBehavior(Object identifier, Consumer<Map<String,Component>> con);
	
	/**
	 * Adds a biconsumer to the component.
	 * If the component usually takes a consumer, it will be passed the biconsumer's first argument as its argument.
	 * @param identifier	The object used for a conditional
	 * @param bic			The biconsumer to be run
	 */
	public void addBehavior(Object identifier, BiConsumer<Map<String,Component>,Map<String,Component>> bic);
	
	
	public void appendBehavior(Object identifier, Consumer<Map<String,Component>> con);

	
	public void appendBehavior(Object identifier, BiConsumer<Map<String,Component>,Map<String,Component>> bic);

	
}
