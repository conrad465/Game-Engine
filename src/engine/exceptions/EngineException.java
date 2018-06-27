package engine.exceptions;

public class EngineException extends Exception {
	/**
	 * default for serialization
	 */
	private static final long serialVersionUID = 1L;
	

	public EngineException(String invalidCommand){
		super(String.format(invalidCommand));
		
	}
}
