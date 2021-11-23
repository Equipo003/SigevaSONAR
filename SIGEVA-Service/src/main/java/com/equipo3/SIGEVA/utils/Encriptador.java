package com.equipo3.SIGEVA.utils;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Component;

/**
 * Clase destinada a la creación de un encriptador para encriptar la información
 * sensibles
 * 
 * @author Equipo3
 *
 */
@Component
public class Encriptador {
	private final String key = "2189474sdGHSGDA223hdf";
	AES256TextEncryptor encriptador;

	
	public Encriptador() {
		encriptador = new AES256TextEncryptor();
		encriptador.setPassword(key);
	}

	public String encriptar(String cadena) {
		return encriptador.encrypt(cadena);
	}

	public String desencriptar(String cadena) {
		try {
			return encriptador.decrypt(cadena);
		} catch (Exception e) {
			return null;
		}

	}

	public String getClave() {
		return key;
	}

}
