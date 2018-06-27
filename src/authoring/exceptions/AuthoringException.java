package authoring.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * What could go wrong in reading XML files. Must handle all different cases.
 * @author dylanpowers
 *
 */
public class AuthoringException extends RuntimeException {
	// serialization
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_ERROR_MESSAGE = "Something is wrong. Please try again.\n";

	/**
	 * Create an exception based upon a different caught exception
	 */
	public AuthoringException(Throwable cause, AuthoringAlert indication) {
		super(cause);
		if (indication.equals(AuthoringAlert.SHOW)) {
			this.displayAlert();
		}
	}

	/**
	 * Throw a new exception that reads a given message
	 * @param message message to be read
	 * @param values objects to be used in the message given to the user
	 */
	public AuthoringException(String message, AuthoringAlert indication, Object... values) {
		super(String.format(message, values));
		if (indication.equals(AuthoringAlert.SHOW)) {
			this.displayAlert(message);
		}
	}

	/**
	 * Raises a new exception with the given message
	 * @param message the message to raise the exception with
	 */
	public AuthoringException(String message, AuthoringAlert indication) {
		super(message);
		if (indication.equals(AuthoringAlert.SHOW)) {
			this.displayAlert(message);
		}
	}

	/**
	 * @return the default error message for this exception
	 */
	@Override
	public String getMessage() {
		return DEFAULT_ERROR_MESSAGE;
	}

	/**
	 * Show an alert with a given message.
	 * @param message the message to be displayed on the alert
	 */
	public void displayAlert(String message) {
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText(message);
		a.showAndWait();
	}

	/**
	 * Show an alert with the given message and values.
	 * @param message the message to be displayed on the alert
	 * @param values the objects to format the string with
	 */
	public void displayAlert(String message, Object... values) {
		this.displayAlert(String.format(message, values));
	}

	/**
	 * Display an alert with the default message
	 */
	public void displayAlert() {
		this.displayAlert(DEFAULT_ERROR_MESSAGE);
	}
}
