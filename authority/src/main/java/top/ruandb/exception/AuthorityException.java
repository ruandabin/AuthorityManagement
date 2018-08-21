package top.ruandb.exception;

@SuppressWarnings("serial")
public class AuthorityException extends RuntimeException{

	public AuthorityException() {
		super();
	}

	public AuthorityException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthorityException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorityException(String message) {
		super(message);
	}

	public AuthorityException(Throwable cause) {
		super(cause);
	}
	
}
