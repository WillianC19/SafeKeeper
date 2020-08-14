package br.coffea.safekeeper.dao;

public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException() {
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Throwable cause) {
		super(cause);
	}
	
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DAOException(String message, Throwable cause, boolean enableSuppresion, boolean writableStackTrace) {
		super(message, cause, enableSuppresion, writableStackTrace);
	}
	
}
