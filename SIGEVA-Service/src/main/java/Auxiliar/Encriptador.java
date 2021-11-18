package Auxiliar;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

/**
 * Clase destinada a la creación de un encriptador para encriptar la información
 * sensibles
 * 
 * @author Equipo3
 *
 */
public class Encriptador {
	//@Autowired
	private Environment env;
	private final String CLAVE = env.getProperty("encrypted.key");
	AES256TextEncryptor encriptador;

	
	public Encriptador() {
		encriptador = new AES256TextEncryptor();
		encriptador.setPassword(CLAVE);
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
		return CLAVE;
	}

}
