package com.toucan.rtp.core.customexception;

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
    
    public static class OtpValidationException extends RuntimeException {
    	 
		private static final long serialVersionUID = -2664357348266903347L;
 
		public OtpValidationException(String message) {
			super(message);
		}
	}
 
	public static class UserNotFoundException extends RuntimeException {
 
		private static final long serialVersionUID = 7073140824846691821L;
 
		public UserNotFoundException(String message) {
			super(message);
		}
	}
 
	public static class GeneralServiceException extends RuntimeException {
 
		private static final long serialVersionUID = 7047702984659216676L;
 
		public GeneralServiceException(String message) {
			super(message);
		}
	}
 
	public static class OtpServiceException extends RuntimeException {
 
		private static final long serialVersionUID = 5824712550444365916L;
 
		public OtpServiceException(String message) {
			super(message);
		}
 
	}

}
