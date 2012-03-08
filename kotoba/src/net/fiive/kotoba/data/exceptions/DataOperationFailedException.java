package net.fiive.kotoba.data.exceptions;

public class DataOperationFailedException extends RuntimeException {

	private static final long serialVersionUID = -6464456615454500861L;

	public DataOperationFailedException(String detailMessage) {
		super( detailMessage);
	}
}
