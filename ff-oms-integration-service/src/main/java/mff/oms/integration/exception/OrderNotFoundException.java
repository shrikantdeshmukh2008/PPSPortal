package mff.oms.integration.exception;

public class OrderNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException() {

	}

	public OrderNotFoundException(String msg) {
		super(msg);

	}

}
