package com.equipo3.SIGEVA.exception;

import org.springframework.http.HttpStatus;

public class SiGeVaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6095736635870105404L;

	private final HttpStatus status;
	private final String mensajeError;

	public SiGeVaException(HttpStatus status, String mensajeError) {
		this.status = status;
		this.mensajeError = mensajeError;
	}

	@Override
	public String getMessage() {
		return this.mensajeError;
	}

	public HttpStatus getStatus() {
		return this.status;
	}

}