package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Auxiliar.Encriptador;

class encriptarTest {

	static Encriptador encriptador = new Encriptador();
	static String cad = "Test encriptador";
	
	
	@Test
	void encriptar() {
		assertNotEquals(encriptador.encriptar(cad), cad);
	}
	
	@Test
	void desencriptar() {
		String encrip = "";
		encrip = encriptador.encriptar(cad);
		assertEquals(encriptador.desencriptar(encrip), cad);
	}

}
