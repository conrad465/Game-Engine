package authoring.factories;

import java.util.function.Consumer;

/**
 * Interface that is to be implemented by elements that should be clickable.
 * @author Hemanth Yakkali(hy115)
 *
 */
public interface ClickableElement extends Element{
	
	public void handleConsumer(Consumer<Void> event);

}
