package com.equipo3.SIGEVA;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
@SpringBootTest
public class CrearCentroSaludTest {
    @Autowired
    private AdministradorController administradorController;
    @Test
	public void testCrearCentroSaludBien() {
		
    	try {
    		administradorController.crearCentroSalud(null);
        } catch (Exception e){
            Assertions.assertNull(e);
        }
	}
}
