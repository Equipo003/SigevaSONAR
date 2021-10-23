package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class CrearCentroSaludTest {
    @Autowired
    private AdministradorController administradorController;
    @Test
	public void testCrearCentroSaludBien() {
		
    	try {
    		administradorController.crearCentroSalud();
        } catch (Exception e){
            Assertions.assertNull(e);
        }
	
	}
}
