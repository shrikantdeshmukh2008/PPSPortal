package mff.oms.inventory.exception;

public class InsufficientInventoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientInventoryException() {

	}

	public InsufficientInventoryException(String msg) {
		super(msg);

	}

}
