package com.toucan.rtp.customexception;

public class CustomExceptions {

    public static class UserAlreadyExistsException extends RuntimeException {
		private static final long serialVersionUID = -7038400469095522947L;

		public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidUserException extends RuntimeException {
      
		private static final long serialVersionUID = 9173181464193032222L;

		public InvalidUserException(String message) {
            super(message);
        }
    }
}
