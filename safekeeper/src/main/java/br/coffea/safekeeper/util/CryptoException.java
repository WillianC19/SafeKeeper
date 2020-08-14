package br.coffea.safekeeper.util;

public class CryptoException extends RuntimeException {

	private static final long serialVersionUID = 1051262115025076714L;

	public CryptoException() {
	}
	
	public CryptoException(String message) {
		super(message);
	}
	
	public CryptoException(Throwable cause) {
		super(cause);
	}
	
	public CryptoException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CryptoException(String message, Throwable cause, boolean enableSuppresion, boolean writableStackTrace) {
		super(message, cause, enableSuppresion, writableStackTrace);
	}
	
}