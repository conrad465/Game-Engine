/**
 * The Menu Interface defines the basic structure that all menus must 
 * adhere to. All menus must have the method signature of toggleMenu()
 * in order to allow other classes to have access to when a menu is displayed.
 * The functionality of the menu will reside within the functionality of the 
 * buttons.
 * @author Ryan Fu & Scott Pledger
 *
 */

public interface IMenu {
	
	/**
	 * Changes whether the menu object is displayed on the scene
	 */

	public void toggleMenu();
}
