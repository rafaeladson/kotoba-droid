package net.fiive.kotoba.data.exceptions;

public class DataOperationFailedException extends RuntimeException {

	public DataOperationFailedException(String detailMessage) {
		super( detailMessage);
	}
}
