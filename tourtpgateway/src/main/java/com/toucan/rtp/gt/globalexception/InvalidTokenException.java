package com.toucan.rtp.gt.globalexception;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = 3900236475469841326L;

	public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
