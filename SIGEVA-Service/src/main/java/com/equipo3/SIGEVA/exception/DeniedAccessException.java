package com.equipo3.SIGEVA.exception;

public class DeniedAccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887115421365651479L;

	public DeniedAccessException() {
	}

	public DeniedAccessException(String mensaje) {
		super(mensaje);
	}

}